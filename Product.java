/**
 * Product class represents an item available for purchase.
 * Contains product details like ID, name, category, price, and stock quantity.
 */
public class Product {

    private String productId;
    private String name;
    private String category;
    private double price;
    private int stock;

    /**
     * Constructor to create a Product.
     * @param productId Unique product ID
     * @param name Product name
     * @param category Product category
     * @param price Product price in rupees
     * @param stock Available stock quantity
     */
    public Product(String productId, String name, String category, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Gets the product ID.
     * @return The product ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Gets the product name.
     * @return The product name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the product category.
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the product price.
     * @return The price in rupees
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the available stock quantity.
     * @return The stock quantity
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity.
     * @param stock The new stock quantity
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the product price.
     * @param price The new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the product name.
     * @param name The new product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the product category.
     * @param category The new product category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Converts the product to a string format for file storage.
     * @return String representation of the product
     */
    public String toFileString() {
        return productId + "," + name + "," + category + "," + price + "," + stock;
    }

    /**
     * Creates a Product from a file string.
     * @param line The line from the products file
     * @return A Product object
     */
    public static Product fromFileString(String line) {
        String[] p = line.split(",");
        return new Product(p[0], p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4]));
    }

    /**
     * Displays product information in a formatted way.
     */
    public void display() {
        System.out.printf("%-6s %-35s %-20s Rs.%-10.0f Stock: %d%n",
                productId, name, category, price, stock);
    }
}
