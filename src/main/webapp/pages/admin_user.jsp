<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user</title>
    <link href="<c:url value="/resources/css/admin_user.css"/>" rel="stylesheet">
</head>
<body>

<div align="right">
    <button onclick="deleteUser(${user.id})">delete</button>
    <button onclick="showEdit()">edit</button>
    <button onclick="location.href='/admin/users';">back</button>
</div>

<div>
    <ul>
        <li>id : ${user.id}</li>
        <li>first name : ${user.firstName}</li>
        <li>last name : ${user.lastName}</li>
        <li>email : ${user.email}</li>
        <li>role : ${user.role}</li>
    </ul>
</div>


<div id="edit_container" class="form_container">
<p id="message">edit!</p>
<form:form modelAttribute="user" action="/admin/update/${user.id}" method="post">
    <form:label path="firstName">first name</form:label>
    <form:input path="firstName" name="firstName" />
    <form:label path="lastName">last name</form:label>
    <form:input path="lastName" name="lastName" />
    <form:select path="role">
        <form:option value=''>   </form:option>
        <form:option value='TEACHER'>teacher</form:option>
        <form:option value='STUDENT'>student</form:option>
    </form:select>
    <form:label path="email">email</form:label>
    <form:input path="email" name="email"/>

    <form:button name="update">update</form:button>
</form:form>
</div>


</body>

<script>
    function deleteUser(userId) {
        var deleteConfirm = confirm("Are you sure you want to delete this user?");

        if(deleteConfirm === true){
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    alert(this.responseText);
                    window.open("/admin/users", "_self");
                }
            };
            xhttp.open("DELETE", "http://localhost:8080/admin/delete/" + userId, true);
            xhttp.send();
        }
    }

    function showEdit() {
        document.getElementById("edit_container").style.visibility = "visible";
    }
</script>


</html>
