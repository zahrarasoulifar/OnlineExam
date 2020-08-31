<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/error.css"/>" rel="stylesheet">
    <title>error!</title>
</head>
<body>

<h2 align="center">This is invalid!</h2>
<h2 align="center">${message}</h2>
<br>
<div align="center">
    <button onclick="location.href='/login';">login page</button>
</div>

</body>
</html>
