<%-- 
    Document   : confirmDelete
    Created on : 28 thg 10, 2024, 15:15:12
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Confirm Delete Issue</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .confirm-container {
            text-align: center;
            margin-top: 50px;
            color: #dc3545;
        }
        .button {
            margin: 10px;
        }
    </style>
</head>
<body>
    <div class="confirm-container">
        <h2>Confirm Deletion</h2>
        <p>Are you sure you want to delete the following issue?</p>
        <p><strong>Title:</strong> ${issue.title}</p>
        <p><strong>Description:</strong> ${issue.description}</p>
        <p><strong>Assigned To:</strong> ${issue.assigneeId.fullName}</p>
        <p><strong>Deadline:</strong> <fmt:formatDate value="${issue.deadline}" pattern="dd-MM-yyyy" /></p>

        <form action="deleteIssue" method="post">
            <input type="hidden" name="issueId" value="${issue.issueId}" />
            <button type="submit" class="button button-danger">Yes, Delete</button>
            <a href="listIssue" class="button button-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>
