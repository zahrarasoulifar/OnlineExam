<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
    <link href="<c:url value="/resources/css/login.css"/>" rel="stylesheet">
</head>
<body>

    <div class="main">
        <p class="sign" align="center">Sign in</p>
        <p class="sign" align="center">${login_error}</p>
        <form class="form1" name='form' action="/loginProcess" method='POST'>
            <input class="un" type="text" align="center" placeholder="Email" name="email">
            <input class="pass" type="password" align="center" placeholder="Password" name="password">
            <input type="submit" class="button" align="center" value="Sign in"/>

        </form>
        <button class="button" onclick="location.href='/academic/signup_page';">Sign up</button>
    </div>

</body>
</html>


