package controlller.Issue;

import dal.IssueDAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Issue;
import model.Requirement;
import model.Setting;
import model.User;

public class UpdateIssue extends HttpServlet {
    private final IssueDAO issueDao = new IssueDAO();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày phù hợp với dữ liệu nhập vào

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int issueId = Integer.parseInt(request.getParameter("issueId"));
        Issue issue = issueDao.getIssueDetail(issueId);

        if (issue != null) {
            List<Setting> types = issueDao.getSettingsByType(6); // Giả sử type 6 là cho loại của issue
            List<Setting> statuses = issueDao.getTypeIssue();
            List<User> users = issueDao.getAllUsers();
            List<Requirement> requirements = issueDao.getAllRequirement();

            request.setAttribute("issue", issue);
            request.setAttribute("types", types);
            request.setAttribute("statuses", statuses);
            request.setAttribute("users", users);
            request.setAttribute("requirements", requirements);

            request.getRequestDispatcher("/WEB-INF/View/Issue/updateIssue.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int issueId = Integer.parseInt(request.getParameter("issueId"));
            String title = request.getParameter("title");
            int typeId = Integer.parseInt(request.getParameter("typeId"));
            int reqId = Integer.parseInt(request.getParameter("reqId"));
            int assignerId = Integer.parseInt(request.getParameter("assignerId"));
            int assigneeId = Integer.parseInt(request.getParameter("assigneeId"));
            int statusId = Integer.parseInt(request.getParameter("statusId"));
            String description = request.getParameter("description");

            // Chuyển đổi chuỗi ngày thành java.sql.Date
            String deadlineStr = request.getParameter("deadline");
            Date deadline = null;
            if (deadlineStr != null && !deadlineStr.isEmpty()) {
                java.util.Date parsedDate = dateFormat.parse(deadlineStr);
                deadline = new Date(parsedDate.getTime());
            }

            int updatedById = Integer.parseInt(request.getParameter("updatedById")); // ID của người thực hiện cập nhật

            boolean updated = issueDao.updateIssue(title, typeId, reqId, assignerId, assigneeId, deadline, statusId, description, updatedById, issueId);

            if (updated) {
                response.sendRedirect("listIssue"); // chuyển hướng về trang danh sách issue
            } else {
                request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response);
        } 
    }
}
