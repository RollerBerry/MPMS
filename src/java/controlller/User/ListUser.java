/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller.User;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.User;

/**
 *
 * @author Predator
 */
public class ListUser extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();
        ArrayList<User> userList = dao.listUser();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/WEB-INF/View/User/ListUser.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();

        // Kiểm tra xem có yêu cầu thay đổi trạng thái (từ AJAX) không
        String userIdParam = request.getParameter("userId");
        String statusParam = request.getParameter("status");

        if (userIdParam != null && statusParam != null) {
            // Nếu có tham số userId và status, xử lý việc thay đổi trạng thái
            int userId = Integer.parseInt(userIdParam);
            int newStatus = Integer.parseInt(statusParam);

            // Cập nhật trạng thái người dùng
            boolean isUpdated = dao.updateUserStatus(userId, newStatus == 1);

            if (isUpdated) {
                response.setStatus(HttpServletResponse.SC_OK); // Thành công
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Thất bại
            }
            return; // Kết thúc xử lý cho AJAX, không thực hiện phần còn lại
        }

        // Nếu không phải là yêu cầu AJAX, thực hiện tìm kiếm và sắp xếp danh sách người dùng
        String department = request.getParameter("department");
        String userName = request.getParameter("userName");

        ArrayList<User> userList;

        // Kiểm tra nếu có ít nhất một trong hai tham số là không rỗng
        if ((department != null && !department.trim().isEmpty())
                || (userName != null && !userName.trim().isEmpty())) {

            userList = dao.searchUser(department, userName);
        } else {
            userList = dao.listUser(); // Lấy danh sách người dùng nếu không có tham số nào
        }
        System.out.println(userList);

        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/WEB-INF/View/User/ListUser.jsp").forward(request, response);
    }


}
