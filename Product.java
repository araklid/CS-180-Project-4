
public class Product {
    private String name;
    private String nameOfStore;
    private String description;
    private double price;
    private int quantityAvailable;
    private Seller seller;
    public Product(String nameOfStore, String name, String description, int quantityAvailable, double price) {
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
    public Seller getSeller() {
        return seller;
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

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
