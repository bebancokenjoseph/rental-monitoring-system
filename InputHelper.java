// ============================================
// InputHelper.java
// Owner: Member 3 (Input & Validation Engineer)
// Purpose: Safe input reading and validation
// ============================================

import java.util.Scanner;

public class InputHelper {

    private static Scanner sc = new Scanner(System.in);

    // ==========================================
    // Read a non-empty string from user
    // ==========================================
    public static String getString(String prompt) {
        String input = "";
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[WARNING] Input cannot be empty. Please try again.");
        }
    }

    // ==========================================
    // Read a valid integer from user
    // ==========================================
    public static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("[WARNING] Invalid number. Please enter a whole number.");
            }
        }
    }

    // ==========================================
    // Read a positive integer (e.g. IDs)
    // ==========================================
    public static int getPositiveInt(String prompt) {
        while (true) {
            int value = getInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("[WARNING] Please enter a positive number (greater than 0).");
        }
    }

    // ==========================================
    // Read a valid double/decimal amount
    // ==========================================
    public static double getDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("[WARNING] Amount cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("[WARNING] Invalid amount. Please enter a number (e.g. 500 or 1200.50).");
            }
        }
    }

    // ==========================================
    // Read and validate a date (YYYY-MM-DD)
    // ==========================================
    public static String getDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String input = sc.nextLine().trim();

            // Basic format check using regex
            if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return input;
            }
            System.out.println("[WARNING] Invalid date format. Use YYYY-MM-DD (e.g. 2025-05-20).");
        }
    }

    // ==========================================
    // Read and validate rental status
    // ==========================================
    public static String getStatus(String prompt) {
        while (true) {
            System.out.print(prompt + " (Active / Returned / Overdue): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("Active") ||
                input.equalsIgnoreCase("Returned") ||
                input.equalsIgnoreCase("Overdue")) {

                // Capitalize properly
                return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
            }
            System.out.println("[WARNING] Status must be: Active, Returned, or Overdue.");
        }
    }

    // ==========================================
    // Ask user for confirmation (y/n)
    // ==========================================
    public static boolean confirm(String message) {
        while (true) {
            System.out.print(message + " (y/n): ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no"))  return false;
            System.out.println("[WARNING] Please type 'y' for yes or 'n' for no.");
        }
    }
}
