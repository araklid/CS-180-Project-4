import java.io.*;
import java.util.*;

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

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void addStores(Store store) {
        this.stores.add(store);
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void removeStore(Store store) {
        stores.remove(store);
    }
    public void addStore(Store store) {
        stores.add(store);
    }
    public Store findStore(String storeName) {
        Store foundStore = null;
        for (Store store : stores) {
            if (store.getName().equals(storeName)) {
                foundStore = store;
            }
        }
        return foundStore;
    }








}
