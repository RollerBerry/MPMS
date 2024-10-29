package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object to access the database
 * @author Admin
 */
public class DBContext {
    Connection connection;
    protected PreparedStatement ps;
    protected ResultSet rs;

    public DBContext() {
        try {
            String user = "root";
            String password = "123456";
            String url = "jdbc:mysql://localhost:3306/mpms";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if (connection == null) {
                System.out.println("Failed to establish connection.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        Connection conn = dbContext.getConnection();
        if (conn != null) {
            System.out.println("Connection established successfully.");
            dbContext.close();
        } else {
            System.out.println("Failed to establish connection.");
        }
    }
}
