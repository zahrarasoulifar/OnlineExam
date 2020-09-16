<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>choose question type:</title>
    <link href="<c:url value="/resources/css/buttons.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/messages.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/body.css"/>" rel="stylesheet">
</head>
<body>
<div align="center">
    <p class="message" style="margin: 20vh; display: block">Choose your question type!</p>

    <button style="float: none" onclick="location.href='/question/add_multiple_choice_page/${examId}/${courseId}';">Multiple choice Question</button>
    <button style="float: none" onclick="location.href='/question/add_descriptive_page/${examId}/${courseId}';">Descriptive Question</button>
</div>
</body>
</html>
