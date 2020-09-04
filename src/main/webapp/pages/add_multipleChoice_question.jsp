<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add question</title>
    <link href="<c:url value="/resources/css/add_multipleChoice_question.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/labels.css"/>" rel="stylesheet">
</head>
<body>
<div class="main">
    <h3 align="center" class="title">Add New Descriptive Question!</h3>
    <form:form class="form1" modelAttribute="question" action="/question/add_multipleChoice/${examId}/${courseId}" method="post">
        <form:input class="un" path="title" name="title" required="required"
                    placeholder="title (max:15 characters)" maxlength="15"/>
        <form:textarea class="un" path="content" name="description" required="required" placeholder="question"/>
        <div align="center" style="margin-bottom: 10px">
            <label class="label" >Choices (choose right choice)</label>
            <select style="margin-left: 0.5vh" name="right_choice" id="choice_container" required>
                <option name="op" disabled>Question Choices</option>
            </select>
        </div>
        <input id="choice" class="un" placeholder="enter the new choice here...">
        <label onclick="addChoice()" id="addLabel">add choice!</label>
        <div align="center">
            <p class="label" style="margin-bottom: 1px; margin-top: 45px;">
                Add this question to course question bank?</p><br>
            <label class="label" for="yes">YES</label>
            <input type="radio" id="yes" name="bankStatus" value="YES">
            <label class="label" for="no">NO</label>
            <input type="radio" id="no" name="bankStatus" value="NO">
        </div><br>
        <input type="number" name="point" class="un" placeholder="question point">
        <form:button name="add">add Question</form:button>
        <div id="hidden_inputs">
            <input id="choicesNumber" name="choicesNumber" value="0" type="hidden">
        </div>
    </form:form>

</div>



</body>

<script>
    function addChoice() {
        var choiceNumber = document.getElementById("choicesNumber").value;
        choiceNumber++;
        document.getElementById("choicesNumber").value = choiceNumber;
        var choice = document.getElementById("choice").value;
        var choices = document.getElementById("choice_container").innerHTML;
        var choicesInputs = document.getElementById("hidden_inputs").innerHTML;
        choices += "<option>" + choiceNumber + "." + choice + "</option>";
        choicesInputs += "<input type='hidden' value="  + choice + " name=" + choiceNumber + " >";
        document.getElementById("choice_container").innerHTML = choices;
        document.getElementById("hidden_inputs").innerHTML = choicesInputs;
        document.getElementById("choice").textContent = "";
    }
</script>

</html>
