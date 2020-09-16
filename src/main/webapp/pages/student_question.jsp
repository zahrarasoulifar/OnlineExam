<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="<c:url value="/resources/css/body.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/student_question.css"/>" rel="stylesheet">
</head>
<body>
<br><br><br>

<form>
    <div align="center" class="main">
        <p id="question">${question.content}</p>
        <div id="answer" align="center">
            <textarea id="descriptive_answer" placeholder="enter your answer here..."></textarea>
        </div>
        <div id="choices">

        </div>
    </div>
</form>
<div align="center">
    <button onclick="previousQuestion(${exam.id}, ${student.id}, ${question.id})" id="previous_btn">previous</button>
    <button onclick="nextQuestion(${exam.id}, ${student.id}, ${question.id})" id="next_btn">next</button>
    <button id="finish_btn" onclick="finishExam()" style="visibility: hidden">Finish</button>
</div>
<div id="timer" align="right">
    <span id="minute"></span> <span>:</span> <span id="second"></span>
</div>
</body>

<script>

    window.onload = function () {
        setTimer("${time}");
        showQuestion();
        showAnswer("${studentAnswer.content}", "${type}");
        showFinishButton("${finish_enabled}");
        disablePrevious("${previous_enabled}");
    };

    function showFinishButton(finishEnabled) {
        if (finishEnabled === "true") {
            document.getElementById("finish_btn").style.visibility = "visible";
            document.getElementById("next_btn").disabled = true;
        }
    }

    function disablePrevious(previousEnabled) {
        if (previousEnabled === "false") {
            document.getElementById("previous_btn").disabled = true;
        }
    }

    function showQuestion() {
        var type = "${type}";
        if (type === "multiple") {
            document.getElementById("descriptive_answer").style.visibility = "hidden";
            getQuestionChoices("${question.id}");
        }
    }

    function showAnswer(content, type) {
        console.log(type);
        if (type === "descriptive") {
            console.log(document.getElementById("descriptive_answer"));
            console.log(content);
            document.getElementById("descriptive_answer").textContent = content;
        }
        else if (type === "multiple") {
            var choicesLength = document.getElementsByName("choice").length;
            for (i = 0; i < choicesLength; i++) {
                if (document.getElementsByName("choice").item(i).value === content) {
                    document.getElementsByName("choice").item(i).checked = true;
                    break;
                }
            }
        }
    }

    function nextQuestion(examId, studentId, questionId) {
        submitAnswer("${studentAnswer.id}");
        window.open("/question/next/" + examId + "/" + studentId + "/" + questionId, "_self");
    }

    function previousQuestion(examId, studentId, questionId) {
        submitAnswer("${studentAnswer.id}");
        window.open("/question/previous/" + examId + "/" + studentId + "/" + questionId, "_self");
    }

    function submitAnswer(id, point) {
        var type = "${type}";
        var answer;
        if (type === "multiple") {
            answer = document.querySelector('input[name="choice"]:checked').value;
        }
        else if (type === "descriptive") {
            answer = document.getElementById("descriptive_answer").value;
        }
        var userAnswer = {
            "content" : answer,
            "student" : {
                "id" : ${student.id}
            },
            "exam" : {
                "id" : "${exam.id}"
            },
            "question" : {
                "id" : "${question.id}",
                "content" : "${question.content}"
            }
        };

        if (id !== null && id !== "") {
            console.log(id);
            userAnswer.id = id;
        }
        console.log(userAnswer);
        $.ajax({
            type : "POST",
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            url : "http://localhost:8080/answer/save_student_answer",
            data :
                JSON.stringify(userAnswer),
            success : function(result) {
                alert("done!");
            },
            done : function(e) {
                console.log("DONE");
            }
        });
    }

    function getQuestionChoices(questionId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                console.log(data);
                showChoices(data);
                showAnswer("${studentAnswer.content}", "${type}");
            }
        };
        xhttp.open("GET", "http://localhost:8080/question/choices/" + questionId, true);
        xhttp.send();
    }

    function showChoices(data) {
        var choices = '';
        for (i = 0; i < data.length; i++) {
            choices += "<input type='radio' value='" + data[i].content + "' id='" + data[i].id + "' name='choice'>"
                + data[i].content + "<br>";
        }
        document.getElementById("choices").innerHTML = choices;
    }

    function setTimer(time) {
        setInterval(function() {
            time = time - 1;
            if (time === 0){
                alert("TIME'S UP!");
                submitAnswer("${studentAnswer.id}");
                window.open("/exam/exam_finished/${student.id}", "_self");
            }
            var minute = Math.floor(time / 60);
            var second = time - (minute * 60);
            document.getElementById('minute').textContent = minute;
            document.getElementById('second').textContent = second;
        }, 1000);
    }

    function finishExam() {
        submitAnswer("${studentAnswer.id}");
        window.open("/exam/exam_finished/${student.id}", "_self");
    }
</script>

</html>
