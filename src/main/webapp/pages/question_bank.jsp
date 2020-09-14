<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="<c:url value="/resources/css/question_bank.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
</head>
<body>
<button onclick="location.href='/exam/teacher_edit_page/${examId}';">back</button><br><br>

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
    window.onload = function loadDoc() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                console.log(data);
                showData(data);
            }
        };
        xhttp.open("GET", "http://localhost:8080/question/question_bank/${courseId}", true);
        xhttp.send();
    }

    function showData(data) {
        var table = '<tr> <th>Title</th> <th>Question</th> <th> </th></tr>';
        data.map(value =>
        table += '<tr id="' + value.id + '"><td> ' + value.title +
            '</td><td>' + value.content + '</td><td class="clickable" style="font-weight: bold;" ' +
            'onclick="addQuestionToExam(' + value.id + ',' + ${examId} + ')">Add </td></tr>'
    )
        document.getElementById("table").innerHTML = table;
    }

    function addQuestionToExam(questionId, examId) {
        var point = window.prompt("enter point of the question for this exam:");
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                alert(this.responseText);
            }
        };
        xhttp.open("PUT", "/question/add_existed_question/" + examId +"/" + questionId +"/" + point, true);
        xhttp.send();
    }
</script>

</html>
