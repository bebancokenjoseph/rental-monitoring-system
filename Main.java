// ============================================
// Main.java
// Owner: Member 4 (Error Handling, Testing & Menu)
// Purpose: Main menu system, program entry point,
//          integrates all modules
// ============================================

public class Main {

    public static void main(String[] args) {

        // Test DB connection on startup
        DBConnection.testConnection();

        boolean running = true;

        while (running) {
            printMenu();
            int choice = InputHelper.getInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    handleAddRental();
                    break;

                case 2:
                    CRUDOperations.viewAllRentals();
                    break;

                case 3:
                    handleSearchRental();
                    break;

                case 4:
                    handleUpdateRental();
                    break;

                case 5:
                    handleDeleteRental();
                    break;

                case 0:
                    System.out.println("\nThank you for using Rental Monitoring System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("[WARNING] Invalid choice. Please select from the menu.");
            }
        }
    }

    // ==========================================
    // Print the main menu
    // ==========================================
    private static void printMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║     RENTAL MONITORING SYSTEM         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  [1] Add New Rental                  ║");
        System.out.println("║  [2] View All Rentals                ║");
        System.out.println("║  [3] Search Rental by ID             ║");
        System.out.println("║  [4] Update Rental                   ║");
        System.out.println("║  [5] Delete Rental                   ║");
        System.out.println("║  [0] Exit                            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    // ==========================================
    // Handle Add Rental input flow
    // ==========================================
    private static void handleAddRental() {
        System.out.println("\n--- ADD NEW RENTAL ---");

        String customerName = InputHelper.getString("Customer Name   : ");
        String itemName     = InputHelper.getString("Item to Rent    : ");
        String rentalDate   = InputHelper.getDate("Rental Date     ");
        String returnDate   = InputHelper.getDate("Return Date     ");
        String status       = InputHelper.getStatus("Status          ");
        double amount       = InputHelper.getDouble("Amount (PHP)    : ");

        System.out.println("\nReview Entry:");
        System.out.println("  Customer  : " + customerName);
        System.out.println("  Item      : " + itemName);
        System.out.println("  Rent Date : " + rentalDate);
        System.out.println("  Return    : " + returnDate);
        System.out.println("  Status    : " + status);
        System.out.printf("  Amount    : PHP %.2f%n", amount);

        if (InputHelper.confirm("\nConfirm add?")) {
            CRUDOperations.addRental(customerName, itemName, rentalDate, returnDate, status, amount);
        } else {
            System.out.println("Add cancelled.");
        }
    }

    // ==========================================
    // Handle Search Rental by ID
    // ==========================================
    private static void handleSearchRental() {
        System.out.println("\n--- SEARCH RENTAL BY ID ---");
        int id = InputHelper.getPositiveInt("Enter Rental ID : ");
        CRUDOperations.searchRentalById(id);
    }

    // ==========================================
    // Handle Update Rental
    // ==========================================
    private static void handleUpdateRental() {
        System.out.println("\n--- UPDATE RENTAL ---");
        int id = InputHelper.getPositiveInt("Enter Rental ID to update : ");

        // Show current record first
        CRUDOperations.searchRentalById(id);

        String newStatus = InputHelper.getStatus("New Status      ");
        double newAmount = InputHelper.getDouble("New Amount (PHP): ");

        if (InputHelper.confirm("\nConfirm update for Rental ID " + id + "?")) {
            CRUDOperations.updateRental(id, newStatus, newAmount);
        } else {
            System.out.println("Update cancelled.");
        }
    }

    // ==========================================
    // Handle Delete Rental
    // ==========================================
    private static void handleDeleteRental() {
        System.out.println("\n--- DELETE RENTAL ---");
        int id = InputHelper.getPositiveInt("Enter Rental ID to delete : ");

        // Show record before deleting
        CRUDOperations.searchRentalById(id);

        if (InputHelper.confirm("Are you sure you want to DELETE Rental ID " + id + "?")) {
            CRUDOperations.deleteRental(id);
        } else {
            System.out.println("Delete cancelled.");
        }
    }
}
