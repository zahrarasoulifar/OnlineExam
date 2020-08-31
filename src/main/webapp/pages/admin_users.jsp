<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="<c:url value="/resources/css/admin_users.css"/>" rel="stylesheet">
    <title>users</title>
</head>
<body>
<div align="right">
    <button onclick="location.href='/admin/signup_page';">add user</button>
    <button onclick="location.href='/admin/home';">back</button>
</div>
<div class="form_container" align="center">
        <input id="firstName" name="firstName" placeholder="first name"/>
        <input id="lastName" name="lastName" placeholder="last name"/>
        <select id="role">
            <option value=''>   </option>
            <option value='TEACHER'>teacher</option>
            <option value='STUDENT'>student</option>
        </select>
        <input id="email" name="email" placeholder="email"/>

    <button onclick="search()">do search!</button>
</div>
<br>
<div class="main" id="action_message">click on each user to open user page!</div>
<ul id="list">

</ul>

</body>

<script>
    window.onload =  function getUnverifiedUsers() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data);

            }
        };
        xhttp.open("GET", "http://localhost:8080/admin/getVerifiedUsers", true);
        xhttp.send();
    }

    function showData(data){
        var list = '';
        data.map(value =>
        list += '<li id=' + value.id + '> ' + value.firstName +
            '<br> ' + value.lastName + ' <br>' + value.email + '<br> ' + value.role + '</li>'
    );
        document.getElementById("list").innerHTML =list;


    }

    var list = document.querySelector('ul');
    list.addEventListener('click', function(ev) {
        if (ev.target.tagName === 'LI') {
            ev.target.classList.add('checked');
            console.log(ev.target.id);
            window.open("/admin/user/" + ev.target.id, "_self");
        }
    }, false);


    function search(){
        var search = {
            "firstName" : document.getElementById("firstName").value,
            "lastName" : document.getElementById("lastName").value,
            "email" : document.getElementById("email").value,
            "role" : document.getElementById("role").value
        }

        $.ajax({
            type : "POST",
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            url : "http://localhost:8080/academic/search/0/10",
            data :
                JSON.stringify(search),
            success : function(result) {
                showData(result);
            },
            done : function(e) {
                console.log("DONE");
            }
        });
    }


</script>


</html>
