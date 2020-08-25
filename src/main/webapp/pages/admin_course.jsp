<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link href="<c:url value="/resources/css/admin_course.css"/>" rel="stylesheet">
    <title>course</title>

</head>
<body>
<div align="right">
    <button onclick="loadAddingData()">add users </button>
    <button onclick="deleteCourse(${course.id})">delete course</button>
    <button onclick="location.href='/course/getCoursePage/' + ${course.id};">reload</button>
    <button onclick="location.href='/course/all';">back</button>
</div>


<p class="main" id="list_action_message">click on each user to delete from course!</p>
<div>
    <h3>teachers</h3>
    <ul id="teachers">
    </ul>

    <h3>students</h3>
    <ul id="students">
    </ul>

</div>

</body>

<script>
    var isDeletable = true;
    window.onload = function (ev) {
        loadStudents();
        loadTeachers();
        deleteOperation();
    }

    function loadTeachers() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data, "teachers");
            }
        };
        xhttp.open("GET", "http://localhost:8080/course/getCourseTeachers/" + ${course.id}, true);
        xhttp.send();
    }

    function loadStudents() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data, "students");

            }
        };
        xhttp.open("GET", "http://localhost:8080/course/getCourseStudents/" + ${course.id}, true);
        xhttp.send();
    }

    function showData(data, listName){
        var list = '';
        data.map(value =>
        list += '<li id=' + value.id + '> ' + value.firstName +
            '<br>' + value.lastName + '<br>' + value.role + '</li>'
    );

        document.getElementById(listName).innerHTML =list;
    }


    function loadAddingData() {
        loadTeachersToAdd();
        loadStudentsToAdd();
        addOperation();
        isDeletable = false;
        document.getElementById("list_action_message").innerHTML = "click on each user to add to the course";
    }
    function loadTeachersToAdd() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data, "teachers");
            }
        };
        xhttp.open("GET", "http://localhost:8080/course/teachersToAdd/" + ${course.id}, true);
        xhttp.send();
    }

    function loadStudentsToAdd() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data, "students");
            }
        };
        xhttp.open("GET", "http://localhost:8080/course/studentsToAdd/" + ${course.id}, true);
        xhttp.send();
    }


    function addOperation() {
        var list = document.querySelectorAll('ul');
        for (i = 0; i < list.length; i++) {
            list[i] = list[i].cloneNode(true);
            list[i].addEventListener('click', function addEvent(ev) {
                if (ev.target.tagName === 'LI') {
                    ev.target = ev.target.cloneNode(true);
                    ev.target.classList.add('checked');
                    addUserToCourse(ev.target.id);
                }

            }, false);
        }
    }

    function addUserToCourse(userId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                console.log(this.responseText);
            }
        };
        xhttp.open("PUT", "http://localhost:8080/course/addUser/" + ${course.id} + "/" + userId, true);
        xhttp.send();
    }

    function deleteOperation() {
        var list = document.querySelectorAll('ul');
        for (i = 0; i < list.length; i++) {
            list[i].addEventListener('click', function deleteEvent(ev) {
                if (ev.target.tagName === 'LI') {
                    if (!ev.target.classList.contains('checked')) {
                        if (isDeletable === true) {
                            var deleteConfirm = confirm("are you sure you want to delete this user from course?");
                            if (deleteConfirm === true) {
                                ev.target.classList.add('checked');
                                deleteUserFromCourse(ev.target.id);
                            }
                        }
                    }
                }

            }, false);

        }
    }

    function deleteUserFromCourse(userId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                console.log(this.responseText);
            }
        };
        xhttp.open("DELETE", "http://localhost:8080/course/deleteUser/" + ${course.id} + "/" + userId, true);
        xhttp.send();
    }

    function deleteCourse(courseId) {
        var deleteConfirm = confirm("Are you sure you want to delete this course?");

        if (deleteConfirm === true) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    alert(this.responseText);
                    window.open("/course/all", "_self");
                }
            };
            xhttp.open("DELETE", "http://localhost:8080/course/delete/" + courseId , true);
            xhttp.send();
        }
    }

</script>


</html>
