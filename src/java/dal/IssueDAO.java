package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Issue;
import model.Requirement;
import model.Setting;
import model.User;

public class IssueDAO extends DBContext {

    // 1. List all issues with brief details
    public List<Issue> listAllIssues() {
        List<Issue> issues = new ArrayList<>();
        String sql = """
                SELECT issue.issue_id, issue.title, setting.name AS type_name, requirement.title AS req_title,
                       issue.deadline, issue.status_id
                FROM issue
                LEFT JOIN setting ON issue.type_id = setting.setting_id
                LEFT JOIN requirement ON issue.req_id = requirement.req_id;
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Issue issue = new Issue();
                issue.setIssueId(rs.getInt("issue_id"));
                issue.setTitle(rs.getString("title"));

                Setting type = new Setting();
                type.setName(rs.getString("type_name"));
                issue.setTypeId(type);

                Requirement req = new Requirement();
                req.setTitle(rs.getString("req_title"));
                issue.setReqId(req);

                issue.setDeadline(rs.getDate("deadline"));
                issue.setStatusId(rs.getInt("status_id"));

                issues.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issues;
    }

    // 2. Get issue details by id
    public Issue getIssueDetail(int issueId) {
        String sql = """
                SELECT issue.issue_id, issue.title, setting.name AS type_name, requirement.title AS req_title, issue.deadline,
                       issue.status_id, assigner.full_name AS assigner_name, assignee.full_name AS assignee_name,
                       issue.status_date, issue.description, issue.created_at, created_by.full_name AS created_by_name,
                       issue.updated_at, updated_by.full_name AS updated_by_name
                FROM issue
                LEFT JOIN setting ON issue.type_id = setting.setting_id
                LEFT JOIN requirement ON issue.req_id = requirement.req_id
                LEFT JOIN user AS assigner ON issue.assigner_id = assigner.user_id
                LEFT JOIN user AS assignee ON issue.assignee_id = assignee.user_id
                LEFT JOIN user AS created_by ON issue.created_by_id = created_by.user_id
                LEFT JOIN user AS updated_by ON issue.updated_by_id = updated_by.user_id
                WHERE issue.issue_id = ?;
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Issue issue = new Issue();
                    issue.setIssueId(rs.getInt("issue_id"));
                    issue.setTitle(rs.getString("title"));

                    Setting type = new Setting();
                    type.setName(rs.getString("type_name"));
                    issue.setTypeId(type);

                    Requirement req = new Requirement();
                    req.setTitle(rs.getString("req_title"));
                    issue.setReqId(req);

                    issue.setDeadline(rs.getDate("deadline"));
                    issue.setStatusId(rs.getInt("status_id"));

                    User assigner = new User();
                    assigner.setFullName(rs.getString("assigner_name"));
                    issue.setAssignerId(assigner);

                    User assignee = new User();
                    assignee.setFullName(rs.getString("assignee_name"));
                    issue.setAssigneeId(assignee);

                    issue.setStatusDate(rs.getTimestamp("status_date"));
                    issue.setDescription(rs.getString("description"));
                    issue.setCreatedAt(rs.getTimestamp("created_at"));

                    User createdBy = new User();
                    createdBy.setFullName(rs.getString("created_by_name"));
                    issue.setCreatedById(createdBy);

                    issue.setUpdatedAt(rs.getTimestamp("updated_at"));

                    User updatedBy = new User();
                    updatedBy.setFullName(rs.getString("updated_by_name"));
                    issue.setUpdatedById(updatedBy);

                    return issue;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Search issues by title (case-insensitive)
    public List<Issue> searchIssuesByTitle(String title) {
        List<Issue> issues = new ArrayList<>();
        String sql = """
                SELECT issue.issue_id, issue.title, setting.name AS type_name, requirement.title AS req_title, issue.deadline,
                       issue.status_id
                FROM issue
                LEFT JOIN setting ON issue.type_id = setting.setting_id
                LEFT JOIN requirement ON issue.req_id = requirement.req_id
                WHERE LOWER(issue.title) LIKE LOWER(?);
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Issue issue = new Issue();
                    issue.setIssueId(rs.getInt("issue_id"));
                    issue.setTitle(rs.getString("title"));

                    Setting type = new Setting();
                    type.setName(rs.getString("type_name"));
                    issue.setTypeId(type);

                    Requirement req = new Requirement();
                    req.setTitle(rs.getString("req_title"));
                    issue.setReqId(req);

                    issue.setDeadline(rs.getDate("deadline"));
                    issue.setStatusId(rs.getInt("status_id"));

                    issues.add(issue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issues;
    }

    // 4. Filter issues by status, type, or requirement
    public List<Issue> filterIssues(Integer status, String typeName, String reqTitle) {
        List<Issue> issues = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
                SELECT issue.issue_id, issue.title, setting.name AS type_name, requirement.title AS req_title,
                       issue.deadline, issue.status_id
                FROM issue
                LEFT JOIN setting ON issue.type_id = setting.setting_id
                LEFT JOIN requirement ON issue.req_id = requirement.req_id
                WHERE 1=1
                """);

        if (status != null) {
            sql.append("AND issue.status_id = ? ");
        }
        if (typeName != null && !typeName.isEmpty()) {
            sql.append("AND setting.name = ? ");
        }
        if (reqTitle != null && !reqTitle.isEmpty()) {
            sql.append("AND requirement.title = ? ");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (status != null) {
                ps.setInt(paramIndex++, status);
            }
            if (typeName != null && !typeName.isEmpty()) {
                ps.setString(paramIndex++, typeName);
            }
            if (reqTitle != null && !reqTitle.isEmpty()) {
                ps.setString(paramIndex, reqTitle);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Issue issue = new Issue();
                    issue.setIssueId(rs.getInt("issue_id"));
                    issue.setTitle(rs.getString("title"));

                    Setting type = new Setting();
                    type.setName(rs.getString("type_name"));
                    issue.setTypeId(type);

                    Requirement req = new Requirement();
                    req.setTitle(rs.getString("req_title"));
                    issue.setReqId(req);

                    issue.setDeadline(rs.getDate("deadline"));
                    issue.setStatusId(rs.getInt("status_id"));

                    issues.add(issue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issues;
    }

    // 5. Insert new issue
    public boolean insertIssue(String title, int typeId, int reqId, int assignerId, int assigneeId, Date deadline,
                               int statusId, String description, int createdById) {
        String sql = """
                INSERT INTO issue (title, type_id, req_id, assigner_id, assignee_id, deadline, status_id, description, created_by_id, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP);
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, typeId);
            ps.setInt(3, reqId);
            ps.setInt(4, assignerId);
            ps.setInt(5, assigneeId);
            ps.setDate(6, deadline);
            ps.setInt(7, statusId);
            ps.setString(8, description);
            ps.setInt(9, createdById);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Update existing issue
    public boolean updateIssue(String title,
            int typeId,
            int reqId,
            int assignerId,
            int assigneeId,
            Date deadline,
            int statusId,
            String description,
            int createdById,
            int issueId) {
        String sql = """
                UPDATE issue
                SET title = ?, type_id = ?, req_id = ?, assigner_id = ?, assignee_id = ?, 
                    deadline = ?, status_id = ?, description = ?, updated_by_id = ?
                WHERE issue_id = ?;
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, typeId);
            ps.setInt(3, reqId);
            ps.setInt(4, assignerId);
            ps.setInt(5, assigneeId);
            ps.setDate(6, deadline);
            ps.setInt(7, statusId);
            ps.setString(8, description);
            ps.setInt(9, createdById);
            ps.setInt(10, issueId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 7. Delete issue by id
    public boolean deleteIssue(int issueId) {
    if (getIssueDetail(issueId) == null) {
        System.out.println("Issue with ID " + issueId + " does not exist.");
        return false;
    }
    String sql = "DELETE FROM issue WHERE issue_id = ?;";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, issueId);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error deleting issue with ID " + issueId + ": " + e.getMessage());
        e.printStackTrace();
    }
    return false;
}



    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, full_name FROM user";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<Requirement> getAllRequirement() {
        List<Requirement> requirements = new ArrayList<>();
        String sql = "SELECT req_id, title FROM requirement";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Requirement requirement = new Requirement();
                requirement.setReqId(rs.getInt("req_id"));
                requirement.setTitle(rs.getString("title"));
                requirements.add(requirement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requirements;
    }

    public List<Setting> getTypeIssue() {
        return getSettingsByType(6); // Giả định rằng type 5 là cho status
    }

    public List<Setting> getSettingsByType(int type) {
        List<Setting> settings = new ArrayList<>();
        String sql = "SELECT setting_id, name FROM setting WHERE type = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Setting setting = new Setting();
                    setting.setSettingId(rs.getInt("setting_id"));
                    setting.setName(rs.getString("name"));
                    settings.add(setting);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settings;
    }

    public static void main(String[] args) {
        IssueDAO issueDao = new IssueDAO();

        // 1. Kiểm tra phương thức listAllIssues
        System.out.println("All Issues: " + issueDao.listAllIssues());

        // 2. Kiểm tra phương thức getIssueDetail
        int testIssueId = 1; // ID của Issue cần lấy chi tiết
        System.out.println("Issue Details: " + issueDao.getIssueDetail(testIssueId));

        // 3. Kiểm tra phương thức searchIssuesByTitle
        String searchTitle = "Test"; // Từ khóa để tìm kiếm Issue
        System.out.println("Search Issues by Title: " + issueDao.searchIssuesByTitle(searchTitle));

        // 4. Kiểm tra phương thức filterIssues
        Integer statusFilter = 1; // Trạng thái để lọc
        String typeNameFilter = "Bug"; // Loại để lọc
        String reqTitleFilter = "Requirement 1"; // Tên yêu cầu để lọc
        System.out.println("Filtered Issues: " + issueDao.filterIssues(statusFilter, typeNameFilter, reqTitleFilter));

        // 5. Kiểm tra phương thức insertIssue
        boolean isInserted = issueDao.insertIssue(
                "New Issue", // Title
                2, // type_id
                3, // req_id
                1, // assigner_id
                2, // assignee_id
                new java.sql.Date(System.currentTimeMillis()), // deadline
                1, // status_id
                "This is a new test issue", // Description
                1 // created_by_id
        );
        System.out.println("Insert Issue: " + (isInserted ? "Success" : "Failed"));

        // 6. Kiểm tra phương thức updateIssue
        boolean isUpdated = issueDao.updateIssue(
                "Updated Issue Title", // Title mới
                2, // type_id
                3, // req_id
                1, // assigner_id
                2, // assignee_id
                new java.sql.Date(System.currentTimeMillis()), // deadline mới
                2, // status_id mới
                "Updated description of the issue", // Description mới
                1, // updated_by_id
                testIssueId // issue_id của Issue cần cập nhật
        );
        System.out.println("Update Issue: " + (isUpdated ? "Success" : "Failed"));

        // 7. Kiểm tra phương thức deleteIssue
        int deleteIssueId = 2; // ID của Issue cần xóa
        boolean isDeleted = issueDao.deleteIssue(deleteIssueId);
        System.out.println("Delete Issue with ID " + deleteIssueId + ": " + (isDeleted ? "Success" : "Failed"));

        // 8. Kiểm tra phương thức getAllUsers
        System.out.println("All Users: " + issueDao.getAllUsers());

        // 9. Kiểm tra phương thức getAllRequirement
        System.out.println("All Requirements: " + issueDao.getAllRequirement());

        // 10. Kiểm tra phương thức getStatusIssue
        System.out.println("Status Issues: " + issueDao.getTypeIssue());
    }

}
