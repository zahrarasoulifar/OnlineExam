<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link href="<c:url value="/resources/css/admin_course.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
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
    <table id="teachers">

    </table>

    <h3>students</h3>
    <table id="students">

    </table>

</div>

</body>

<script>
    var isDeletable = true;
    window.onload = function (ev) {
        loadStudents();
        loadTeachers();
    }

    function loadTeachers() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data, "teachers");
                deleteOperation();
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

    function showData(data, tableId) {
        var table = '<tr> <th>first name</th> <th>last name</th> <th>role</th></tr>';
        data.map(value =>
        table += '<tr id="' + value.id + '"><td> ' + value.firstName + '</td><td>'
            + value.lastName + '</td><td>' + value.role + '</td></tr>'
    )
        document.getElementById(tableId).innerHTML =table;
    }

    function loadAddingData() {
        loadTeachersToAdd();
        loadStudentsToAdd();
        isDeletable = false;
        document.getElementById("list_action_message").innerHTML = "click on each user to add to the course";
    }
    function loadTeachersToAdd() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data, "teachers");
                addOperation();
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
                addOperation();
            }
        };
        xhttp.open("GET", "http://localhost:8080/course/studentsToAdd/" + ${course.id}, true);
        xhttp.send();
    }


    function addOperation() {
        var tables = document.getElementsByTagName("table");
        for (i = 0; i < tables.length; i++) {
            var rows = tables[i].getElementsByTagName("tr");
            for (j = 1; j < rows.length; j++) {
                var currentRow = tables[i].rows[j];
                var createClickHandler = function (row) {
                    return function () {
                        row = row.cloneNode(true);
                        addUserToCourse(row.id);
                        document.getElementById(row.id).style.backgroundColor = "#888";
                        document.getElementById(row.id).classList.add('checked');
                    };
                };
                currentRow.onclick = createClickHandler(currentRow);
            }
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
        var tables = document.getElementsByTagName("table");
        for (i = 0; i < tables.length; i++) {
            var rows = tables[i].getElementsByTagName("tr");
            for (j = 1; j < rows.length; j++) {
                var currentRow = tables[i].rows[j];
                var createClickHandler = function (row) {
                    return function () {
                        if (!row.classList.contains('checked')) {
                            if (isDeletable === true) {
                                var deleteConfirm = confirm("are you sure you want to delete this user from course?");
                                if (deleteConfirm === true) {
                                    row.classList.add('checked');
                                    deleteUserFromCourse(row.id);
                                }
                            }
                        }

                    };
                };
                currentRow.onclick = createClickHandler(currentRow);
            }
        }
    }

    function deleteUserFromCourse(userId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                console.log(this.responseText);
                window.open("/course/getCoursePage/" + ${course.id}, "_self");
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
