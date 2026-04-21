import java.util.Scanner;

/**
 * CashPayment class that implements the Payment interface.
 * Handles cash payments and calculates change.
 */
public class CashPayment implements Payment { // POLYMORPHISM: CashPayment is a type of Payment

    private Scanner sc;

    public CashPayment(Scanner sc) {
        this.sc = sc;
    }

    // POLYMORPHISM: Method Overriding - same method name but different implementation from Payment interface
    @Override
    public boolean processPayment(double amount) {
        System.out.println("\n--- Cash Payment ---");
        System.out.printf("Total Amount Due : Rs. %.0f%n", amount);
        System.out.print("Enter cash amount tendered: Rs. ");

        double tendered;
        try {
            tendered = sc.nextDouble();
            sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Invalid amount entered. Payment failed.");
            return false;
        }

        if (tendered < amount) {
            System.out.printf("Insufficient amount. You need Rs. %.0f more.%n", (amount - tendered));
            return false;
        }

        double change = tendered - amount;
        System.out.printf("Payment of Rs. %.0f received.%n", tendered);
        if (change > 0) {
            System.out.printf("Change returned : Rs. %.0f%n", change);
        }
        System.out.println("Cash payment successful!");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Cash";
    }
}
