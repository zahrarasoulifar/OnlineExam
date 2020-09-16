<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exam finished</title>
    <link href="<c:url value="/resources/css/buttons.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/messages.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/body.css"/>" rel="stylesheet">
</head>
<body>
<div align="center">
    <br>
    <p class="message">Exam is finished!</p><br>
    <p>${message}</p>
</div>
<br>
<div align="center">
    <button style="float: none" onclick="location.href='/student/home_page/${studentId}';">Back To Home page</button>
</div>
</body>
</html>
