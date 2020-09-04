<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Exam!</title>
    <link href="<c:url value="/resources/css/teacher_exam.css"/>" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div class="navbar" >
    <button onclick="location.href='/teacher/course/${exam.teacher.id}/${exam.course.id}';">back</button>
    <button onclick="deleteExam(${exam.id}, ${exam.teacher.id}, ${exam.course.id})">delete</button>
    <div class="dropdown">
        <button class="dropbtn">Add Question<br>
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="/question/choose_question_type/${exam.id}/${exam.course.id}">New Question</a>
            <a href="/question/question_bank_page/${exam.id}/${exam.course.id}">Question Bank</a>
        </div>
    </div>
    <button onclick="stopExam(${exam.id})">stop exam!</button>
</div><br><br>
<div>
    <p>${edit_message}</p>
    <form method="post" action="/exam/edit/${exam.id}">
    <table id="table">

        <tr>
            <th>Title</th>
            <th>Time</th>
            <th>Start</th>
            <th>End</th>
            <th style="color: black" class="clickable" onclick="enableEdit()">Edit</th>
        </tr>
        <tr>
            <td><input name="title" value="${exam.title}" disabled></td>
            <td><input type="number" name="time" value="${exam.time}" disabled></td>
            <td><input name="startDate" value="${exam.startDate}" disabled></td>
            <td><input name="endDate" value="${exam.endDate}" disabled></td>
            <td><input type="submit" value="save" id="save_btn" style="visibility: hidden "></td>
        </tr>
    </table>
    </form>

    <h3>Questions! Total points: <span id="total_point"></span></h3>
    <table id="question_table">

    </table>
</div>
</body>
<script>
    function enableEdit() {
        var inputs = document.getElementsByTagName("input");
        for (i = 0; i < inputs.length; i++) {
            inputs[i].disabled = false;
        }
        document.getElementById("save_btn").style.visibility = "visible";
    }

    function deleteExam(examId, teacherId, courseId) {
        var deleteConfirm = confirm("are you sure you want to delete this exam from course?");
        if (deleteConfirm === true) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    window.open("/teacher/course/" + teacherId + '/' + courseId, "_self");
                }
            };
            xhttp.open("DELETE", "http://localhost:8080/exam/delete/" + examId, true);
            xhttp.send();
        }
    }

    function stopExam(examId) {
        var stopConfirm = confirm("are you sure you want to stop the exam?");

        if (stopConfirm === true) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    alert(this.responseText);
                }
            };
            xhttp.open("PUT", "http://localhost:8080/exam/stop/" + examId, true);
            xhttp.send();
        }
    }

    window.onload = function () {
        getQuestions();
        getExamTotalPoint("${exam.id}");
    }

    function getExamTotalPoint(examId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("total_point").textContent = this.responseText;
            }
        };
        xhttp.open("GET", "http://localhost:8080/exam/total_point/" + examId, true);
        xhttp.send();
    }
    function getQuestions() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                console.log(this.responseText);
                var data = JSON.parse(this.responseText);
                console.log(data);
                showMapData(data);
            }
        };
        xhttp.open("GET", "http://localhost:8080/exam/questions/${exam.id}", true);
        xhttp.send();
    }

    function showMapData(data) {
        var questionTable = '<tr > <th>Title</th> <th>Question</th> <th>Point </th><th></th></tr>';
        for (var index in data) {
            console.log("question : " + index + ", " + data[index]);
            var question = JSON.parse(index);
            questionTable += '<tr id="' + question.id + '"><td contenteditable="true"> ' + question.title +
                '</td><td contenteditable="true">' + question.content + '</td> <td>' + data[index] + '</td>' +
                ' <td style="font-weight: bold; cursor: pointer" ' +
                'onclick="editQuestion(' + question.id + ')">save changes</td>';
        }
        document.getElementById("question_table").innerHTML = questionTable;
    }

    function editQuestion(questionId) {
        var row = document.getElementById(questionId);
        var cells = row.getElementsByTagName("td");
        var question = {
            "id" : questionId,
            "title" : cells[0].textContent,
            "content" : cells[1].textContent
        }
        console.log(question);
        $.ajax({
            type : "PUT",
            contentType : 'application/json; charset=utf-8',
            url : "http://localhost:8080/question/edit",
            data : JSON.stringify(question),
            success : function(result) {
                alert(result);
            },
            done : function(e) {
                console.log("DONE");
            }
        });
    }


</script>

</html>
