/**
 * Payment interface that defines payment processing behavior.
 * Implemented by different payment methods like Cash and UPI.
 */
public interface Payment { // ABSTRACTION: Interface defines common contract for all payment types

    // ABSTRACTION: Abstract methods that must be implemented by any class using this interface
    boolean processPayment(double amount);

    String getPaymentMethod();
}
