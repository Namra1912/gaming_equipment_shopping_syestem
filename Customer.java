import java.util.List;
import java.util.Scanner;

/**
 * Customer class that extends User to manage customer operations.
 * Customers can browse, search, sort products, add to cart, and checkout.
 */
public class Customer extends User {

    private Cart cart = new Cart();

    /**
     * Constructor to create a Customer user.
     * @param name The customer's name
     * @param email The customer's email
     * @param password The customer's password
     */
    public Customer(String name, String email, String password) {
        super(name, email, password, "customer");
    }

    /**
     * Returns the customer's shopping cart.
     * @return The cart object
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Displays the customer menu and handles customer operations.
     * @param sc Scanner object for user input
     */
    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Browse All Products");
            System.out.println("2. Search Products");
            System.out.println("3. Sort Products");
            System.out.println("4. Add Product to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Remove Item from Cart");
            System.out.println("7. Checkout");
            System.out.println("8. View My Order History");
            System.out.println("9. Logout");
            System.out.print("Enter choice: ");
            // Handle non-integer input gracefully to avoid InputMismatchException
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
                continue;
            }

            switch (choice) {
                case 1:
                    browseProducts();
                    break;
                case 2:
                    searchProducts(sc);
                    break;
                case 3:
                    sortProducts(sc);
                    break;
                case 4:
                    addToCart(sc);
                    break;
                case 5:
                    cart.viewCart();
                    break;
                case 6:
                    removeFromCart(sc);
                    break;
                case 7:
                    checkout(sc);
                    break;
                case 8:
                    viewOrderHistory();
                    break;
                case 9:
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 9);
    }

    /**
     * Displays all available products to the customer.
     */
    private void browseProducts() {
        List<Product> products = FileHandler.loadAllProducts();
        ProductSearchSort.displayProducts(products);
    }

    /**
     * Searches products by name or category keyword.
     * @param sc Scanner object for user input
     */
    private void searchProducts(Scanner sc) {
        System.out.print("Enter keyword to search (name or category): ");
        String keyword = sc.nextLine();
        List<Product> all = FileHandler.loadAllProducts();
        List<Product> results = ProductSearchSort.search(all, keyword);
        if (results.isEmpty()) {
            System.out.println("No products found matching \"" + keyword + "\".");
        } else {
            ProductSearchSort.displayProducts(results);
        }
    }

    /**
     * Sorts products by price or name.
     * @param sc Scanner object for user input
     */
    private void sortProducts(Scanner sc) {
        System.out.println("\n--- Sort By ---");
        System.out.println("1. Price: Low to High");
        System.out.println("2. Price: High to Low");
        System.out.println("3. Name: A to Z");
        System.out.print("Enter choice: ");

        int opt;
        try {
            opt = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        List<Product> products = FileHandler.loadAllProducts();

        switch (opt) {
            case 1:
                ProductSearchSort.sortByPriceLowToHigh(products);
                break;
            case 2:
                ProductSearchSort.sortByPriceHighToLow(products);
                break;
            case 3:
                ProductSearchSort.sortByNameAtoZ(products);
                break;
            default:
                System.out.println("Invalid sort option.");
                return;
        }
        ProductSearchSort.displayProducts(products);
    }

    /**
     * Adds a product to the shopping cart by its ID.
     * @param sc Scanner object for user input
     */
    private void addToCart(Scanner sc) {
        System.out.print("Enter Product ID to add to cart: ");
        String id = sc.nextLine();

        List<Product> products = FileHandler.loadAllProducts();
        boolean found = false;

        for (Product p : products) {
            if (p.getProductId().equalsIgnoreCase(id)) {
                cart.addProduct(p);
                found = true;
                break;
            }
        }
        if (!found)
            System.out.println("Product ID not found.");
    }

    /**
     * Removes a product from the shopping cart by its ID.
     * @param sc Scanner object for user input
     */
    private void removeFromCart(Scanner sc) {
        System.out.print("Enter Product ID to remove from cart: ");
        String id = sc.nextLine();
        cart.removeProduct(id);
    }


    /**
     * Handles the checkout process including payment and order creation.
     * @param sc Scanner object for user input
     */
    private void checkout(Scanner sc) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Add products before checking out.");
            return;
        }

        cart.viewCart();

        System.out.println("--- Confirm Checkout ---");
        System.out.print("Proceed to payment? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (!confirm.equals("yes") && !confirm.equals("y")) {
            System.out.println("Checkout cancelled.");
            return;
        }

        System.out.println("\n--- Select Payment Method ---");
        System.out.println("1. Cash");
        System.out.println("2. UPI");
        System.out.print("Enter choice: ");

        int payChoice;
        try {
            payChoice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Checkout cancelled.");
            return;
        }

        Payment payment;
        switch (payChoice) {
            case 1:
                payment = new CashPayment(sc);
                break;
            case 2:
                payment = new UPIPayment(sc);
                break;
            default:
                System.out.println("Invalid payment option. Checkout cancelled.");
                return;
        }

        double total = cart.getTotal();
        boolean success = payment.processPayment(total);

        if (!success) {
            System.out.println("Payment failed. Your cart is still saved.");
            return;
        }

        String orderId = "ORD" + System.currentTimeMillis();

        // Store customer email in the order for accurate order history matching
        Order order = new Order(orderId, getName(), getEmail(), cart.getCartItems(), total, payment.getPaymentMethod());
        FileHandler.saveOrder(order.toFileString());

        List<Product> allProducts = FileHandler.loadAllProducts();
        for (Product cartItem : cart.getCartItems()) {
            for (Product p : allProducts) {
                if (p.getProductId().equalsIgnoreCase(cartItem.getProductId())) {
                    p.setStock(Math.max(0, p.getStock() - 1));
                    break;
                }
            }
        }
        FileHandler.rewriteProducts(allProducts);

        cart.clear();

        System.out.println("\n==========================================");
        System.out.println("   Order Placed Successfully!");
        System.out.println("   Order ID : " + orderId);
        System.out.printf("   Total    : Rs. %.0f%n", total);
        System.out.println("   Payment  : " + payment.getPaymentMethod());
        System.out.println("   Thank you for shopping with us!");
        System.out.println("==========================================");
    }

    /**
     * Displays the customer's order history.
     * Orders are matched by the customer's email to avoid conflicts with duplicate names.
     */
    private void viewOrderHistory() {
        List<String> allLines = FileHandler.loadAllOrders();
        boolean found = false;

        System.out.println("\n--- My Order History ---");

        for (String line : allLines) {
            try {
                Order o = Order.fromFileString(line);
                // Match by email (stored in order) to avoid false matches on duplicate names
                if (o.getCustomerEmail().equalsIgnoreCase(getEmail())) {
                    o.display();
                    found = true;
                }
            } catch (Exception e) {
                System.out.println("Skipping malformed order entry.");
            }
        }

        if (!found) {
            System.out.println("No orders found. Place your first order!");
        }
    }
}
