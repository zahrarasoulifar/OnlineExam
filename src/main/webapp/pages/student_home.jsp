<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/student_home.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
    <title>student</title>

</head>
<body>
<div align="right" class="navbar">
    <button onclick="location.href='/student/profile/' + ${user.id};">profile</button>
    <button onclick="location.href='/student/home_page';">reload</button>
    <button onclick="location.href='/logout';">logout</button>
</div>
<table id="table">

</table>

</body>
<script>
    window.onload = function () {
        checkVerification("${user_state}");
        loadDoc();
    };

    function checkVerification(status) {
        if (status !== "verified" && status !== "") {
            alert("your account is not verified, you can't continue! try again later!");
            window.open("/logout", "_self");
        }
    }

    function loadDoc() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data);
                addRowHandlers();
            }
        };
        xhttp.open("GET", "http://localhost:8080/student/studentCourses/" + ${user.id}, true);
        xhttp.send();
    }

    function showData(data) {
        var table = '<tr> <th>course title</th> <th>category</th> <th>number</th></tr>';
        data.map(value =>
        table += '<tr id="' + value.id + '"><td> ' +
            value.title + '</td><td>' + value.category + '</td><td>' + value.number + '</td>' +
            '</td></tr>'
    )
        document.getElementById("table").innerHTML = table;
    }

    function addRowHandlers() {
        var table = document.getElementById("table");
        var rows = table.getElementsByTagName("tr");
        for (i = 1; i < rows.length; i++) {
            var currentRow = table.rows[i];
            var createClickHandler = function(row) {
                return function() {
                    window.open("/student/course/" + ${user.id} + '/' + row.id, "_self");
                };
            };
            currentRow.onclick = createClickHandler(currentRow);
        }
    }
</script>

</html>
