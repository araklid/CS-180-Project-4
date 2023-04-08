import java.util.ArrayList;


public class Customer extends User {
    private ArrayList<Product> shoppingCart;
    private ArrayList<Product> purchaseHistory;
    public Customer(String username, String password, ArrayList<Product> shoppingCart, ArrayList<Product> purchaseHistory) {
        super(username, password);
        this.shoppingCart = shoppingCart;
        this.purchaseHistory = purchaseHistory;
    }
    public Customer(String username, String password) {
        super(username, password);
        shoppingCart = new ArrayList<>();
    }

    public void setShoppingCart(ArrayList<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void addToShoppingCart(Product product) {
        this.shoppingCart.add(product);
    }

    public void removeFromShoppingCart(Product product) {
        this.shoppingCart.remove(product);
    }

    ////

    public void setPurchaseHistory(ArrayList<Product> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public ArrayList<Product> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void addToPurchaseHistory(Product product) {
        this.purchaseHistory.add(product);
    }

    public void removeFromPurchaseHistory(Product product) {
        this.purchaseHistory.remove(product);
    }


}
