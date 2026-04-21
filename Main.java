import java.util.Scanner;

/**
 * Main class that runs the online shopping system.
 * Handles user registration, login, and menu navigation.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("==========================================");
        System.out.println("  Gaming Equipment Online Shopping System ");
        System.out.println("==========================================");

        do {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
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
                    registerUser(sc);
                    break;
                case 2:
                    loginUser(sc);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);

        sc.close();
    }

    private static void registerUser(Scanner sc) {
        System.out.println("\n--- Register ---");
        System.out.print("Enter Name     : ");
        String name = sc.nextLine();
        System.out.print("Enter Email    : ");
        String email = sc.nextLine();

        if (FileHandler.emailExists(email)) {
            System.out.println("Account already exists with this email.");
            return;
        }

        System.out.print("Enter Password : ");
        String password = sc.nextLine();
        System.out.print("Role (admin/customer): ");
        String role = sc.nextLine().toLowerCase();

        if (!role.equals("admin") && !role.equals("customer")) {
            System.out.println("Invalid role.");
            return;
        }

        // Restrict admin registration with a secret code read from admin_secret.txt
        if (role.equals("admin")) {
            System.out.print("Enter Admin Secret Code: ");
            String code = sc.nextLine();
            if (!FileHandler.validateAdminSecret(code)) {
                System.out.println("Invalid admin code. Registration denied.");
                return;
            }
        }

        FileHandler.saveUser(new User(name, email, password, role));
        System.out.println("Registered successfully! You can now login as " + role + ".");
    }

    private static void loginUser(Scanner sc) {
        System.out.println("\n--- Login ---");
        System.out.print("Enter Email    : ");
        String email = sc.nextLine();
        System.out.print("Enter Password : ");
        String password = sc.nextLine();

        User user = FileHandler.validateLogin(email, password);

        if (user == null) {
            System.out.println("Incorrect email or password.");
            return;
        }

        System.out.println("Welcome, " + user.getName() + "!");

        // POLYMORPHISM: Single variable (user) with different behaviors based on role
        // Admin and Customer both inherit from User but have different showMenu() implementations
        if (user.getRole().equals("admin")) {
            new Admin(user.getName(), user.getEmail(), user.getPassword()).showMenu(sc);
        } else {
            new Customer(user.getName(), user.getEmail(), user.getPassword()).showMenu(sc);
        }
    }
}
