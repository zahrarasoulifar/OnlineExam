<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>courses</title>
    <link href="<c:url value="/resources/css/admin_courses.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
</head>
<body>
<div align="right">
    <button onclick="loadDoc()">reload</button>
    <button onclick="location.href='/admin/home/1';">back</button>
</div>
<div class="form_container">
    <div id="message" align="center">${message}</div>
    <form:form modelAttribute="course" action="/course/" method="post">
    <form:input path="title" name="title" required="required" placeholder="course title"/>
    <form:input path="number" name="number" required="required" placeholder="course number"/>
    <form:input path="category" name="category" required="required" placeholder="category"/>

    <form:button name="add">add</form:button>
</form:form>

</div>


<div class="main" id="action_message">click on each course to open course page!</div>
<table id="table">

</table>

<ul id="list">

</ul>
</body>

<script>

    window.onload = function loadDoc() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {

                var data = JSON.parse(this.responseText);
                showData(data);

            }
        };
        xhttp.open("GET", "http://localhost:8080/course/getCourses", true);
        xhttp.send();
    }

    function showData(data) {
        var table = '<tr> <th>course title</th> <th>category</th> <th>number</th></tr>';
        data.map(value =>
        table += '<tr id="' + value.id + '"><td> ' + value.title + '</td><td>'
            + value.category + '</td><td>' + value.number + '</td></tr>'
    )
        document.getElementById("table").innerHTML =table;
        addRowHandlers();
    }

    var list = document.querySelector('ul');
    list.addEventListener('click', function(ev) {
        if (ev.target.tagName === 'LI') {
            window.open("/course/getCoursePage/" + ev.target.id, "_self");
        }
    }, false);


    function addRowHandlers() {
        var table = document.getElementById("table");
        var rows = table.getElementsByTagName("tr");
        for (i = 1; i < rows.length; i++) {
            var currentRow = table.rows[i];
            var createClickHandler = function(row) {
                return function() {
                    window.open("/course/getCoursePage/" + row.id, "_self");

                };
            };
            currentRow.onclick = createClickHandler(currentRow);
        }
    }
</script>

</html>
