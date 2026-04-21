import java.util.ArrayList;
import java.util.List;

/**
 * Cart class to manage shopping cart items for customers.
 * Allows adding, removing, and viewing products in the cart.
 */
public class Cart {

    private List<Product> cartItems = new ArrayList<>();

    /**
     * Adds a product to the cart if it is in stock.
     * @param p The product to add
     */
    public void addProduct(Product p) {
        if (p.getStock() <= 0) {
            System.out.println("Sorry, " + p.getName() + " is out of stock.");
            return;
        }
        cartItems.add(p);
        System.out.println(p.getName() + " added to cart.");
    }

    /**
     * Removes a product from the cart by its ID.
     * @param productId The ID of the product to remove
     */
    public void removeProduct(String productId) {
        boolean found = false;
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getProductId().equalsIgnoreCase(productId)) {
                System.out.println(cartItems.get(i).getName() + " removed from cart.");
                cartItems.remove(i);
                found = true;
                break;
            }
        }
        if (!found)
            System.out.println("Product not found in cart.");
    }

    /**
     * Displays all items in the cart with their total cost.
     */
    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\n--- Your Cart ---");
        System.out.printf("%-6s %-35s Rs.%n", "ID", "Product Name");
        System.out.println("-".repeat(55));
        double total = 0;
        for (Product p : cartItems) {
            System.out.printf("%-6s %-35s %.0f%n", p.getProductId(), p.getName(), p.getPrice());
            total += p.getPrice();
        }
        System.out.println("-".repeat(55));
        System.out.printf("Total: Rs. %.0f%n", total);
        System.out.println();
    }

    /**
     * Calculates and returns the total cost of all items in the cart.
     * @return The total price of all cart items
     */
    public double getTotal() {
        double total = 0;
        for (Product p : cartItems)
            total += p.getPrice();
        return total;
    }

    /**
     * Returns the list of products in the cart.
     * @return List of products in the cart
     */
    public List<Product> getCartItems() {
        return cartItems;
    }

    /**
     * Checks if the cart is empty.
     * @return True if cart is empty, false otherwise
     */
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    /**
     * Removes all items from the cart.
     */
    public void clear() {
        cartItems.clear();
    }
}
