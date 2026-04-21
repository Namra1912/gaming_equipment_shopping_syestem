import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ProductSearchSort class provides methods to search and sort products.
 * Supports searching by keyword and sorting by price or name.
 * UTILITY CLASS: All methods are static, providing common functionality without needing object instantiation
 */
public class ProductSearchSort {

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

    public static void sortByPriceLowToHigh(List<Product> products) {
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
    }

    public static void sortByPriceHighToLow(List<Product> products) {
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice).reversed());
    }

    public static void sortByNameAtoZ(List<Product> products) {
        Collections.sort(products, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
    }

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
