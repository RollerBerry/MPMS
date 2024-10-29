<%-- 
    Document   : detailIssue
    Created on : 28 thg 10, 2024, 15:45:00
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Issue Details</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>Issue Details</h2>
    <p><strong>Title:</strong> ${issue.title}</p>
    <p><strong>Type:</strong> ${issue.typeId.name}</p>
    <p><strong>Requirement:</strong> ${issue.reqId.title}</p>
    <p><strong>Deadline:</strong> ${issue.deadline}</p>
    <p><strong>Status:</strong> <c:choose>
        <c:when test="${issue.statusId == 1}">Pending</c:when>
        <c:when test="${issue.statusId == 2}">In-progress</c:when>
        <c:when test="${issue.statusId == 3}">Done</c:when>
        <c:when test="${issue.statusId == 4}">Canceled</c:when>
        <c:otherwise>Unknown</c:otherwise>
    </c:choose></p>
    <p><strong>Assigner:</strong> ${issue.assignerId.fullName}</p>
    <p><strong>Assignee:</strong> ${issue.assigneeId.fullName}</p>
    <p><strong>Status Date:</strong> ${issue.statusDate}</p>
    <p><strong>Description:</strong> ${issue.description}</p>
    <p><strong>Created At:</strong> ${issue.createdAt}</p>
    <p><strong>Created By:</strong> ${issue.createdById.fullName}</p>
    <p><strong>Updated At:</strong> ${issue.updatedAt}</p>
    <p><strong>Updated By:</strong> ${issue.updatedById.fullName}</p>
    <br>
    <a href="listIssue" class="button button-secondary">Back to List</a>
</body>
</html>
