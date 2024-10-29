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
        <!-- Filter by Type -->
        <label for="typeId">Filter by Type:</label>
        <select name="typeName" id="typeId">
            <option value="" ${selectedTypeName == null ? 'selected' : ''}>All</option>
            <c:forEach var="type" items="${types}">
                <option value="${type.name}" ${type.name == selectedTypeName ? 'selected' : ''}>${type.name}</option>
            </c:forEach>
        </select>

        <!-- Filter by Status -->
        <label for="statusId">Filter by Status:</label>
        <select name="status" id="statusId">
            <option value="" ${selectedStatus == null ? 'selected' : ''}>All</option>
            <c:forEach var="status" items="${statuses}">
                <option value="${status.settingId}" ${status.settingId == selectedStatus ? 'selected' : ''}>${status.name}</option>
            </c:forEach>
        </select>

        <!-- Filter by Requirement -->
        <label for="reqId">Filter by Requirement:</label>
        <select name="reqTitle" id="reqId">
            <option value="" ${selectedReqTitle == null ? 'selected' : ''}>All</option>
            <c:forEach var="requirement" items="${requirements}">
                <option value="${requirement.title}" ${requirement.title == selectedReqTitle ? 'selected' : ''}>${requirement.title}</option>
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
                <td>
                    <c:choose>
                        <c:when test="${issue.statusId == 1}">Pending</c:when>
                        <c:when test="${issue.statusId == 2}">In-progress</c:when>
                        <c:when test="${issue.statusId == 3}">Done</c:when>
                        <c:when test="${issue.statusId == 4}">Cancelled</c:when>
                        <c:otherwise>Unknown</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <form action="detailIssue" method="get" style="display:inline;">
                        <input type="hidden" name="issueId" value="${issue.issueId}" />
                        <button type="submit" class="button button-secondary">View</button>
                    </form>
                    <form action="updateIssue" method="get" style="display:inline;">
                        <input type="hidden" name="issueId" value="${issue.issueId}" />
                        <button type="submit" class="button button-primary">Edit</button>
                    </form>
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
