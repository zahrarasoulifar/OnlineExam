<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin</title>
    <link href="<c:url value="/resources/css/admin_home.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/tables.css"/>" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

<div class="navbar" >
        <p class="main">hello ${user.firstName} !</p>
        <button onclick="location.href='/logout';">logout</button>
        <button onclick="location.href='/admin/home/1';">reload</button>
        <button onclick="location.href='/admin/users';">users</button>
        <button onclick="location.href='/course/all';">courses</button>
</div>
<table id="table">

</table><br>
<div class="footer">
    <div align="center">
        <button onclick="verifyAll()" style="float: none;">Verify Users</button>
    </div><br>
    <div class="paging_div" align="center">
        <button id="pre" class="paging_btn" style="float: none;" onclick="location.href='/admin/home/${pageNumber - 1}';"><</button>
        <span>${pageNumber}</span>
        <button style="float: none;" class="paging_btn" onclick="location.href='/admin/home/${pageNumber + 1}';">></button>
    </div><br>
</div>
</body>

<script>
    window.onload = function () {
        getUnverifiedUsers("${pageNumber}");
        disablePreviousButton("${pageNumber}");
    }

    function getUnverifiedUsers(pageNumber) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(this.responseText);
                if (data.length === 0) {
                    window.open("/admin/home/1", "_self");
                }
                else {
                    showData(data);
                }

            }
        };
        xhttp.open("GET", "http://localhost:8080/user/getUnverifiedUsers/" + pageNumber + "/5", true);
        xhttp.send();
    }
    
    function disablePreviousButton(pageNumber) {
        if (pageNumber === "1") {
            document.getElementById("pre").disabled = true;
        }
    }

    function showData(data) {
        var table = '<tr> <th>Name</th> <th>role</th> <th>email</th> <th>' +
            ' <input type="checkbox" id="check_all"></th></tr>';
        data.map(value =>
        table += '<tr id="' + value.id + '"><td> ' + value.firstName + ' ' + value.lastName +
            '</td><td>' + value.role + '</td><td>' + value.email + '</td><td><input type="checkbox" name="user_verification" value="'
            + value.id + '"></td></tr>'
    )
        document.getElementById("table").innerHTML = table;
        addCheckAllOnClick();
    }

    function addCheckAllOnClick() {
        document.getElementById('check_all').onclick = function () {
            var checkboxes = document.getElementsByName('user_verification');
            for (var checkbox of checkboxes) {
                checkbox.checked = this.checked;
            }
        }
    }
    function verifyAll() {
            var checkboxes = document.getElementsByName('user_verification');
            var idList = [];
            for (var checkbox of checkboxes) {
                if (checkbox.checked) {
                    idList.push(parseInt(checkbox.value))
                }
            }

        $.ajax({
            type : "PUT",
            contentType : 'application/json; charset=utf-8',
            url : "http://localhost:8080/user/verify_list",
            data : JSON.stringify(idList),
            success : function(result) {
                window.open("/admin/home/1", "_self");
            },
            done : function(e) {
                console.log("DONE");
            }
        });
    }

</script>

</html>