<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Points</title>
    <link href="<c:url value="/resources/css/body.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/buttons.css"/>" rel="stylesheet">
</head>
<body>
<div>
    <button onclick="location.href='/student/home_page/${student.id}';">back</button>
</div><br>
<table id="table">

</table>

</body>
<script>
    var pointList = [];
    window.onload = function (ev) {
        loadDoc();
    }

    function loadDoc() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data);
            }
        };
        xhttp.open("GET", "http://localhost:8080/exam/byStudent/" + ${student.id}, true);
        xhttp.send();
    }

    function showData(data) {
        var table = '<tr> <th>exam title</th> <th>point</th></tr>';
        data.map(value =>
        table += '<tr id="' + value.id + '"><td> ' +
            value.title + '</td><td></td></tr>'
    )

        for (i = 0; i < data.length; i++) {
            getStudentTotalPoint(data[i].id, "${student.id}");
        }
        document.getElementById("table").innerHTML = table;
    }

    function getStudentTotalPoint(examId, studentId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                var pointObject = {
                    "id" : examId,
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
        document.getElementById(pointObject.id).children[1].textContent = pointObject.point;
    }
</script>
</html>
