/**
 * Product class represents an item available for purchase.
 * Contains product details like ID, name, category, price, and stock quantity.
 */
public class Product {

    // ENCAPSULATION: private fields keep data hidden from direct external access
    private String productId;
    private String name;
    private String category;
    private double price;
    private int stock;

    public Product(String productId, String name, String category, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toFileString() {
        return productId + "," + name + "," + category + "," + price + "," + stock;
    }

    public static Product fromFileString(String line) {
        String[] p = line.split(",");
        return new Product(p[0], p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4]));
    }

    public void display() {
        System.out.printf("%-6s %-35s %-20s Rs.%-10.0f Stock: %d%n",
                productId, name, category, price, stock);
    }
}
