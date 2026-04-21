/**
 * User class represents a user in the shopping system.
 * Can be either a customer or an admin user.
 */
public class User {

    private String name;
    private String email;
    private String password;
    private String role;

    /**
     * Constructor to create a User.
     * @param name The user's name
     * @param email The user's email
     * @param password The user's password
     * @param role The user's role (admin or customer)
     */
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the user's name.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user's email.
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's password.
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the user's role.
     * @return The role (admin or customer)
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's name.
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the user's email.
     * @param email The new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's password.
     * @param p The new password
     */
    public void setPassword(String p) {
        this.password = p;
    }

    /**
     * Sets the user's role.
     * @param role The new role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Converts the user to a string format for file storage.
     * Uses '|' as delimiter to safely handle names or emails containing commas.
     * @return String representation of the user
     */
    public String toFileString() {
        return name + "|" + email + "|" + password + "|" + role;
    }

    /**
     * Creates a User from a file string.
     * Supports both legacy comma-separated format and the new pipe-separated format.
     * @param line The line from the users file
     * @return A User object
     */
    public static User fromFileString(String line) {
        // Support both old comma format and new pipe format for backward compatibility
        String[] parts = line.contains("|") ? line.split("\\|") : line.split(",");
        return new User(parts[0], parts[1], parts[2], parts[3]);
    }
}
