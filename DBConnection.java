// ============================================
// DBConnection.java
// Owner: Member 1 (Database Engineer)
// Purpose: Handles the JDBC connection to MySQL
// ============================================

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // --- Database credentials ---
    private static final String URL      = "jdbc:mysql://localhost:3306/rental_db";
    private static final String USER     = "root";       // Change if needed
    private static final String PASSWORD = "";           // Change to your MySQL password

    // --- Returns a live connection to the database ---
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create and return the connection
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;

        } catch (ClassNotFoundException e) {
            // This happens if the MySQL connector JAR is missing
            System.out.println("[ERROR] MySQL JDBC Driver not found!");
            System.out.println("        Make sure mysql-connector-j.jar is in your classpath.");
            throw new SQLException("Driver not found: " + e.getMessage());
        }
    }

    // --- Test the connection (optional utility) ---
    public static void testConnection() {
        System.out.println("Testing database connection...");
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("[SUCCESS] Connected to database: rental_db");
            }
        } catch (SQLException e) {
            System.out.println("[FAILED] Could not connect: " + e.getMessage());
        }
    }
}
