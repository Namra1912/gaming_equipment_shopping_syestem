import java.util.List;
import java.util.Scanner;

/**
 * Admin class that extends User to manage admin operations.
 * Admins can view, add, update, and delete products, and view all orders.
 */
public class Admin extends User {
    // INHERITANCE: Admin inherits properties and methods from User class

    public Admin(String name, String email, String password) {
        super(name, email, password, "admin");
    }

    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Products");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. View All Orders");
            System.out.println("6. Logout");
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
                    viewAllProducts();
                    break;
                case 2:
                    addProduct(sc);
                    break;
                case 3:
                    updateProduct(sc);
                    break;
                case 4:
                    deleteProduct(sc);
                    break;
                case 5:
                    viewAllOrders();
                    break;
                case 6:
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private void viewAllProducts() {
        List<Product> products = FileHandler.loadAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found. Add products first.");
            return;
        }
        ProductSearchSort.displayProducts(products);
    }

    private void addProduct(Scanner sc) {
        System.out.println("\n--- Add New Product ---");
        System.out.print("Product ID (e.g. P031): ");
        String id = sc.nextLine();

        // Check for duplicate product ID to prevent silent data corruption
        List<Product> existing = FileHandler.loadAllProducts();
        for (Product p : existing) {
            if (p.getProductId().equalsIgnoreCase(id)) {
                System.out.println("Product ID already exists. Use a different ID.");
                return;
            }
        }

        System.out.print("Product Name           : ");
        String name = sc.nextLine();
        System.out.print("Category               : ");
        String category = sc.nextLine();
        System.out.print("Price (Rs.)            : ");
        double price;
        int stock;
        try {
            price = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Stock                  : ");
            stock = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price or stock value. Product not added.");
            return;
        }

        FileHandler.saveProduct(new Product(id, name, category, price, stock));
        System.out.println("Product added successfully.");
    }

    private void updateProduct(Scanner sc) {
        System.out.print("\nEnter Product ID to update: ");
        String id = sc.nextLine();

        List<Product> products = FileHandler.loadAllProducts();
        boolean found = false;

        for (Product p : products) {
            if (p.getProductId().equalsIgnoreCase(id)) {
                found = true;
                System.out.println("Current details — Name: " + p.getName()
                        + " | Category: " + p.getCategory()
                        + " | Price: Rs." + (int) p.getPrice()
                        + " | Stock: " + p.getStock());

                System.out.println("\nWhat would you like to update?");
                System.out.println("1. Name");
                System.out.println("2. Category");
                System.out.println("3. Price");
                System.out.println("4. Stock");
                System.out.println("5. All fields");
                System.out.print("Enter choice: ");

                int opt;
                try {
                    opt = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice. Update cancelled.");
                    return;
                }

                try {
                    if (opt == 1 || opt == 5) {
                        System.out.print("Enter new name: ");
                        p.setName(sc.nextLine());
                    }
                    if (opt == 2 || opt == 5) {
                        System.out.print("Enter new category: ");
                        p.setCategory(sc.nextLine());
                    }
                    if (opt == 3 || opt == 5) {
                        System.out.print("Enter new price: ");
                        p.setPrice(Double.parseDouble(sc.nextLine().trim()));
                    }
                    if (opt == 4 || opt == 5) {
                        System.out.print("Enter new stock: ");
                        p.setStock(Integer.parseInt(sc.nextLine().trim()));
                    }
                    if (opt < 1 || opt > 5) {
                        System.out.println("Invalid choice. Update cancelled.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value entered. Update cancelled.");
                    return;
                }
                break;
            }
        }

        if (found) {
            FileHandler.rewriteProducts(products);
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Product ID not found.");
        }
    }

    private void deleteProduct(Scanner sc) {
        System.out.print("\nEnter Product ID to delete: ");
        String id = sc.nextLine();

        List<Product> products = FileHandler.loadAllProducts();
        boolean removed = products.removeIf(p -> p.getProductId().equalsIgnoreCase(id));

        if (removed) {
            FileHandler.rewriteProducts(products);
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product ID not found.");
        }
    }

    private void viewAllOrders() {
        List<String> allLines = FileHandler.loadAllOrders();

        System.out.println("\n--- All Orders (Admin View) ---");

        if (allLines.isEmpty()) {
            System.out.println("No orders have been placed yet.");
            return;
        }

        int count = 0;
        for (String line : allLines) {
            try {
                Order o = Order.fromFileString(line);
                o.display();
                count++;
            } catch (Exception e) {
                System.out.println("Skipping malformed order entry.");
            }
        }
        System.out.println("\nTotal orders: " + count);
    }
}
