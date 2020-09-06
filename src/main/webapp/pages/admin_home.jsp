<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin</title>
    <link href="<c:url value="/resources/css/admin_home.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">

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

<table id="table">

</table>


</body>

<script>


    window.onload = function getUnverifiedUsers() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                // showData(data);
                showDataT(data);
            }
        };
        xhttp.open("GET", "http://localhost:8080/user/getUnverifiedUsers", true);
        xhttp.send();
    }

    function showDataT(data) {
        var table = '<tr> <th>Name</th> <th>role</th> <th>email</th> <th> </th></tr>';
        data.map(value =>
        table += '<tr id="' + value.id + '"><td> ' + value.firstName + ' ' + value.lastName +
            '</td><td>' + value.role + '</td><td>' + value.email + '</td><td class="clickable" style="font-weight: bold;" ' +
            'onclick="verify(' + value.id + ')">verify </td></tr>'
    )
        document.getElementById("table").innerHTML = table;
    }

    function changeVerifyIcon(id) {
        var row = document.getElementById(id);
        console.log(row);
        var cells = row.getElementsByTagName("td");
        console.log(cells);
        console.log(cells[3]);
        cells[3].textContent = "✔";
    }


    function verify(userId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                changeVerifyIcon(userId);
            }
        };
        xhttp.open("GET", "http://localhost:8080/user/verify?userId=" + userId, true);
        xhttp.send();
    }

</script>

</html>