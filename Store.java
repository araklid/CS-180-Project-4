import java.util.ArrayList;
//Sellers can view a list of their sales by store, including customer information and revenues from the sale.

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

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
    public void removeProduct(Product product) {
        products.remove(product);
    }


    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }

    public void modifyProduct(Product product, String name, String nameOfStore, String description, double price, int quantityAvailable, Seller seller) {

        for (int i = 0; i < products.size(); i++) {
            if (product.equals(products.get(i))) {
                Product newProduct = new Product(name, nameOfStore, description, quantityAvailable, price);
                products.set(i, newProduct);
            }
        }

    }



}
