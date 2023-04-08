package src.PJ4;

import java.util.ArrayList;
//Sellers can view a list of their sales by store, including customer information and revenues from the sale.
/**
 * Homework X
 *
 * @author Weston Walker, L08
 * @version Jan 1, 2000
 **/
public class Store {
    private ArrayList<Product> products;
    private String name;
    public Store(ArrayList<Product> products, String name) {
        this.products = products;
        this.name = name;
    }
    public Store(String name) {
        this.name = name;
        products = new ArrayList<>();
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }
    public void modifyProduct(String name, String newName, String nameOfStore, String description, double price,
                              int quantityAvailable) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (name.equals(products.get(i).getName())) {
                index = i;
            }
        }
        products.set(index, new Product(newName, nameOfStore, description, price, quantityAvailable));
    }
}
