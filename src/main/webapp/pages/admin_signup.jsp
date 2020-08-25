<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/admin_signup.css"/>" rel="stylesheet">
    <title>signup</title>

</head>
<body>
<div align="right">
    <button class="button" onclick="location.href='/admin/users';">back</button>
</div>
<h3 align="center">${add_message}</h3>
<div class="add_new">
    <form:form class="form1" modelAttribute="user" action="/admin/signup/" method="post">
        <form:input class="un" path="firstName" name="firstName" required="required" placeholder="first name" />
        <form:input class="un" path="lastName" name="lastName" required="required" placeholder="last name" />
        <form:select class="un" path="role" id="role">
            <form:option class="un" value='TEACHER'>teacher</form:option>
            <form:option class="un" value='STUDENT'>student</form:option>
            <form:option class="un" value='ADMIN' onkeypress="addAdminNumberField()">Admin</form:option>
        </form:select>
        <input type="number" id="admin_number" name="admin_number" class="admin_role un" placeholder="admin number" >
        <form:input class="un" path="email" name="email" required="required" placeholder="email"/>
        <form:password class="un" path="password" name="password" required="required" placeholder="password"/>
        <input class="un" type="password" name="password_confirm" id="password_confirm"
               required="required" placeholder="password confirm">
        <form:button class="button" name="register"> Register </form:button>

    </form:form>
</div>

</body>

<script>

    window.onload = function () {
        var selectBox = document.getElementById("role");
        selectBox.addEventListener('change', changeFunc);
        function changeFunc() {
            if (this.value === "ADMIN"){
                document.getElementById("admin_number").style.visibility = "visible";
                document.getElementById("admin_number_label").style.visibility = "visible";
            }
            else {
                document.getElementById("admin_number").style.visibility = "hidden";
                document.getElementById("admin_number_label").style.visibility = "hidden";
            }
        }
    }
</script>

</html>
