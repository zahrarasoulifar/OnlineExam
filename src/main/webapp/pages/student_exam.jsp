<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exam</title>
    <link href="<c:url value="/resources/css/student_exam.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/body.css"/>" rel="stylesheet">
</head>
<body>
<div class="navbar">
    <button class="nav_button" onclick="location.href='/student/course/${student.id}/${exam.course.id}';">back</button>
</div>
<div class="main" align="center">
    <h2>Exam info</h2>
    <p>title: ${exam.title}</p>
    <p>description: ${exam.description}</p>
    <p>time: ${exam.time} minutes</p>
    <p>start time: ${exam.startDate}</p>
    <p>end time: ${exam.endDate}</p>
</div>
<br>
<div align="center">
    <button id="start_btn" onclick="location.href='/exam/start/${exam.id}/${student.id}';">Start Exam</button>
</div>
</body>
</html>
