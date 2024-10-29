<%-- 
    Document   : updateIssue
    Created on : 27 thg 10, 2024, 17:41:17
    Author     : mituz
--%>

<%-- 
    Document   : updateIssue
    Created on : 28 thg 10, 2024
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update Issue</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>Update Issue</h2>
    <form action="updateIssue" method="post">
        <input type="hidden" name="issueId" value="${issue.issueId}" />

        <label for="title">Title:</label>
        <input type="text" name="title" id="title" value="${issue.title}" required><br><br>

        <label for="description">Description:</label>
        <textarea name="description" id="description">${issue.description}</textarea><br><br>

        <!-- Dropdown for Type Selection -->
        <label for="typeId">Type:</label>
        <select name="typeId" id="typeId">
            <c:forEach var="type" items="${types}">
                <option value="${type.settingId}" ${type.settingId == issue.typeId.settingId ? 'selected' : ''}>${type.name}</option>
            </c:forEach>
        </select><br><br>

        <!-- Dropdown for Requirement Selection -->
        <label for="reqId">Requirement:</label>
        <select name="reqId" id="reqId">
            <c:forEach var="req" items="${requirements}">
                <option value="${req.reqId}" ${req.reqId == issue.reqId.reqId ? 'selected' : ''}>${req.title}</option>
            </c:forEach>
        </select><br><br>

        <!-- Dropdown for Assigner Selection -->
        <label for="assignerId">Assigner:</label>
        <select name="assignerId" id="assignerId">
            <c:forEach var="user" items="${users}">
                <option value="${user.userId}" ${user.userId == issue.assignerId.userId ? 'selected' : ''}>${user.fullName}</option>
            </c:forEach>
        </select><br><br>

        <!-- Dropdown for Assignee Selection -->
        <label for="assigneeId">Assignee:</label>
        <select name="assigneeId" id="assigneeId">
            <c:forEach var="user" items="${users}">
                <option value="${user.userId}" ${user.userId == issue.assigneeId.userId ? 'selected' : ''}>${user.fullName}</option>
            </c:forEach>
        </select><br><br>

        <!-- Dropdown for Status Selection -->
        <label for="statusId">Status:</label>
        <select name="statusId" id="statusId">
            <c:forEach var="status" items="${statuses}">
                <option value="${status.settingId}" ${status.settingId == issue.statusId ? 'selected' : ''}>${status.name}</option>
            </c:forEach>
        </select><br><br>

        <!-- Input for Deadline -->
        <label for="deadline">Deadline:</label>
        <input type="date" name="deadline" id="deadline" value="${issue.deadline}" required><br><br>

        <button type="submit" class="button button-primary">Update Issue</button>
    </form>
</body>
</html>


