// ============================================
// Main.java
// Owner: Lynzelle (Error Handling, Testing & Menu)
// Purpose: Main menu system â€” 3 sub-menus for
//          Customers, Rentals, and Payments
// ============================================

public class Main {

    public static void main(String[] args) {
        DBConnection.testConnection();
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = InputHelper.getInt("Enter your choice: ");

            switch (choice) {
                case 1: customerMenu(); break;
                case 2: rentalMenu();   break;
                case 3: paymentMenu();  break;
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
    // MAIN MENU
    // ==========================================
    private static void printMainMenu() {
        System.out.println("\nâ•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
        System.out.println("â•‘     RENTAL MONITORING SYSTEM         â•‘");
        System.out.println("â• â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•£");
        System.out.println("â•‘  [1] Customer Management             â•‘");
        System.out.println("â•‘  [2] Rental Management               â•‘");
        System.out.println("â•‘  [3] Payment Management              â•‘");
        System.out.println("â•‘  [0] Exit                            â•‘");
        System.out.println("â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");
    }

    // ==========================================
    // CUSTOMER SUB-MENU
    // ==========================================
    private static void customerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nâ•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
            System.out.println("â•‘        CUSTOMER MANAGEMENT           â•‘");
            System.out.println("â• â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•£");
            System.out.println("â•‘  [1] Add New Customer                â•‘");
            System.out.println("â•‘  [2] View All Customers              â•‘");
            System.out.println("â•‘  [3] Search Customer by ID           â•‘");
            System.out.println("â•‘  [4] Update Customer                 â•‘");
            System.out.println("â•‘  [5] Delete Customer                 â•‘");
            System.out.println("â•‘  [0] Back to Main Menu               â•‘");
            System.out.println("â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");

            int choice = InputHelper.getInt("Enter your choice: ");
            switch (choice) {
                case 1: handleAddCustomer();    break;
                case 2: CRUDOperations.viewAllCustomers(); break;
                case 3: handleSearchCustomer(); break;
                case 4: handleUpdateCustomer(); break;
                case 5: handleDeleteCustomer(); break;
                case 0: back = true;            break;
                default: System.out.println("[WARNING] Invalid choice.");
            }
        }
    }

    private static void handleAddCustomer() {
        System.out.println("\n--- ADD NEW CUSTOMER ---");
        String name    = InputHelper.getString("Full Name       : ");
        String contact = InputHelper.getContactNumber("Contact Number  : ");
        String email   = InputHelper.getString("Email           : ");

        System.out.println("\nReview Entry:");
        System.out.println("  Name    : " + name);
        System.out.println("  Contact : " + contact);
        System.out.println("  Email   : " + email);

        if (InputHelper.confirm("\nConfirm add?"))
            CRUDOperations.addCustomer(name, contact, email);
        else
            System.out.println("Add cancelled.");
    }

    private static void handleSearchCustomer() {
        System.out.println("\n--- SEARCH CUSTOMER BY ID ---");
        int id = InputHelper.getPositiveInt("Enter Customer ID: ");
        CRUDOperations.searchCustomerById(id);
    }

    private static void handleUpdateCustomer() {
        System.out.println("\n--- UPDATE CUSTOMER ---");
        int id = InputHelper.getPositiveInt("Enter Customer ID to update: ");
        CRUDOperations.searchCustomerById(id);

        String name    = InputHelper.getString("New Full Name    : ");
        String contact = InputHelper.getContactNumber("New Contact No.  : ");
        String email   = InputHelper.getString("New Email        : ");

        if (InputHelper.confirm("\nConfirm update for Customer ID " + id + "?"))
            CRUDOperations.updateCustomer(id, name, contact, email);
        else
            System.out.println("Update cancelled.");
    }

    private static void handleDeleteCustomer() {
        System.out.println("\n--- DELETE CUSTOMER ---");
        int id = InputHelper.getPositiveInt("Enter Customer ID to delete: ");
        CRUDOperations.searchCustomerById(id);

        if (InputHelper.confirm("Are you sure you want to DELETE Customer ID " + id + "?"))
            CRUDOperations.deleteCustomer(id);
        else
            System.out.println("Delete cancelled.");
    }

    // ==========================================
    // RENTAL SUB-MENU
    // ==========================================
    private static void rentalMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nâ•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
            System.out.println("â•‘         RENTAL MANAGEMENT            â•‘");
            System.out.println("â• â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•£");
            System.out.println("â•‘  [1] Add New Rental                  â•‘");
            System.out.println("â•‘  [2] View All Rentals                â•‘");
            System.out.println("â•‘  [3] Search Rental by ID             â•‘");
            System.out.println("â•‘  [4] Update Rental                   â•‘");
            System.out.println("â•‘  [5] Delete Rental                   â•‘");
            System.out.println("â•‘  [0] Back to Main Menu               â•‘");
            System.out.println("â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");

            int choice = InputHelper.getInt("Enter your choice: ");
            switch (choice) {
                case 1: handleAddRental();    break;
                case 2: CRUDOperations.viewAllRentals(); break;
                case 3: handleSearchRental(); break;
                case 4: handleUpdateRental(); break;
                case 5: handleDeleteRental(); break;
                case 0: back = true;          break;
                default: System.out.println("[WARNING] Invalid choice.");
            }
        }
    }

    private static void handleAddRental() {
        System.out.println("\n--- ADD NEW RENTAL ---");
        System.out.println("(View customers first to get Customer ID)");
        CRUDOperations.viewAllCustomers();

        int    customerId  = InputHelper.getPositiveInt("Customer ID     : ");
        String itemName    = InputHelper.getString("Item to Rent    : ");
        String rentalDate  = InputHelper.getDate("Rental Date     ");
        String returnDate  = InputHelper.getDate("Return Date     ");
        String status      = InputHelper.getStatus("Status          ");
        double amount      = InputHelper.getDouble("Amount (PHP)    : ");

        System.out.println("\nReview Entry:");
        System.out.println("  Customer ID : " + customerId);
        System.out.println("  Item        : " + itemName);
        System.out.println("  Rent Date   : " + rentalDate);
        System.out.println("  Return Date : " + returnDate);
        System.out.println("  Status      : " + status);
        System.out.printf("  Amount      : PHP %.2f%n", amount);

        if (InputHelper.confirm("\nConfirm add?"))
            CRUDOperations.addRental(customerId, itemName, rentalDate, returnDate, status, amount);
        else
            System.out.println("Add cancelled.");
    }

    private static void handleSearchRental() {
        System.out.println("\n--- SEARCH RENTAL BY ID ---");
        int id = InputHelper.getPositiveInt("Enter Rental ID : ");
        CRUDOperations.searchRentalById(id);
    }

    private static void handleUpdateRental() {
        System.out.println("\n--- UPDATE RENTAL ---");
        int id = InputHelper.getPositiveInt("Enter Rental ID to update: ");
        CRUDOperations.searchRentalById(id);

        String newStatus = InputHelper.getStatus("New Status      ");
        double newAmount = InputHelper.getDouble("New Amount (PHP): ");

        if (InputHelper.confirm("\nConfirm update for Rental ID " + id + "?"))
            CRUDOperations.updateRental(id, newStatus, newAmount);
        else
            System.out.println("Update cancelled.");
    }

    private static void handleDeleteRental() {
        System.out.println("\n--- DELETE RENTAL ---");
        int id = InputHelper.getPositiveInt("Enter Rental ID to delete: ");
        CRUDOperations.searchRentalById(id);

        if (InputHelper.confirm("Are you sure you want to DELETE Rental ID " + id + "?"))
            CRUDOperations.deleteRental(id);
        else
            System.out.println("Delete cancelled.");
    }

    // ==========================================
    // PAYMENT SUB-MENU
    // ==========================================
    private static void paymentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nâ•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
            System.out.println("â•‘        PAYMENT MANAGEMENT            â•‘");
            System.out.println("â• â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•£");
            System.out.println("â•‘  [1] Add New Payment                 â•‘");
            System.out.println("â•‘  [2] View All Payments               â•‘");
            System.out.println("â•‘  [3] Search Payment by ID            â•‘");
            System.out.println("â•‘  [4] Delete Payment                  â•‘");
            System.out.println("â•‘  [0] Back to Main Menu               â•‘");
            System.out.println("â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");

            int choice = InputHelper.getInt("Enter your choice: ");
            switch (choice) {
                case 1: handleAddPayment();    break;
                case 2: CRUDOperations.viewAllPayments(); break;
                case 3: handleSearchPayment(); break;
                case 4: handleDeletePayment(); break;
                case 0: back = true;           break;
                default: System.out.println("[WARNING] Invalid choice.");
            }
        }
    }

    private static void handleAddPayment() {
        System.out.println("\n--- ADD NEW PAYMENT ---");
        System.out.println("(View rentals first to get Rental ID)");
        CRUDOperations.viewAllRentals();

        int    rentalId    = InputHelper.getPositiveInt("Rental ID       : ");
        double amountPaid  = InputHelper.getDouble("Amount Paid (PHP): ");
        String method      = InputHelper.getPaymentMethod("Payment Method  ");

        System.out.println("\nReview Entry:");
        System.out.println("  Rental ID   : " + rentalId);
        System.out.printf("  Amount Paid : PHP %.2f%n", amountPaid);
        System.out.println("  Method      : " + method);

        if (InputHelper.confirm("\nConfirm payment?"))
            CRUDOperations.addPayment(rentalId, amountPaid, method);
        else
            System.out.println("Payment cancelled.");
    }

    private static void handleSearchPayment() {
        System.out.println("\n--- SEARCH PAYMENT BY ID ---");
        int id = InputHelper.getPositiveInt("Enter Payment ID: ");
        CRUDOperations.searchPaymentById(id);
    }

    private static void handleDeletePayment() {
        System.out.println("\n--- DELETE PAYMENT ---");
        int id = InputHelper.getPositiveInt("Enter Payment ID to delete: ");
        CRUDOperations.searchPaymentById(id);

        if (InputHelper.confirm("Are you sure you want to DELETE Payment ID " + id + "?"))
            CRUDOperations.deletePayment(id);
        else
            System.out.println("Delete cancelled.");
    }
    }
