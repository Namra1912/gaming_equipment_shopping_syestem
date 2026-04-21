import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ProductSearchSort class provides methods to search and sort products.
 * Supports searching by keyword and sorting by price or name.
 */
public class ProductSearchSort {

    /**
     * Searches products by keyword in name or category.
     * @param products List of products to search
     * @param keyword The keyword to search for
     * @return List of products matching the keyword
     */
    public static List<Product> search(List<Product> products, String keyword) {
        List<Product> results = new ArrayList<>();
        String kw = keyword.toLowerCase();

        for (Product p : products) {
            if (p.getName().toLowerCase().contains(kw) ||
                p.getCategory().toLowerCase().contains(kw)) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Sorts products by price from lowest to highest.
     * @param products List of products to sort
     */
    public static void sortByPriceLowToHigh(List<Product> products) {
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
    }

    /**
     * Sorts products by price from highest to lowest.
     * @param products List of products to sort
     */
    public static void sortByPriceHighToLow(List<Product> products) {
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice).reversed());
    }

    /**
     * Sorts products by name alphabetically (case-insensitive).
     * @param products List of products to sort
     */
    public static void sortByNameAtoZ(List<Product> products) {
        Collections.sort(products, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
    }

    /**
     * Displays a list of products in a formatted table.
     * @param products List of products to display
     */
    public static void displayProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        System.out.println();
        System.out.printf("%-6s %-35s %-20s %-14s %s%n", "ID", "Product Name", "Category", "Price (Rs.)", "Stock");
        System.out.println("-".repeat(85));
        for (Product p : products) {
            p.display();
        }
        System.out.println();
    }
}
