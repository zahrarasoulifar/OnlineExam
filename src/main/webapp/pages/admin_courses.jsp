<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>courses</title>
    <link href="<c:url value="/resources/css/admin_courses.css"/>" rel="stylesheet">
</head>
<body>
<div align="right">
    <button onclick="loadDoc()">reload</button>
    <button onclick="location.href='/admin/home';">back</button>
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

    function showData(data){
        var list = '';
        data.map(value =>
        list += '<li id=' + value.id + '> ' + value.title +
            '<br> ' + value.number + ' <br>' + value.category + '</li>'
    );
        document.getElementById("list").innerHTML =list;
    }

    var list = document.querySelector('ul');
    list.addEventListener('click', function(ev) {
        if (ev.target.tagName === 'LI') {
            window.open("/course/getCoursePage/" + ev.target.id, "_self");
        }
    }, false);
</script>

</html>
