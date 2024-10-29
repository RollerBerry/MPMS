<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mini Project Management System</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .notlogin {
                min-height: 100vh;
                background-image: url("https://www.baypmtech.com/wp-content/uploads/2014/01/847x480-14-IBM-023_illustration_analytics_3.png");
            }
            .overlay {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
            }
            .sec {
                min-height: 100vh;
            }
            .sidebar {
                height: 100vh; /* Chiều cao của sidebar */
                position: fixed; /* Giữ sidebar cố định */
                top: 0; /* Đặt ở phía trên cùng */
                left: 0; /* Đặt ở phía bên trái */
                width: 250px; /* Chiều rộng của sidebar */
                background-color: #343a40; /* Màu nền của sidebar */
                padding: 20px; /* Padding bên trong sidebar */
                color: white; /* Màu chữ trong sidebar */
                display: none; /* Bắt đầu ẩn sidebar */
            }
            .sidebar a {
                color: white; /* Màu chữ các liên kết */
                text-decoration: none; /* Bỏ gạch chân */
            }
            .sidebar a:hover {
                text-decoration: underline; /* Hiệu ứng khi hover */
            }
            .content {
                margin-left: 270px; /* Đẩy nội dung sang phải để không bị che bởi sidebar */
                padding: 20px; /* Padding cho nội dung */
            }
            .toggle-button {
                margin: 10px; /* Khoảng cách cho nút toggle */
            }
        </style>
        <script>
            function toggleSidebar() {
                const sidebar = document.getElementById("sidebar");
                if (sidebar.style.display === "none" || sidebar.style.display === "") {
                    sidebar.style.display = "block"; // Hiện sidebar
                } else {
                    sidebar.style.display = "none"; // Ẩn sidebar
                }
            }
        </script>
    </head>
    <body>
        <section class="sec">
            <c:if test="${sessionScope.user == null}">
                <div class="container-fluid notlogin">
                    <div class="overlay d-flex justify-content-center align-items-center" style="flex-direction: column;">
                        <div>
                            <p style="color: white; font-size: 50px">LOGIN TO USE</p><br>
                        </div>
                        <div>
                            <a type="button" class="btn btn-success" href="login">Login</a>    
                            <a type="button" class="btn btn-warning" href="Register">Register</a>    
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <%@include file="header.jsp" %>
                <c:choose>
                    <c:when test="${sessionScope.user.roleId == 5}">
                        <button class="btn btn-primary toggle-button" onclick="toggleSidebar()">Toggle Sidebar</button>
                        <div id="sidebar" class="sidebar">
                            <h4>Dashboard</h4>
                            <ul class="list-unstyled">
                                <li><a href="ListUser">Manage User</a></li>
                                <li><a href="requirement-list">Requirement List</a></li>
                                <li><a href="issue-list">Issue List</a></li>
                                <li><a href="project-list">Project List</a></li>
                            </ul>
                        </div>
                        <div class="content">
                            <h2>Welcome to the Dashboard</h2>
                            <p>As an Admin, you can manage users and view all items.</p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.user.roleId == 6}">
                        <div class="content">
                            <h2>Welcome User</h2>
                            <p>As a User, you can view and manage your issues.</p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.user.roleId == 7}">
                        <div class="content">
                            <h2>Welcome Project Manager</h2>
                            <p>As a Project Manager, you can manage your projects.</p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.user.roleId == 8}">
                        <div class="content">
                            <h2>Welcome Team Member</h2>
                            <p>As a Team Member, you can view requirements assigned to you.</p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.user.roleId == 9}">
                        <div class="content">
                            <h2>Welcome Team Member</h2>
                            <p>As a Team Member, you can view requirements assigned to you.</p>
                        </div>
                    </c:when>
                </c:choose>
            </c:if>
        </section>
        <%@include file="footer.html" %>
    </body>
</html>