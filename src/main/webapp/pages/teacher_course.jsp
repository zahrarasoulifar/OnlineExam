<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course</title>
    <link href="<c:url value="/resources/css/teacher_course.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/messages.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
</head>
<body>
<div class="navbar" >
    <p class="message">${course.title}</p>
    <button onclick="location.href='/teacher/home_page/${user.id}';">back</button>
    <button onclick="location.href='/teacher/add_exam_page/' + ${user.id} + '/' + ${course.id};">add exam</button>
</div>
<div>
    <h3>Exams!</h3>
    <table id="table">
        <tr>
            <th>Title</th>
            <th>Time</th>
            <th>Start</th>
            <th>End</th>
        </tr>
    </table>
</div>

</body>

<script>
    window.onload = function () {
        loadDoc();
    };

    function loadDoc() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data);
            }
        };
        xhttp.open("GET", "http://localhost:8080/course/exams/" + ${course.id}, true);
        xhttp.send();
    }

    function showData(data) {
        var table = '<tr style="cursor: unset"> <th>Title</th> <th>Time</th> <th>Start</th> <th>End</th><th> </th></tr>';
        data.map(value =>
        table += '<tr  style="cursor: unset" id="' + value.id + '"><td> ' + value.title +
            '</td><td>' + value.time + '</td><td>'
            + new Date(value.startDate).toLocaleDateString() + ' '
            + new Date(value.startDate).toLocaleTimeString() + '</td><td>'
            + new Date(value.endDate).toLocaleDateString() + ' '
            + new Date(value.endDate).toLocaleTimeString() +
            '</td><td class="clickable" style="font-weight: bold;" ' +
            'onclick="openExam(' + value.id + ',' + ${user.id} + ',' + value.teacher.id + ')">open </td></tr>'
    )
        document.getElementById("table").innerHTML =table;
    }

    function openExam(examId, teacherId, examTeacherId) {
        if (teacherId !== examTeacherId) {
            alert("you can't open this exam!");
        }
        else {
            window.open("/exam/teacher_edit_page/" + examId , "_self");
        }
    }

</script>
</html>
