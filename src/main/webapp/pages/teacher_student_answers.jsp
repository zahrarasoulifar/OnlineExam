<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Answers</title>
    <link href="<c:url value="/resources/css/body.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/buttons.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/messages.css"/>" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div id="navbar">
    <p class="message">student: ${student.firstName}  ${student.lastName}</p>
    <p class="message">exam: ${exam.title}</p>
    <button onclick="location.href='/teacher/answers/${exam.id}';">back</button>
</div><br><br>

<form>
    <h3><span>student total points : </span> <span id="total_point"></span></h3>
    <table id="table">

    </table>
    <button onclick="submitPoints()">submit points</button>
</form>


</body>
<script>
    var answersList;
    window.onload = function (ev) {
        loadStudentAnswers("${exam.id}", "${student.id}");
    }

    function loadStudentAnswers(examId, studentId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data);
                answersList = data;
                calculateTotalPoint();
                console.log(data);
            }
        };
        xhttp.open("GET", "http://localhost:8080/student_answer/" + examId + "/" + studentId, true);
        xhttp.send();
    }

    function showData(data) {
        var table = '<tr> <th>Question</th> <th>teacher answer</th> <th>student answer</th><th>point</th></tr>';
        data.map(value =>
        table += '<tr><td> ' +
            value.question.content + '</td><td>' + value.question.answer.content + '</td><td>' + value.content +
            '</td><td><input placeholder="max: ' + value.maxPoint + '" max="' + value.maxPoint +
            '" type="number" min=0 step="any" id= ' + value.id +  '></td></tr>'
    )
        document.getElementById("table").innerHTML = table;
    }

    function submitPoints() {
        answersList.forEach(setPoint);
        $.ajax({
            type : "POST",
            contentType : 'application/json; charset=utf-8',
            url : "http://localhost:8080/student_answer/grade_answers",
            data :
                JSON.stringify(answersList),
            success : function(result) {
                console.log("done!");
            },
            done : function(e) {
                console.log("DONE");
            }
        });
        console.log(answersList);
    }

    function setPoint(answer, index) {
        answer.point = parseFloat(document.getElementById(answer.id).value);
    }
    var totalPoint = 0;
    function calculateTotalPoint() {
        answersList.forEach(sum);

        function sum(answer, index) {
            if (answer.point !== null && answer.point !== "") {
                totalPoint += parseFloat(answer.point);
                console.log("point:" + answer.point);
                console.log("on: " + totalPoint);
            }
        }
        document.getElementById("total_point").textContent = totalPoint;
    }
</script>

</html>
