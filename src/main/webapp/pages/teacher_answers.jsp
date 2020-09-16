<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student answers</title>
    <link href="<c:url value="/resources/css/body.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/buttons.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/messages.css"/>" rel="stylesheet">
</head>
<body>
<div id="navbar">
    <span>Exam : ${exam.title}</span>
    <button onclick="location.href='/exam/teacher_edit_page/${exam.id}';">back</button>
</div>
<br>
<table id="table">

</table>
</body>

<script>
    var pointList = [];

    window.onload = function (ev) {
        loadStudents("${exam.id}");
    }

    function loadStudents(examId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data, examId);
            }
        };
        xhttp.open("GET", "http://localhost:8080/exam/students/" + examId, true);
        xhttp.send();
    }

    function showData(data, examId) {
        console.log(data);
        var table = '<tr> <th>first name</th> <th>last name</th><th>total point</th></tr>';
        data.map(value => table += '<tr id="' + value.id + '"><td> ' + value.firstName +
            '</td><td>' + value.lastName + '</td><td></td></tr>'
    )
        for (i = 0; i < data.length; i++) {
             getStudentTotalPoint("${exam.id}", data[i].id);
        }
        document.getElementById("table").innerHTML = table;
        console.log(pointList);
        addRowHandlers("table", "${exam.id}");
    }

    function getStudentTotalPoint(examId, studentId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                var pointObject = {
                    "id" : studentId,
                    "point" : data
                }
                pointList.push(pointObject);
                showStudentTotalPoints();
            }
        };
        xhttp.open("GET", "http://localhost:8080/student_answer/total_point/" + examId + "/" + studentId, true);
        xhttp.send();
    }

    function showStudentTotalPoints() {
        var pointObject = pointList.pop();
        document.getElementById(pointObject.id).children[2].textContent = pointObject.point;
    }

    function addRowHandlers(tableId, examId) {
        var table = document.getElementById(tableId);
        var rows = table.getElementsByTagName("tr");
        for (i = 1; i < rows.length; i++) {
            var currentRow = table.rows[i];
            var createClickHandler = function(row) {
                return function() {
                    window.open("/teacher/student_answers/" + examId + "/" + row.id , "_self");
                };
            };
            currentRow.onclick = createClickHandler(currentRow);
        }
    }


</script>
</html>
