<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Exam!</title>
    <link href="<c:url value="/resources/css/teacher_exam.css"/>" rel="stylesheet">
</head>
<body>
<div class="navbar" >
    <button onclick="location.href='/teacher/course/${exam.teacher.id}/${exam.course.id}';">back</button>
    <button onclick="deleteExam(${exam.id}, ${exam.teacher.id}, ${exam.course.id})">delete</button>
    <button onclick="location.href='#';">add Question</button>
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


</script>

</html>
