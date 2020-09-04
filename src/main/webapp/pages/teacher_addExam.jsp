<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Exam</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="<c:url value="/resources/css/teacher_addExam.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/labels.css"/>" rel="stylesheet">
</head>
<body>
<div align="right">
    <button class="button" onclick="location.href='/teacher/course/${teacherId}/${courseId}';">back</button>
</div>
<div class="main">
    <h3 align="center" class="title">Add New Exam!</h3>
    <form:form class="form1" modelAttribute="exam" action="/exam/${teacherId}/${courseId}" method="post">
        <form:input class="un" path="title" name="title" required="required" placeholder="title"/>
        <form:textarea class="un" path="description" name="description" required="required" placeholder="description"/>
        <div align="center">
            <form:label path="time" class="label" for="time">time(minutes):</form:label>
        </div>
        <form:input class="un" path="time" name="time" required="required" placeholder="time(minutes)"/><br>
        <div align="center">
            <form:label path="startDate" class="label" for="startDate">start time:</form:label><br>
        </div>
        <input class="un" type="datetime-local" name="startDate" id="startDate"><br>
        <div align="center">
            <form:label path="endDate" class="label" for="endDate">end time:</form:label>
        </div>
        <input class="un" type="datetime-local" name="endDate" id="endDate"><br>
        <form:button name="add">add exam</form:button>
    </form:form>

</div>
</body>
</html>
