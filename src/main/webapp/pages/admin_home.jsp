<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin</title>
    <link href="<c:url value="/resources/css/admin_home.css"/>" rel="stylesheet">

</head>
<body>

<div class="navbar" >
        <p class="main">hello ${user.firstName} !</p>
        <button onclick="location.href='/logout';">logout</button>
        <button onclick="location.href='/admin/home';">reload</button>
        <button onclick="location.href='/admin/users';">users</button>
        <button onclick="location.href='/course/all';">courses</button>

</div>
<br>
<p class="main" id="message">unverified users! click on each user to verify!</p>
<p  id="verify_message"></p>
<ul id="list">

</ul>


</body>

<script>


    window.onload = function getUnverifiedUsers() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data);
            }
        };
        xhttp.open("GET", "http://localhost:8080/user/getUnverifiedUsers", true);
        xhttp.send();
    }

    function showData(data){
        var list = '';
        data.map(value =>
        list += '<li id=' + value.id + '> ' + value.firstName +
            '   ' + value.lastName + '  |  ' + value.email + ' </li>'
    );

        document.getElementById("list").innerHTML =list;
    }

    var list = document.querySelector('ul');
    list.addEventListener('click', function(ev) {
        if (ev.target.tagName === 'LI') {
            ev.target.classList.add('checked');
            verify(ev.target.id);

        }
    }, false);

    function verify(userId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                if (this.responseText == true) {
                    document.getElementById('verify_message').innerHTML = "user verified."
                }
                if (this.responseText == false) {
                    document.getElementById('verify_message').innerHTML = "user verification failed."
                }
            }
        };
        xhttp.open("GET", "http://localhost:8080/user/verify?userId=" + userId, true);
        xhttp.send();
    }

</script>

</html>