// ============================================
// DBConnection.java
// Owner: Ken (Database Engineer)
// Purpose: Handles the JDBC connection to MySQL
// ============================================

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/rental_db";
    private static final String USER     = "root";
    private static final String PASSWORD = "";  // Change if you have a MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("[ERROR] MySQL JDBC Driver not found!");
            System.out.println("        Make sure mysql-connector-j.jar is in your classpath.");
            throw new SQLException("Driver not found: " + e.getMessage());
        }
    }

    public static void testConnection() {
        System.out.println("Testing database connection...");
        try (Connection conn = getConnection()) {
            if (conn != null)
                System.out.println("[SUCCESS] Connected to database: rental_db");
        } catch (SQLException e) {
            System.out.println("[FAILED] Could not connect: " + e.getMessage());
        }
    }
}
