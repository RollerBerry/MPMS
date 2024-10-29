<%-- 
    Document   : listRequirement
    Created on : 28 thg 10, 2024, 14:13:12
    Author     : mituz
--%>

<%-- 
    Document   : listIssue
    Created on : 28 thg 10, 2024, 14:13:12
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of Issues</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>List of Issues</h2>

    <!-- Search Form -->
    <form action="searchIssue" method="get">
        <label for="searchTitle">Search by Title:</label>
        <input type="text" name="title" id="searchTitle" placeholder="Enter title to search" />
        <button type="submit" class="button button-primary">Search</button>
    </form>
    
    <!-- Filter Form -->
    <form action="filterIssue" method="get">
        <label for="typeId">Filter by Type:</label>
        <select name="typeId" id="typeId">
            <option value="-1" ${selectedTypeId == -1 ? 'selected' : ''}>All</option>
            <c:forEach var="type" items="${types}">
                <option value="${type.settingId}" ${type.settingId == selectedTypeId ? 'selected' : ''}>${type.name}</option>
            </c:forEach>
        </select>

        <label for="statusId">Filter by Status:</label>
        <select name="statusId" id="statusId">
            <option value="-1" ${selectedStatusId == -1 ? 'selected' : ''}>All</option>
            <c:forEach var="status" items="${statuses}">
                <option value="${status.settingId}" ${status.settingId == selectedStatusId ? 'selected' : ''}>${status.name}</option>
            </c:forEach>
        </select>

        <label for="reqId">Filter by Requirement:</label>
        <select name="reqId" id="reqId">
            <option value="-1" ${selectedReqId == -1 ? 'selected' : ''}>All</option>
            <c:forEach var="requirement" items="${requirements}">
                <option value="${requirement.reqId}" ${requirement.reqId == selectedReqId ? 'selected' : ''}>${requirement.title}</option>
            </c:forEach>
        </select>

        <button type="submit" class="button button-warning">Filter</button>
    </form>

    <br>

    <!-- Table of Issues -->
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Type</th>
            <th>Requirement</th>
            <th>Deadline</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="issue" items="${issues}">
            <tr>
                <td>${issue.issueId}</td>
                <td>${issue.title}</td>
                <td>${issue.typeId.name}</td>
                <td>${issue.reqId.title}</td>
                <td>${issue.deadline}</td>
                <td>${issue.statusId == 1 ? 'Active' : 'Inactive'}</td>
                <td>
                    <a href="detailIssue?issueId=${issue.issueId}" class="button button-secondary">View</a>
                    <a href="updateIssue?issueId=${issue.issueId}" class="button button-primary">Edit</a>
                    <form action="deleteIssue" method="post" style="display:inline;">
                        <input type="hidden" name="issueId" value="${issue.issueId}" />
                        <button type="submit" class="button button-danger" onclick="return confirm('Are you sure you want to delete this issue?')">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br>
    <a href="insertIssue" class="button button-primary">Add New Issue</a>
</body>
</html>
