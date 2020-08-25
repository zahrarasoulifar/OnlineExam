<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/email_sent.css"/>" rel="stylesheet">
    <title>Email sent</title>

</head>
<body>
<h2 align="center">activation link sent to ${email}!</h2>
<div align="center">
    <button onclick="location.href='/login';">login page</button>
</div>

</body>
</html>
