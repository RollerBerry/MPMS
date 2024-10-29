<%-- 
    Document   : insertIssue
    Created on : 28 thg 10, 2024
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Insert Issue</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>Add New Issue</h2>
    <form action="insertIssue" method="post">
        <label for="title">Title:</label>
        <input type="text" name="title" id="title" required><br><br>

        <label for="typeId">Type:</label>
        <select name="typeId" id="typeId" required>
            <c:forEach var="type" items="${types}">
                <option value="${type.settingId}">${type.name}</option>
            </c:forEach>
        </select><br><br>

        <label for="reqId">Requirement:</label>
        <select name="reqId" id="reqId" required>
            <c:forEach var="requirement" items="${requirements}">
                <option value="${requirement.reqId}">${requirement.title}</option>
            </c:forEach>
        </select><br><br>

        <label for="assignerId">Assigner:</label>
        <select name="assignerId" id="assignerId" required>
            <c:forEach var="user" items="${users}">
                <option value="${user.userId}">${user.fullName}</option>
            </c:forEach>
        </select><br><br>

        <label for="assigneeId">Assignee:</label>
        <select name="assigneeId" id="assigneeId" required>
            <c:forEach var="user" items="${users}">
                <option value="${user.userId}">${user.fullName}</option>
            </c:forEach>
        </select><br><br>

        <label for="deadline">Deadline:</label>
        <input type="date" name="deadline" id="deadline" required><br><br>

        <label for="statusId">Status:</label>
        <select name="statusId" id="statusId" required>
            <c:forEach var="status" items="${statuses}">
                <option value="${status.settingId}">${status.name}</option>
            </c:forEach>
        </select><br><br>

        <label for="description">Description:</label>
        <textarea name="description" id="description"></textarea><br><br>

        <input type="hidden" name="createdById" value="${sessionScope.currentUser.userId}">

        <button type="submit" class="button button-primary">Add Issue</button>
    </form>
    <br>
    <a href="listIssue" class="button button-secondary">Back to List</a>
</body>
</html>
