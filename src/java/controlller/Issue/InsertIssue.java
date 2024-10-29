package controlller.Issue;

import dal.IssueDAO;
import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for inserting a new issue.
 */
public class InsertIssue extends HttpServlet {
    private final IssueDAO issueDao = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt các danh sách cần thiết cho dropdown (users, requirements, types, statuses)
        request.setAttribute("users", issueDao.getAllUsers());
        request.setAttribute("requirements", issueDao.getAllRequirement());
        request.setAttribute("types", issueDao.getSettingsByType(6)); // Giả định rằng type 6 là dành cho loại Issue
        request.setAttribute("statuses", issueDao.getTypeIssue());   // Lấy danh sách status cho Issue

        request.getRequestDispatcher("/WEB-INF/View/Issue/insertIssue.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy các thông tin từ form
            String title = request.getParameter("title");
            int typeId = Integer.parseInt(request.getParameter("typeId"));
            int reqId = Integer.parseInt(request.getParameter("reqId"));
            int assignerId = Integer.parseInt(request.getParameter("assignerId"));
            int assigneeId = Integer.parseInt(request.getParameter("assigneeId"));
            Date deadline = Date.valueOf(request.getParameter("deadline"));  // Chuyển đổi sang Date SQL
            int statusId = Integer.parseInt(request.getParameter("statusId"));
            String description = request.getParameter("description");
            int createdById = Integer.parseInt(request.getParameter("createdById")); // ID của người tạo

            // Thêm Issue mới vào database
            boolean inserted = issueDao.insertIssue(title, typeId, reqId, assignerId, assigneeId, deadline, statusId, description, createdById);

            if (inserted) {
                response.sendRedirect("listIssue"); // Điều hướng về trang danh sách nếu thêm thành công
            } else {
                request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response); // Chuyển tiếp đến trang lỗi nếu thất bại
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response); // Chuyển tiếp đến trang lỗi nếu có ngoại lệ
        }
    }
}
