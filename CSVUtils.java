import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    public static List<Product> importProductsFromCSV(String filePath) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Product product = new Product(values[0], values[1], values[2], Integer.parseInt(values[3]), Double.parseDouble(values[4]));
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void exportProductsToCSV(List<Product> products, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Product product : products) {
                writer.append(product.getNameOfStore())
                        .append(",")
                        .append(product.getName())
                        .append(",")
                        .append(product.getDescription())
                        .append(",")
                        .append(String.valueOf(product.getQuantityAvailable()))
                        .append(",")
                        .append(String.valueOf(product.getPrice()))
                        .append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportCustomerShoppingCartToCSV(Customer customer, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Product product : customer.getShoppingCart()) {
                writer.append(product.getNameOfStore())
                        .append(",")
                        .append(product.getName())
                        .append(",")
                        .append(product.getDescription())
                        .append(",")
                        .append(String.valueOf(product.getQuantityAvailable()))
                        .append(",")
                        .append(String.valueOf(product.getPrice()))
                        .append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportCustomerPurchaseHistoryToCSV(Customer customer, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Product product : customer.getPurchaseHistory()) {
                writer.append(product.getNameOfStore())
                        .append(",")
                        .append(product.getName())
                        .append(",")
                        .append(product.getDescription())
                        .append(",")
                        .append(String.valueOf(product.getQuantityAvailable()))
                        .append(",")
                        .append(String.valueOf(product.getPrice()))
                        .append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
