<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/student_home.css"/>" rel="stylesheet">
    <title>student</title>

</head>
<body>
<div align="right">
    <button onclick="location.href='/logout';">logout</button>
</div>
<h3 align="center">your account state : ${user_state}</h3>
<div>
    <ul>
        <li>id : ${user.id}</li>
        <li>first name : ${user.firstName}</li>
        <li>last name : ${user.lastName}</li>
        <li>email : ${user.email}</li>
        <li>role : ${user.role}</li>
    </ul>
</div>

</body>
</html>
