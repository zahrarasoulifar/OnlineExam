<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add question</title>
    <link href="<c:url value="/resources/css/add_descriptive_page.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/labels.css"/>" rel="stylesheet">
</head>
<body>
<div class="main">
    <h3 align="center" class="title">Add New Descriptive Question!</h3>
    <form:form class="form1" modelAttribute="question" action="/question/add_descriptive/${examId}/${courseId}" method="post">
        <form:input class="un" path="title" name="title" required="required"
                    placeholder="title (max:15 characters)" maxlength="15"/>
        <form:textarea class="un" path="content" name="description" required="required" placeholder="question"/>
        <div align="center">
            <label class="label">enter your preferred answer here.</label>
        </div>
        <textarea class="un" name="answer_content"></textarea><br>
        <div align="center">
            <p>Add this question to course question bank?</p><br>
            <label class="label" for="yes">YES</label>
            <input type="radio" id="yes" name="bankStatus" value="YES">
            <label class="label" for="no">NO</label>
            <input type="radio" id="no" name="bankStatus" value="NO">
        </div><br>
        <input type="number" name="point" min=0 step="any" class="un" placeholder="question point">
        <form:button name="add">add Question</form:button>
    </form:form>

</div>
</body>
</html>
