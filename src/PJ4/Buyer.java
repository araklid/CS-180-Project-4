package src.PJ4;

import java.util.ArrayList;

/**
 * Homework X
 *
 * @author Weston Walker, L08
 * @version Jan 1, 2000
 **/
public class Buyer extends User {
    private ArrayList<Product> shoppingCart;
    public Buyer(String username, String password, ArrayList<Product> shoppingCart) {
        super(username, password);
        this.shoppingCart = shoppingCart;
    }
    public Buyer(String username, String password) {
        super(username, password);
        shoppingCart = new ArrayList<>();
    }


}
