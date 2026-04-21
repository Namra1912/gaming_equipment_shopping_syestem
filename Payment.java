/**
 * Payment interface that defines payment processing behavior.
 * Implemented by different payment methods like Cash and UPI.
 */
public interface Payment {

    /**
     * Processes a payment with the given amount.
     * @param amount The amount to be paid
     * @return True if payment successful, false otherwise
     */
    boolean processPayment(double amount);

    /**
     * Returns the name of the payment method.
     * @return The payment method name
     */
    String getPaymentMethod();
}
