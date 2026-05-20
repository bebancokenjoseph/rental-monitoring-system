// ============================================
// CRUDOperations.java
// Owner: James (Backend Logic Developer)
// Purpose: All SQL operations for customers, rentals, payments
// ============================================

import java.sql.*;

public class CRUDOperations {

    // ==========================================
    // CUSTOMERS — Add
    // ==========================================
    public static void addCustomer(String fullName, String contactNumber, String email) {
        String sql = "INSERT INTO customers (full_name, contact_number, email) VALUES (?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fullName);
            ps.setString(2, contactNumber);
            ps.setString(3, email);
            int rows = ps.executeUpdate();
            if (rows > 0) { conn.commit(); System.out.println("\n[SUCCESS] Customer added!"); }
            else           { conn.rollback(); System.out.println("[FAILED] Could not add customer."); }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // CUSTOMERS — View All
    public static void viewAllCustomers() {
        String sql = "SELECT * FROM customers ORDER BY customer_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n========================================================");
            System.out.println("                  ALL CUSTOMERS                        ");
            System.out.println("========================================================");
            System.out.printf("%-5s %-25s %-15s %-25s%n", "ID", "Full Name", "Contact", "Email");
            System.out.println("--------------------------------------------------------");
            boolean has = false;
            while (rs.next()) {
                has = true;
                System.out.printf("%-5d %-25s %-15s %-25s%n",
                    rs.getInt("customer_id"), rs.getString("full_name"),
                    rs.getString("contact_number"), rs.getString("email"));
            }
            if (!has) System.out.println("  No customers found.");
            System.out.println("========================================================\n");
        } catch (SQLException e) { System.out.println("[ERROR] " + e.getMessage()); }
    }

    // CUSTOMERS — Search by ID
    public static void searchCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n========================================================");
            System.out.println("               CUSTOMER SEARCH RESULT                  ");
            System.out.println("========================================================");
            if (rs.next()) {
                System.out.println("  Customer ID  : " + rs.getInt("customer_id"));
                System.out.println("  Full Name    : " + rs.getString("full_name"));
                System.out.println("  Contact      : " + rs.getString("contact_number"));
                System.out.println("  Email        : " + rs.getString("email"));
            } else {
                System.out.println("  No customer found with ID: " + id);
            }
            System.out.println("========================================================\n");
            rs.close();
        } catch (SQLException e) { System.out.println("[ERROR] " + e.getMessage()); }
    }

    // CUSTOMERS — Update
    public static void updateCustomer(int id, String fullName, String contact, String email) {
        String sql = "UPDATE customers SET full_name=?, contact_number=?, email=? WHERE customer_id=?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fullName); ps.setString(2, contact);
            ps.setString(3, email);   ps.setInt(4, id);
            int rows = ps.executeUpdate();
            if (rows > 0) { conn.commit(); System.out.println("\n[SUCCESS] Customer ID " + id + " updated!"); }
            else           { conn.rollback(); System.out.println("[NOT FOUND] No customer with ID: " + id); }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // CUSTOMERS — Delete
    public static void deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) { conn.commit(); System.out.println("\n[SUCCESS] Customer ID " + id + " deleted!"); }
            else           { conn.rollback(); System.out.println("[NOT FOUND] No customer with ID: " + id); }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // ==========================================
    // RENTALS — Add (uses customer_id FK)
    // ==========================================
    public static void addRental(int customerId, String itemName,
                                  String rentalDate, String returnDate,
                                  String status, double amount) {
        String sql = "INSERT INTO rentals (customer_id, item_name, rental_date, return_date, status, amount) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);    ps.setString(2, itemName);
            ps.setString(3, rentalDate); ps.setString(4, returnDate);
            ps.setString(5, status);     ps.setDouble(6, amount);
            int rows = ps.executeUpdate();
            if (rows > 0) { conn.commit(); System.out.println("\n[SUCCESS] Rental record added!"); }
            else           { conn.rollback(); System.out.println("[FAILED] Could not add rental."); }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // RENTALS — View All (with customer name via JOIN)
    public static void viewAllRentals() {
        String sql = "SELECT r.rental_id, c.full_name, r.item_name, r.rental_date, " +
                     "r.return_date, r.status, r.amount " +
                     "FROM rentals r JOIN customers c ON r.customer_id = c.customer_id " +
                     "ORDER BY r.rental_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n========================================================");
            System.out.println("                 ALL RENTAL RECORDS                    ");
            System.out.println("========================================================");
            System.out.printf("%-5s %-20s %-18s %-12s %-12s %-10s %-8s%n",
                    "ID", "Customer", "Item", "Rent Date", "Return Date", "Status", "Amount");
            System.out.println("--------------------------------------------------------");
            boolean has = false;
            while (rs.next()) {
                has = true;
                System.out.printf("%-5d %-20s %-18s %-12s %-12s %-10s %-8.2f%n",
                        rs.getInt("rental_id"), rs.getString("full_name"),
                        rs.getString("item_name"), rs.getString("rental_date"),
                        rs.getString("return_date"), rs.getString("status"),
                        rs.getDouble("amount"));
            }
            if (!has) System.out.println("  No rental records found.");
            System.out.println("========================================================\n");
        } catch (SQLException e) { System.out.println("[ERROR] " + e.getMessage()); }
    }

    // RENTALS — Search by ID
    public static void searchRentalById(int id) {
        String sql = "SELECT r.*, c.full_name FROM rentals r " +
                     "JOIN customers c ON r.customer_id = c.customer_id WHERE r.rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n========================================================");
            System.out.println("               RENTAL SEARCH RESULT                    ");
            System.out.println("========================================================");
            if (rs.next()) {
                System.out.println("  Rental ID    : " + rs.getInt("rental_id"));
                System.out.println("  Customer     : " + rs.getString("full_name"));
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
        } catch (SQLException e) { System.out.println("[ERROR] " + e.getMessage()); }
    }

    // RENTALS — Update
    public static void updateRental(int id, String newStatus, double newAmount) {
        String sql = "UPDATE rentals SET status=?, amount=? WHERE rental_id=?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus); ps.setDouble(2, newAmount); ps.setInt(3, id);
            int rows = ps.executeUpdate();
            if (rows > 0) { conn.commit(); System.out.println("\n[SUCCESS] Rental ID " + id + " updated!"); }
            else           { conn.rollback(); System.out.println("[NOT FOUND] No rental with ID: " + id); }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // RENTALS — Delete
    public static void deleteRental(int id) {
        String sql = "DELETE FROM rentals WHERE rental_id=?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) { conn.commit(); System.out.println("\n[SUCCESS] Rental ID " + id + " deleted!"); }
            else           { conn.rollback(); System.out.println("[NOT FOUND] No rental with ID: " + id); }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // ==========================================
    // PAYMENTS — Add
    // ==========================================
    public static void addPayment(int rentalId, double amountPaid, String method) {
        String sql = "INSERT INTO payments (rental_id, amount_paid, payment_method) VALUES (?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, rentalId); ps.setDouble(2, amountPaid); ps.setString(3, method);
            int rows = ps.executeUpdate();
            if (rows > 0) { conn.commit(); System.out.println("\n[SUCCESS] Payment recorded!"); }
            else           { conn.rollback(); System.out.println("[FAILED] Could not record payment."); }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // PAYMENTS — View All (with customer + item via JOIN)
    public static void viewAllPayments() {
        String sql = "SELECT p.payment_id, c.full_name, r.item_name, p.amount_paid, " +
                     "p.payment_date, p.payment_method " +
                     "FROM payments p " +
                     "JOIN rentals r ON p.rental_id = r.rental_id " +
                     "JOIN customers c ON r.customer_id = c.customer_id " +
                     "ORDER BY p.payment_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n========================================================");
            System.out.println("                  ALL PAYMENTS                         ");
            System.out.println("========================================================");
            System.out.printf("%-5s %-20s %-18s %-10s %-20s %-15s%n",
                    "ID", "Customer", "Item", "Amount", "Date", "Method");
            System.out.println("--------------------------------------------------------");
            boolean has = false;
            while (rs.next()) {
                has = true;
                System.out.printf("%-5d %-20s %-18s %-10.2f %-20s %-15s%n",
                        rs.getInt("payment_id"), rs.getString("full_name"),
                        rs.getString("item_name"), rs.getDouble("amount_paid"),
                        rs.getString("payment_date"), rs.getString("payment_method"));
            }
            if (!has) System.out.println("  No payments found.");
            System.out.println("========================================================\n");
        } catch (SQLException e) { System.out.println("[ERROR] " + e.getMessage()); }
    }

    // PAYMENTS — Search by ID
    public static void searchPaymentById(int id) {
        String sql = "SELECT p.*, c.full_name, r.item_name FROM payments p " +
                     "JOIN rentals r ON p.rental_id = r.rental_id " +
                     "JOIN customers c ON r.customer_id = c.customer_id " +
                     "WHERE p.payment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n========================================================");
            System.out.println("               PAYMENT SEARCH RESULT                   ");
            System.out.println("========================================================");
            if (rs.next()) {
                System.out.println("  Payment ID   : " + rs.getInt("payment_id"));
                System.out.println("  Customer     : " + rs.getString("full_name"));
                System.out.println("  Item         : " + rs.getString("item_name"));
                System.out.printf("  Amount Paid  : PHP %.2f%n", rs.getDouble("amount_paid"));
                System.out.println("  Date         : " + rs.getString("payment_date"));
                System.out.println("  Method       : " + rs.getString("payment_method"));
            } else {
                System.out.println("  No payment found with ID: " + id);
            }
            System.out.println("========================================================\n");
            rs.close();
        } catch (SQLException e) { System.out.println("[ERROR] " + e.getMessage()); }
    }

    // PAYMENTS — Delete
    public static void deletePayment(int id) {
        String sql = "DELETE FROM payments WHERE payment_id=?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) { conn.commit(); System.out.println("\n[SUCCESS] Payment ID " + id + " deleted!"); }
            else           { conn.rollback(); System.out.println("[NOT FOUND] No payment with ID: " + id); }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
