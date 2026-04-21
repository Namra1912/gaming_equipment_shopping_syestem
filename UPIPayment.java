import java.util.Scanner;

/**
 * UPIPayment class that implements the Payment interface.
 * Handles UPI payments through a UPI ID.
 */
public class UPIPayment implements Payment { // POLYMORPHISM: UPIPayment is another type of Payment

    private Scanner sc;

    public UPIPayment(Scanner sc) {
        this.sc = sc;
    }

    // POLYMORPHISM: Method Overriding - different implementation of processPayment for UPI
    @Override
    public boolean processPayment(double amount) {
        System.out.println("\n--- UPI Payment ---");
        System.out.printf("Total Amount Due : Rs. %.0f%n", amount);
        System.out.print("Enter your UPI ID (e.g. name@upi): ");

        String upiId = sc.nextLine().trim();

        if (upiId.isEmpty() || !upiId.contains("@")) {
            System.out.println("Invalid UPI ID. Payment failed.");
            return false;
        }

        System.out.println("Processing payment to UPI: " + upiId);
        System.out.println("...");
        System.out.printf("Rs. %.0f debited from %s successfully!%n", amount, upiId);
        System.out.println("UPI payment successful!");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "UPI";
    }
}
