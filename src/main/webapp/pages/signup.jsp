<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/signup.css"/>" rel="stylesheet">
    <script src="<c:url value="/resources/js/hello.js" />"></script>
    <title>register</title>
</head>
<body>

<div class="main">
    <p class="sign" align="center">Sign up</p>
    <form:form class="form1" modelAttribute="academicUser" action="/academic/signup" method="post" onsubmit="validatePassword">
        <form:input class="un" path="firstName" name="firstName" required="required" placeholder="first name"/>
        <form:input class="un" path="lastName" name="lastName" required="required" placeholder="last name"/>
        <form:select path="role" class="un">
            <form:option value='TEACHER' class="un">teacher</form:option>
            <form:option value='STUDENT' class="un">student</form:option>
        </form:select>
        <form:input class="un" path="email" name="email" required="required" id="email" placeholder="Email" onchange="validateEmail"/>
        <form:password class="pass" path="password" name="password" placeholder="password" required="required"/>
        <input class="pass" type="password" name="password_confirm" id="password_confirm" placeholder="password confirm"required>

        <form:button class="button" name="register">Register</form:button>

    </form:form>
</div>
</body>

<script>
    var password = document.getElementById("password");
    var confirm_password = document.getElementById("password_confirm");
    var email = document.getElementById("email");

    function validatePassword(){
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
            var content = password.value;
            var regex = /(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,})$/;

            if (!(regex.test(content))) {
                password.setCustomValidity("password length must be more than 8 and include number and letter.");
            }else {
                password.setCustomValidity('');
            }
        }
    }
    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;


    function validateEmail()
    {
        var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        if (!regex.test(email.value)) {
            email.setCustomValidity("enter a valid email address!");
        }
        else {
            email.setCustomValidity('');
        }
    }

    email.onkeyup = validateEmail;

</script>

</html>
