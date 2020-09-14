
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>student profile</title>
</head>
<body>
    <button onclick="location.href='/student/home_page/${user.id}';">back</button>
    <br><br>
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
