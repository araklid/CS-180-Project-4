package src.PJ4;
import java.util.*;
/**
 * Homework X
 *
 * @author Weston Walker, L08
 * @version Jan 1, 2000
 **/
public class Seller extends User {
    private ArrayList<Store> stores;
    public Seller(String username, String password, ArrayList<Store> stores) {
        super(username, password);
        this.stores = stores;
    }
    public Seller(String username, String password) {
        super(username, password);
        stores = new ArrayList<>();
    }

}
