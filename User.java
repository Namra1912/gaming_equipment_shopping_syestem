/**
 * User class represents a user in the shopping system.
 * Can be either a customer or an admin user.
 */
public class User {

    // ENCAPSULATION: private variables hidden from outside, accessed only through getter/setter methods
    private String name;
    private String email;
    private String password;
    private String role;

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toFileString() {
        return name + "|" + email + "|" + password + "|" + role;
    }

    public static User fromFileString(String line) {
        // Support both old comma format and new pipe format for backward compatibility
        String[] parts = line.contains("|") ? line.split("\\|") : line.split(",");
        return new User(parts[0], parts[1], parts[2], parts[3]);
    }
}
