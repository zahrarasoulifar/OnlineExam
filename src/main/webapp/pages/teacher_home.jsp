<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/teacher_home.css"/>" rel="stylesheet">

</head>
<body>

<div class="navbar" >
    <p class="main">hello ${user.firstName} !</p>
    <button onclick="location.href='/logout';">logout</button>
    <button onclick="location.href='/teacher/home';">reload</button>
    <button onclick="location.href='/teacher/profile/' + ${user.id};">profile</button>
</div>
<h3 align="center">${user_state}</h3>
<div>
    <table id="table">
        <tr>
            <th>course title</th>
            <th>category</th>
            <th>number</th>
        </tr>
    </table>
</div>
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
        xhttp.open("GET", "http://localhost:8080/teacher/teacherCourses/" + ${user.id}, true);
        xhttp.send();
    }



    function showData(data) {
        var table = '<tr> <th>course title</th> <th>category</th> <th>number</th></tr>';
        data.map(value =>
        table += '<tr id="' + value.id + '"><td> ' + value.title + '</td><td>' + value.category + '</td><td>' + value.number + '</td></tr>'
    )
        document.getElementById("table").innerHTML =table;
    }

    function addRowHandlers() {
        var table = document.getElementById("table");
        var rows = table.getElementsByTagName("tr");
        for (i = 1; i < rows.length; i++) {
            var currentRow = table.rows[i];
            var createClickHandler = function(row) {
                return function() {
                    window.open("/teacher/course/" + ${user.id} + '/' + row.id, "_self");
                };
            };
            currentRow.onclick = createClickHandler(currentRow);
        }
    }
</script>

</html>
