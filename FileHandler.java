import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileHandler class manages all file operations for users, products, and orders.
 * Handles saving and loading data from text files.
 */
public class FileHandler {

    static final String USERS_FILE        = "users.txt";
    static final String PRODUCTS_FILE     = "products.txt";
    static final String ORDERS_FILE       = "orders.txt";
    static final String ADMIN_SECRET_FILE = "admin_secret.txt";

    /**
     * Reads the admin secret code from admin_secret.txt.
     * If the file does not exist, it is created with a default code "ADMIN123".
     * The admin should change this code by editing admin_secret.txt directly.
     * @return The admin secret code as a String
     */
    public static String readAdminSecret() {
        File file = new File(ADMIN_SECRET_FILE);
        if (!file.exists()) {
            // Create the file with a default code on first run
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADMIN_SECRET_FILE))) {
                bw.write("ADMIN123");
                System.out.println("admin_secret.txt created with default code. Please change it.");
            } catch (IOException e) {
                System.out.println("Error creating admin secret file: " + e.getMessage());
            }
            return "ADMIN123";
        }
        try (BufferedReader br = new BufferedReader(new FileReader(ADMIN_SECRET_FILE))) {
            String code = br.readLine();
            if (code != null && !code.trim().isEmpty()) return code.trim();
        } catch (IOException e) {
            System.out.println("Error reading admin secret file: " + e.getMessage());
        }
        return "";
    }

    /**
     * Validates whether the entered code matches the admin secret stored in admin_secret.txt.
     * @param enteredCode The code entered by the user during admin registration
     * @return True if the code matches, false otherwise
     */
    public static boolean validateAdminSecret(String enteredCode) {
        return readAdminSecret().equals(enteredCode);
    }

    /**
     * Saves a user to the users file.
     * @param user The user to save
     */
    public static void saveUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            bw.write(user.toFileString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    /**
     * Loads all users from the users file.
     * @return A list of all users in the system
     */
    public static List<User> loadAllUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) users.add(User.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Validates user login credentials.
     * @param email The user's email
     * @param password The user's password
     * @return The user if credentials match, null otherwise
     */
    public static User validateLogin(String email, String password) {
        for (User u : loadAllUsers()) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password))
                return u;
        }
        return null;
    }

    /**
     * Checks if an email already exists in the system.
     * @param email The email to check
     * @return True if email exists, false otherwise
     */
    public static boolean emailExists(String email) {
        for (User u : loadAllUsers()) {
            if (u.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    /**
     * Loads all products from the products file.
     * @return A list of all products in the system
     */
    public static List<Product> loadAllProducts() {
        List<Product> products = new ArrayList<>();
        File file = new File(PRODUCTS_FILE);
        if (!file.exists()) return products;

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) products.add(Product.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
        return products;
    }

    /**
     * Saves a product to the products file.
     * @param product The product to save
     */
    public static void saveProduct(Product product) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCTS_FILE, true))) {
            bw.write(product.toFileString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving product: " + e.getMessage());
        }
    }

    /**
     * Rewrites the products file with an updated list of products.
     * @param products The list of products to save
     */
    public static void rewriteProducts(List<Product> products) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCTS_FILE, false))) {
            for (Product p : products) {
                bw.write(p.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating products file: " + e.getMessage());
        }
    }

    /**
     * Saves an order to the orders file.
     * @param orderLine The order line to save
     */
    public static void saveOrder(String orderLine) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDERS_FILE, true))) {
            bw.write(orderLine);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }

    /**
     * Loads all orders from the orders file.
     * @return A list of all orders in the system
     */
    public static List<String> loadAllOrders() {
        List<String> orders = new ArrayList<>();
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return orders;

        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) orders.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
        return orders;
    }
}
