// ============================================
// CRUDOperations.java
// Owner: Member 2 (Backend Logic Developer)
// Purpose: All SQL operations (Create, Read, Update, Delete)
// ============================================

import java.sql.*;

public class CRUDOperations {

    // ==========================================
    // CREATE — Insert a new rental record
    // ==========================================
    public static void addRental(String customerName, String itemName,
                                  String rentalDate, String returnDate,
                                  String status, double amount) {

        String sql = "INSERT INTO rentals (customer_name, item_name, rental_date, return_date, status, amount) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, itemName);
            ps.setString(3, rentalDate);
            ps.setString(4, returnDate);
            ps.setString(5, status);
            ps.setDouble(6, amount);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                conn.commit(); // Save to database
                System.out.println("\n[SUCCESS] Rental record added successfully!");
            } else {
                conn.rollback(); // Undo if something went wrong
                System.out.println("[FAILED] Could not add rental record.");
            }

            ps.close();

        } catch (SQLException e) {
            System.out.println("[ERROR] Failed to add rental: " + e.getMessage());
            try {
                if (conn != null) conn.rollback(); // Rollback on error
            } catch (SQLException ex) {
                System.out.println("[ERROR] Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("[ERROR] Could not close connection.");
            }
        }
    }

    // ==========================================
    // READ — View all rental records
    // ==========================================
    public static void viewAllRentals() {
        String sql = "SELECT * FROM rentals ORDER BY rental_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n========================================================");
            System.out.println("               ALL RENTAL RECORDS                      ");
            System.out.println("========================================================");
            System.out.printf("%-5s %-20s %-20s %-12s %-12s %-10s %-10s%n",
                    "ID", "Customer", "Item", "Rent Date", "Return Date", "Status", "Amount");
            System.out.println("--------------------------------------------------------");

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                System.out.printf("%-5d %-20s %-20s %-12s %-12s %-10s %-10.2f%n",
                        rs.getInt("rental_id"),
                        rs.getString("customer_name"),
                        rs.getString("item_name"),
                        rs.getString("rental_date"),
                        rs.getString("return_date"),
                        rs.getString("status"),
                        rs.getDouble("amount"));
            }

            if (!hasRecords) {
                System.out.println("  No rental records found.");
            }

            System.out.println("========================================================\n");

        } catch (SQLException e) {
            System.out.println("[ERROR] Could not retrieve records: " + e.getMessage());
        }
    }

    // ==========================================
    // READ — Search rental by ID
    // ==========================================
    public static void searchRentalById(int id) {
        String sql = "SELECT * FROM rentals WHERE rental_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========================================================");
            System.out.println("               SEARCH RESULT                            ");
            System.out.println("========================================================");

            if (rs.next()) {
                System.out.println("  Rental ID    : " + rs.getInt("rental_id"));
                System.out.println("  Customer     : " + rs.getString("customer_name"));
                System.out.println("  Item Rented  : " + rs.getString("item_name"));
                System.out.println("  Rental Date  : " + rs.getString("rental_date"));
                System.out.println("  Return Date  : " + rs.getString("return_date"));
                System.out.println("  Status       : " + rs.getString("status"));
                System.out.printf("  Amount       : PHP %.2f%n", rs.getDouble("amount"));
            } else {
                System.out.println("  No record found with ID: " + id);
            }

            System.out.println("========================================================\n");
            rs.close();

        } catch (SQLException e) {
            System.out.println("[ERROR] Search failed: " + e.getMessage());
        }
    }

    // ==========================================
    // UPDATE — Modify an existing rental record
    // ==========================================
    public static void updateRental(int id, String newStatus, double newAmount) {
        String sql = "UPDATE rentals SET status = ?, amount = ? WHERE rental_id = ?";

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setDouble(2, newAmount);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                conn.commit(); // Save changes
                System.out.println("\n[SUCCESS] Rental ID " + id + " updated successfully!");
            } else {
                conn.rollback();
                System.out.println("[NOT FOUND] No rental with ID: " + id);
            }

            ps.close();

        } catch (SQLException e) {
            System.out.println("[ERROR] Update failed: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("[ERROR] Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("[ERROR] Could not close connection.");
            }
        }
    }

    // ==========================================
    // DELETE — Remove a rental record
    // ==========================================
    public static void deleteRental(int id) {
        String sql = "DELETE FROM rentals WHERE rental_id = ?";

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                conn.commit(); // Confirm deletion
                System.out.println("\n[SUCCESS] Rental ID " + id + " deleted successfully!");
            } else {
                conn.rollback();
                System.out.println("[NOT FOUND] No rental with ID: " + id);
            }

            ps.close();

        } catch (SQLException e) {
            System.out.println("[ERROR] Delete failed: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("[ERROR] Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("[ERROR] Could not close connection.");
            }
        }
    }
}
