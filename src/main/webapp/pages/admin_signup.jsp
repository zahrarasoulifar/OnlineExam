<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/admin_signup.css"/>" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <title>signup</title>

</head>
<body>
<div align="right">
    <button class="button" onclick="location.href='/admin/users';">back</button>
</div>
<h3 align="center" id="add_message"></h3>
<div class="add_new">
    <form class="form1">
        <input class="un" name="firstName" required placeholder="first name" id="firstName" />
        <input class="un" name="lastName" required placeholder="last name" id="lastName"/>
        <select class="un" id="role">
            <option class="un" value='TEACHER'>teacher</option>
            <option class="un" value='STUDENT'>student</option>
            <option class="un" value='ADMIN'>Admin</option>
        </select>
        <input type="number" id="admin_number" name="admin_number" class="admin_role un" placeholder="admin number" >
        <input type="email" class="un" name="email" required placeholder="email" id="email"/>
        <input type="password" class="un" name="password" required placeholder="password" id="password"/>
        <input type="password" class="un"  name="password_confirm" id="password_confirm"
               required placeholder="password confirm">


    </form>
    <button class="button" name="register" onclick="add()"> Register </button>
</div>

</body>

<script>
    window.onload = function () {
        var selectBox = document.getElementById("role");
        selectBox.addEventListener('change', changeFunc);
        function changeFunc() {
            if (this.value === "ADMIN"){
                document.getElementById("admin_number").style.visibility = "visible";
            }
            else {
                document.getElementById("admin_number").style.visibility = "hidden";
            }
        }
    }

    function add(){
        var user = {
            "firstName" : document.getElementById("firstName").value,
            "lastName" : document.getElementById("lastName").value,
            "email" : document.getElementById("email").value,
            "role" : document.getElementById("role").value,
            "password" : document.getElementById("password").value
        };
        var signupUrl;

        if (user.role === "ADMIN") {
            var number = document.getElementById("admin_number").value;
            user.adminNumber = number;
            signupUrl = "http://localhost:8080/admin/add";
        }
        else {
            signupUrl = "http://localhost:8080/academic/addByAdmin";
        }


        $.ajax({
            type : "POST",
            contentType : 'application/json; charset=utf-8',
            // dataType : 'json',
            url : signupUrl,
            data : JSON.stringify(user),
            success : function(result) {
                alert(result);
                window.open("/admin/signup_page", "_self");
            },
            done : function(e) {
                console.log("DONE");
            }
        });
    }
</script>

</html>
