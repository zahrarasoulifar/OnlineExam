<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>course</title>
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/modal.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/body.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/messages.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/buttons.css"/>" rel="stylesheet">
</head>
<body>
<div class="navbar" >
    <p class="message">${course.title}</p>
    <button onclick="location.href='/student/home_page/${user.id}';">back</button>
    <button onclick="location.href='/student/points/${user.id}';">points</button>
</div>

<div>
    <h3>current exams</h3>
    <table id="current_exams">

    </table>
</div>



<div>
    <h3>future exams</h3>
    <table id="future_exams">

    </table>
</div>

<div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <p id="info">Some text in the Modal..</p>
    </div>

</div>

</body>
<script>
    window.onload = function () {
        loadCurrentExams();
        loadFutureExams();
    };

    function loadCurrentExams() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data, "current_exams");
                addRowHandlers("current_exams");
            }
        };
        xhttp.open("GET", "http://localhost:8080/exam/current_exams/" + ${course.id}, true);
        xhttp.send();
    }

    function loadFutureExams() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                showData(data, "future_exams");
                addModalHandler("future_exams");
            }
        };
        xhttp.open("GET", "http://localhost:8080/exam/future_exams/" + ${course.id}, true);
        xhttp.send();
    }



    function showData(data, tableId) {
        var table = '<tr> <th>Title</th> <th>Time</th> <th>Start</th> <th>End</th></tr>';
        data.map(value =>
        table += '<tr id="' + value.id + '"><td> ' + value.title +
            '</td><td>' + value.time + '</td><td>'
            + new Date(value.startDate).toLocaleDateString() + ' '
            + new Date(value.startDate).toLocaleTimeString() + '</td><td>'
            + new Date(value.endDate).toLocaleDateString() + ' '
            + new Date(value.endDate).toLocaleTimeString() + '</tr>'
    )
        document.getElementById(tableId).innerHTML = table;
    }

    function addRowHandlers(tableId) {
        var table = document.getElementById(tableId);
        var rows = table.getElementsByTagName("tr");
        for (i = 1; i < rows.length; i++) {
            var currentRow = table.rows[i];
            var createClickHandler = function(row) {
                return function() {
                    window.open("/exam/student_exam_page/" + row.id + "/${user.id}", "_self");
                };
            };
            currentRow.onclick = createClickHandler(currentRow);
        }
    }

    function addModalHandler(tableId) {
        var modal = document.getElementById("myModal");
        var table = document.getElementById(tableId);
        var rows = table.getElementsByTagName("tr");
        for (i = 1; i < rows.length; i++) {
            var currentRow = table.rows[i];
            var createClickHandler = function(row) {
                return function() {
                    modal.style.display = "block";
                    getExamData(row.id);
                };
            };
            currentRow.onclick = createClickHandler(currentRow);
        }
        var span = document.getElementsByClassName("close")[0];
        span.onclick = function() {
            modal.style.display = "none";
        }
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    }

    function getExamData(examId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                console.log(data);

            }
        };
        xhttp.open("GET", "http://localhost:8080/" + examId, true);
        xhttp.send();
    }

    function showExamData(data) {
        document.getElementById("info").innerHTML = "title: " + data.title + "</br>" +
            "description: " + data.description + "</br>" + "Time: " + data.time + " minutes" + "</br>" +
            "course title: " + data.course.title + "</br>" +
            "teacher name: " + data.teacher.firstName + " " + data.teacher.lastName +  "</br>";
    }
</script>
</html>
