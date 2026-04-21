import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Order class represents a customer order with products, total, and payment details.
 * Stores order information and can be saved to or loaded from a file.
 */
public class Order {

    private String orderId;
    private String customerName;
    private String customerEmail;
    private List<Product> items;
    private double total;
    private String paymentMethod;
    private String date;

    /** DateTimeFormatter used consistently for saving and displaying order dates. */
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Constructor to create an Order with current date and time.
     * @param orderId Unique order ID
     * @param customerName Name of the customer
     * @param customerEmail Email of the customer (used for accurate order history matching)
     * @param items List of products ordered
     * @param total Total price of the order
     * @param paymentMethod Payment method used
     */
    public Order(String orderId, String customerName, String customerEmail,
                 List<Product> items, double total, String paymentMethod) {
        this.orderId       = orderId;
        this.customerName  = customerName;
        this.customerEmail = customerEmail;
        this.items         = items;
        this.total         = total;
        this.paymentMethod = paymentMethod;
        this.date          = LocalDateTime.now().format(FORMATTER);
    }

    /**
     * Constructor to create an Order with a specific date (used when loading from file).
     * @param orderId Unique order ID
     * @param customerName Name of the customer
     * @param customerEmail Email of the customer
     * @param items List of products ordered
     * @param total Total price of the order
     * @param paymentMethod Payment method used
     * @param date Order date as a string
     */
    public Order(String orderId, String customerName, String customerEmail,
                 List<Product> items, double total, String paymentMethod, String date) {
        this.orderId       = orderId;
        this.customerName  = customerName;
        this.customerEmail = customerEmail;
        this.items         = items;
        this.total         = total;
        this.paymentMethod = paymentMethod;
        this.date          = date;
    }

    /**
     * Gets the order ID.
     * @return The order ID
     */
    public String getOrderId() { return orderId; }

    /**
     * Gets the customer's name.
     * @return The customer name
     */
    public String getCustomerName() { return customerName; }

    /**
     * Gets the customer's email.
     * @return The customer email
     */
    public String getCustomerEmail() { return customerEmail; }

    /**
     * Gets the list of items in the order.
     * @return List of products ordered
     */
    public List<Product> getItems() { return items; }

    /**
     * Gets the total cost of the order.
     * @return The total amount
     */
    public double getTotal() { return total; }

    /**
     * Gets the payment method used.
     * @return The payment method
     */
    public String getPaymentMethod() { return paymentMethod; }

    /**
     * Gets the order date.
     * @return The order date as a string
     */
    public String getDate() { return date; }

    /**
     * Displays the order details in a formatted way.
     */
    public void display() {
        System.out.println("\n==========================================");
        System.out.println("  Order ID      : " + orderId);
        System.out.println("  Customer      : " + customerName);
        System.out.println("  Date          : " + date);
        System.out.println("  Payment       : " + paymentMethod);
        System.out.println("------------------------------------------");
        System.out.printf("  %-6s %-30s %s%n", "ID", "Product", "Price");
        System.out.println("------------------------------------------");
        for (Product p : items) {
            System.out.printf("  %-6s %-30s Rs. %.0f%n",
                    p.getProductId(), p.getName(), p.getPrice());
        }
        System.out.println("------------------------------------------");
        System.out.printf("  Total Paid    : Rs. %.0f%n", total);
        System.out.println("==========================================");
    }


    /**
     * Converts the order to a string format for file storage.
     * Format: orderId|customerName|customerEmail|items|total|paymentMethod|date
     * @return String representation of the order
     */
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            Product p = items.get(i);
            sb.append(p.getProductId())
              .append(":")
              .append(p.getName())
              .append(":")
              .append(p.getPrice());
            if (i < items.size() - 1) sb.append(",");
        }
        return orderId + "|" + customerName + "|" + customerEmail + "|" + sb + "|" + total + "|" + paymentMethod + "|" + date;
    }

    /**
     * Creates an Order from a file string.
     * Supports both legacy format (without email) and new format (with email) for backward compatibility.
     * @param line The line from the orders file
     * @return An Order object
     */
    public static Order fromFileString(String line) {
        String[] parts = line.split("\\|");

        // Support legacy format (6 parts: no email) and new format (7 parts: with email)
        String orderId, customerName, customerEmail, itemsRaw, paymentMethod, date;
        double total;

        if (parts.length >= 7) {
            // New format: orderId|customerName|customerEmail|items|total|paymentMethod|date
            orderId       = parts[0];
            customerName  = parts[1];
            customerEmail = parts[2];
            itemsRaw      = parts[3];
            total         = Double.parseDouble(parts[4]);
            paymentMethod = parts[5];
            date          = parts[6];
        } else {
            // Legacy format: orderId|customerName|items|total|paymentMethod|date
            orderId       = parts[0];
            customerName  = parts[1];
            customerEmail = "";
            itemsRaw      = parts[2];
            total         = Double.parseDouble(parts[3]);
            paymentMethod = parts[4];
            date          = parts[5];
        }

        List<Product> items = new java.util.ArrayList<>();
        if (!itemsRaw.isEmpty()) {
            for (String itemStr : itemsRaw.split(",")) {
                String[] ip = itemStr.split(":");
                items.add(new Product(ip[0], ip[1], "-", Double.parseDouble(ip[2]), 0));
            }
        }

        return new Order(orderId, customerName, customerEmail, items, total, paymentMethod, date);
    }
}
