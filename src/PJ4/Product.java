package src.PJ4;
/**
* Homework X
* @author Weston Walker, L08
* @version Jan 1, 2000
**/
public class Product {
    private String name;
    private String nameOfStore;
    private String description;
    private double price;
    private int quantityAvailable;
    public Product(String name, String nameOfStore, String description, double price, int quantityAvailable) {
        this.name = name;
        this.nameOfStore = nameOfStore;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public String getDescription() {
        return description;
    }

    public String getNameOfStore() {
        return nameOfStore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNameOfStore(String nameOfStore) {
        this.nameOfStore = nameOfStore;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

}
