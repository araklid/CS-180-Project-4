import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/** Dilara Baysal
 * 4/6/2023
 *
 */

public class Menu {

    // Class Variables to make John's life easier. Acts as a median to relay information.
    static ArrayList<String> storeNames = new ArrayList<String>();
    static ArrayList<String> storeProductList = new ArrayList<String>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Clear global variables
        for (int i = 0; i < storeNames.size(); i++) {
            storeNames.remove(storeNames.get(i));
            storeProductList.remove(storeProductList.get(i));
        }
        // Start main logic

        System.out.println("Welcome to the Marketplace!");

        ArrayList<Seller> sellers = setSellers();
        ArrayList<Customer> customers = setCustomers();

        //Customer[]

        String acc = "";

        while (!acc.equals("new") && !acc.equals("log in")) {
            System.out.println("Would you like to create a new account (new), log in (log in), or quit (q)?");
            acc = scan.nextLine();
            if (acc.equals("new")) {
                create(scan, sellers, customers);
            } else if (acc.equals("log in")) {
                logIn(scan, sellers, customers);
            } else if (acc.equals("q")) {
                System.out.println("Goodbye!");
                return;
            } else {
                System.out.println("Not a valid entry!");
            }
        }
    }

    public static ArrayList<Seller> setSellers() {
        String[] sellerData = readFile("Sellers.txt");
        ArrayList<Seller> sellers = new ArrayList<>();
        if (sellerData.length > 0) {
            for (int i = 0; i < sellerData.length; i++) {
                String[] dataLine = sellerData[i].split(";");
                Seller seller = new Seller(dataLine[0], dataLine[1]);

                for (int j = 2; j < dataLine.length; j++) {
                    String[] storeData = dataLine[j].split(",");
                    Store[] stores = new Store[storeData.length - 1];

                    stores[j] = new Store(storeData[0]);
                    for (int k = 1; k < storeData.length - 1; k = k + 4) {
                        Product product = new Product(storeData[0], storeData[k],
                                storeData[k + 1], Integer.parseInt(storeData[k + 2]),
                                Double.parseDouble(storeData[k + 3]));
                        stores[j].addProduct(product);
                    }
                    seller.addStores(stores[j]);
                }
                sellers.add(seller);
            }

        }

        //System.out.println(sellers.get(0).getStores().get(0).getProducts());
        return sellers;
    }

    public static ArrayList<Customer> setCustomers() {
        String[] customerData = readFile("Customers.txt");
        ArrayList<Customer> customers = new ArrayList<>();
        if (customerData.length > 0) {
            for (int i = 0; i < customerData.length; i++) {
                String[] dataLine = customerData[i].split(";");
                Customer customer = new Customer(dataLine[0], dataLine[1]);

                if (dataLine.length > 3) {
                    customer.setWorkingPurchaseHistory(dataLine[3]);
                }
                //customer file: username;pass;shoppingcart;purchasehistory
                //shoppingcart and purchase history are not organized by shop

                if (dataLine.length > 2) {
                    String[] shoppingCart = dataLine[2].split(",");

                    for (int j = 0; j < shoppingCart.length; j = j + 5) {
                        customer.addToShoppingCart(new Product(shoppingCart[j],
                                shoppingCart[j + 1], shoppingCart[j + 2],
                                Integer.parseInt(shoppingCart[j + 3]),
                                Double.parseDouble(shoppingCart[j + 4])));
                    }
                } else {
                    customer.setShoppingCart(new ArrayList<>());
                }

                if (dataLine.length > 3) {
                    String[] purchaseHistory = dataLine[3].split(",");

                    for (int j = 0; j < purchaseHistory.length; j = j + 5) {
                        customer.addToPurchaseHistory(new Product(purchaseHistory[j],
                                purchaseHistory[j + 1], purchaseHistory[j + 2],
                                Integer.parseInt(purchaseHistory[j + 3]),
                                Double.parseDouble(purchaseHistory[j + 4])));
                    }
                } else {
                    customer.setPurchaseHistory(new ArrayList<>());
                }
                customers.add(customer);
            }
        }
        return customers;
    }

    public static void create(Scanner scan, ArrayList<Seller> sellers, ArrayList<Customer> customers) {
        System.out.println("Account creation: Are you a seller (s) or a customer (c)?\nQuit: (q)");
        String slec = scan.nextLine();

        if (slec.equals("s")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();
            if (!userName.equals("q")) {
                for (int i = 0; i < sellers.size(); i++) {
                    if (sellers.get(i).getUsername().equals(userName)) {
                        System.out.println("This username is already taken!");
                        redo(scan, sellers, customers);
                    }
                }

                System.out.println("Enter your password!\nQuit: (q)");
                String password = scan.nextLine();

                Seller seller = new Seller(userName, password);
                sellers.add(seller);
                System.out.println("Welcome " + seller.getUsername() + "!");
                sellerAccount(scan, seller, sellers);
            } else {
                System.out.println("Goodbye!");
            }
        } else if (slec.equals("c")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();
            if (!userName.equals("q")) {
                for (int i = 0; i < customers.size(); i++) {
                    if (customers.get(i).getUsername().equals(userName)) {
                        System.out.println("This username is already taken!");
                        redo(scan, sellers, customers);
                    }
                }

                System.out.println("Enter your password!\nQuit: (q)");
                String password = scan.nextLine();
                Customer customer = new Customer(userName, password);
                customers.add(customer);
                System.out.println("Welcome " + customer.getUsername() + "!");
                customerAccount(scan, sellers, customer, customers);
            } else {
                System.out.println("Goodbye!");
            }

        } else if (slec.equals("q")) {
            System.out.println("Goodbye!");
        } else {
            System.out.println("Not a valid entry!");
            create(scan, sellers, customers);
        }
    }

    public static Product displayFunctions(Scanner scan, ArrayList<Seller> sellers, ArrayList<Customer> customers) {

        ArrayList<Product> list = display(sellers);

        System.out.println("Enter (search) to search the marketplace, (quantity) to sort by quantity available, or (price) to sort by price: ");
        String entry = scan.nextLine();

        if (entry.equals("search")) {
            search(scan, sellers, customers);
        } else if (entry.equals("quantity")) {
            displayByQuantity(sellers, list); // currently doesn't work
        } else if (entry.equals("price")) {
            displayByCost(sellers, list);
        }

        System.out.println("Enter the name of the product, or enter (again) to search again: ");
        String prodNamMaybe = scan.nextLine();

        if (prodNamMaybe.equals("again")) {
            displayFunctions(scan,sellers, customers);
        } else {
            boolean temp = false;
            for (int i = 0; i < sellers.size(); i++) {
                for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                    for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                        if (sellers.get(i).getStores().get(j).getProducts().get(k).getName().toLowerCase().equals(prodNamMaybe.toLowerCase())) {
                            temp = true;
                            //System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getName());
                            return sellers.get(i).getStores().get(j).getProducts().get(k);
                        }
                    }
                }
            }
            if (!temp) {
                System.out.println("No product matches this entry, would you like to select another (y/n)?");
                String yesOrNo = scan.nextLine();
                if (yesOrNo.equals("y")) {
                    displayFunctions(scan, sellers, customers);
                } else {
                    System.out.println("Goodbye!");
                    writeSellers(sellers);
                }
            }
        }
        return null;
    }

    public static ArrayList<Product> display(ArrayList<Seller> sellers) {
        ArrayList<Product> list = new ArrayList<>();

        for (int i = 0; i < sellers.size(); i++) {
            for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                System.out.println("*************************");
                System.out.println(sellers.get(i).getStores().get(j).getName());
                System.out.println("*************************");
                for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                    System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getName());
                    //System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getDescription());
                    //System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getQuantityAvailable() + " units");
                    System.out.printf("$%.2f", sellers.get(i).getStores().get(j).getProducts().get(k).getPrice());
                    list.add(sellers.get(i).getStores().get(j).getProducts().get(k));
                    System.out.println("\n------------------------");
                }
            }
        }
        return list;
    }

    public static void storeProductInitialization(ArrayList<Seller> sellers) {
        for (int i = 0; i < sellers.size(); i++) {
            for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                    storeNames.add(String.valueOf(sellers.get(i).getStores().get(j).getName()));
                    storeProductList.add(String.valueOf(sellers.get(i).getStores().get(j).getProducts().get(k).getName()));
                }
            }
        }
    }
    public static void displayByCost(ArrayList<Seller> sellers, ArrayList<Product> list) {
        ArrayList<Product> sortedProducts = new ArrayList<>();
        int index = -1;
        int size = list.size();
        while (sortedProducts.size() < size) {
            double price = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getPrice() >= price) {
                    price = list.get(i).getPrice();
                    index = i;
                }
            }
            sortedProducts.add(list.get(index));
            list.remove(index);
        }

        for (int i = 0; i < sortedProducts.size(); i++) {
            System.out.println("*************************");
            System.out.println(sortedProducts.get(i).getNameOfStore());
            System.out.println("*************************");
            System.out.println(sortedProducts.get(i).getName());
            //System.out.println(sortedProducts.get(i).getDescription());
            //System.out.println(sortedProducts.get(i).getQuantityAvailable() + " units");
            System.out.printf("$%.2f\n", sortedProducts.get(i).getPrice());
        }
    }

    public static void displayByQuantity(ArrayList<Seller> sellers, ArrayList<Product> list) {
        ArrayList<Product> sortedProducts = new ArrayList<>();
        int index = -1;
        int size = list.size();
        while (sortedProducts.size() < size) {
            int quant = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getQuantityAvailable() >= quant) {
                    quant = list.get(i).getQuantityAvailable();
                    index = i;
                }
            }
            sortedProducts.add(list.get(index));
            list.remove(index);
        }

        for (int i = 0; i < sortedProducts.size(); i++) {
            System.out.println("*************************");
            System.out.println(sortedProducts.get(i).getNameOfStore());
            System.out.println("*************************");
            System.out.println(sortedProducts.get(i).getName());
            //System.out.println(sortedProducts.get(i).getDescription());
            //System.out.println(sortedProducts.get(i).getQuantityAvailable() + " units");
            System.out.printf("$%.2f\n", sortedProducts.get(i).getPrice());
        }
    }

    public static void search(Scanner scan, ArrayList<Seller> sellers, ArrayList<Customer> customers) {
        System.out.println("Search by name, store, or description of products: ");
        String product = scan.nextLine();

        boolean temp = false;
        System.out.println("------------------------------------");

        for (int i = 0; i < sellers.size(); i++) {
            for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                    if (sellers.get(i).getStores().get(j).getProducts().get(k).getName().toLowerCase().contains(product.toLowerCase()) ||
                            sellers.get(i).getStores().get(j).getProducts().get(k).getDescription().toLowerCase().contains(product.toLowerCase()) ||
                            sellers.get(i).getStores().get(j).getProducts().get(k).getNameOfStore().toLowerCase().contains(product.toLowerCase())) {

                        System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getName());
                        //System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getDescription());
                        //System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getQuantityAvailable());
                        System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getPrice());
                        temp = true;
                        System.out.println("------------------------------------");
                    }
                }
            }
        }

        if (!temp) {
            System.out.println("No product matches this entry, would you like to search again (y/n)?");
            String yesOrNo = scan.nextLine();
            if (yesOrNo.equals("y")) {
                displayFunctions(scan, sellers, customers);
            } else {
                System.out.println("Goodbye!");
                writeCustomers(customers);
            }
        }
    }

    public static void logIn(Scanner scan, ArrayList<Seller> sellers, ArrayList<Customer> customers) {

        System.out.println("Login: Are you a seller (s) or a customer (c)?\nQuit: (q)");
        String slec = scan.nextLine();

        if (slec.equals("s")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();

            if (!userName.equals("q")) {
                boolean found = false;
                for (int i = 0; i < sellers.size(); i++) {
                    if (sellers.get(i).getUsername().equals(userName)) {
                        System.out.println("Enter your password!\nQuit: (q)");
                        String password = scan.nextLine();

                        if (!password.equals("q")) {
                            if (sellers.get(i).getPassword().equals(password)) {
                                System.out.println("Welcome " + userName + "!");
                                Seller seller = sellers.get(i);
                                sellerAccount(scan, seller, sellers);

                            } else {
                                System.out.println("Your password is incorrect!");
                                redo(scan, sellers, customers);
                            }
                        }
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("You are not in the system!");
                    redo(scan, sellers, customers);
                }
            }
        } else if (slec.equals("c")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();

            if (!userName.equals("q")) {
                boolean found = false;
                for (int i = 0; i < customers.size(); i++) {
                    if (customers.get(i).getUsername().equals(userName)) {
                        System.out.println("Enter your password!\nQuit: (q)");
                        String password = scan.nextLine();

                        if (!password.equals("q")) {
                            if (customers.get(i).getPassword().equals(password)) {
                                System.out.println("Welcome " + userName + "!");
                                Customer customer = customers.get(i);
                                customerAccount(scan, sellers, customer, customers);


                            } else {
                                System.out.println("Your password is incorrect!");
                                redo(scan, sellers, customers);
                            }
                        }
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("You are not in the system!");
                    redo(scan, sellers, customers);
                }
            }
        } else if (slec.equals("q")) {
            System.out.println("Goodbye!");
        } else {
            System.out.println("Not a valid entry!");
            logIn(scan, sellers, customers);
        }
    }

    public static String[] readFile(String filePath) {
        ArrayList<String> listy = new ArrayList<>();

        try (FileReader fr = new FileReader(filePath);
             BufferedReader bfr = new BufferedReader(fr)) {
            String line = bfr.readLine();
            listy.add(line);
            while (line != null) {
                line = bfr.readLine();
                if (line != null) {
                    listy.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (listy.toArray(new String[listy.size()]));
    }

    public static void write(String fileName, String userName, String password) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, true);
            PrintWriter pw = new PrintWriter(fos);

            pw.println(userName + ";" + password);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void redo(Scanner scan, ArrayList<Seller> sellers, ArrayList<Customer> customers) {
        System.out.println("Would you like to log in again (log in) or create a new account (new)?\nQuit: (q)");
        String redo = scan.nextLine();
        if (redo.equals("log in")) {
            logIn(scan, sellers, customers);
        } else if (redo.equals("new")) {
            create(scan, sellers, customers);
        } else if (redo.equals("q")) {
            System.out.println("Goodbye!");
        } else {
            System.out.println("Not a valid entry!");
            redo(scan, sellers, customers);
        }
    }

    public static void customerAccount(Scanner scan, ArrayList<Seller> sellers, Customer customer, ArrayList<Customer> customers) {
        // John added call that initializes class variables
        storeProductInitialization(sellers);

        //dashboard
        System.out.println("Would you like to view products (view), view your shopping cart (cart), export your purchase history (hist), view your statistics (stats), or quit (q)?");
        String selection = scan.nextLine();

        if (selection.equals("view")) {
            Product productSelection = displayFunctions(scan, sellers, customers);
            System.out.println("*************************");
            System.out.println(productSelection.getNameOfStore());
            System.out.println("*************************");
            System.out.println(productSelection.getName());
            System.out.println(productSelection.getDescription());
            System.out.println(productSelection.getQuantityAvailable() + " units");
            System.out.printf("$%.2f", productSelection.getPrice());
            System.out.println("\n------------------------");

            System.out.println("Would you like to purchase " + productSelection.getName() + ", add to cart, or go back to the main menu?");
            System.out.println("1. Purchase\n2. Add\n3. Main Menu");
            String temp = scan.nextLine();

            if (temp.equals("1")) {
                System.out.println("Nice choice, you've just purchased " + productSelection.getName());
                productSelection.setQuantityAvailable(productSelection.getQuantityAvailable() - 1);
                customer.addToPurchaseHistory(productSelection);
                customerAccount(scan, sellers, customer, customers);

            } else if (temp.equals("2")) {
                System.out.println("Added " + productSelection.getName() + " to cart");
                customer.addToShoppingCart(productSelection);
                customerAccount(scan, sellers, customer, customers);
            } else if (temp.equals("3")) {
                customerAccount(scan, sellers, customer, customers);
            }

        } else if (selection.equals("q")) {
            System.out.println("Goodbye!");
            writeCustomers(customers);
        } else if (selection.equals("hist")) {
            if (customer.getPurchaseHistory().size() != 0 || customer.getPurchaseHistory() == null) {
                for (int i = 0; i < customer.getPurchaseHistory().size(); i++) {
                    System.out.println(customer.getPurchaseHistory().get(i).getName() + ", "
                            + customer.getPurchaseHistory().get(i).getNameOfStore() + ", "
                            + customer.getPurchaseHistory().get(i).getDescription() + ", "
                            + customer.getPurchaseHistory().get(i).getPrice());
                }
                customerAccount(scan, sellers, customer, customers);
            } else {
                System.out.println("Your purchase history is empty!");
                customerAccount(scan, sellers, customer, customers);
            }
        } else if (selection.equals("cart")) {

            if (customer.getShoppingCart().size() != 0 || customer.getShoppingCart() == null) {
                for (int i = 0; i < customer.getShoppingCart().size(); i++) {
                    System.out.println(customer.getShoppingCart().get(i).getName() + ", "
                            + customer.getShoppingCart().get(i).getNameOfStore() + ", "
                            + customer.getShoppingCart().get(i).getDescription() + ", "
                            + customer.getShoppingCart().get(i).getPrice());
                }

                System.out.println("Would you like to check out (check out), remove items from your cart (remove), return to shopping (return), or quit (q): ");
                String next = scan.nextLine();

                if (next.equals("check out")) {
                    for (int i = 0; i < customer.getShoppingCart().size(); i++) {
                        customer.addToPurchaseHistory(customer.getShoppingCart().get(i));
                        customer.removeFromShoppingCart(customer.getShoppingCart().get(i));
                    }
                    customerAccount(scan, sellers, customer, customers);
                } else if (next.equals("remove")) {
                    System.out.println("Which product would you like to remove?");
                    String remove = scan.nextLine();
                    for (int i = 0; i < customer.getShoppingCart().size(); i++) {
                        if (customer.getShoppingCart().get(i).getName().equals(remove)) {
                            customer.getShoppingCart().remove(customer.getShoppingCart().get(i));
                        }
                    }
                    for (int i = 0; i < customer.getShoppingCart().size(); i++) {
                        System.out.println(customer.getShoppingCart().get(i).getName() + ", "
                                + customer.getShoppingCart().get(i).getNameOfStore() + ", "
                                + customer.getShoppingCart().get(i).getDescription() + ", "
                                + customer.getShoppingCart().get(i).getPrice());
                    }
                    customerAccount(scan, sellers, customer, customers);
                } else if (next.equals("return")) {
                    customerAccount(scan, sellers, customer, customers);
                } else if (next.equals("q")) {
                    System.out.println("Goodbye!");
                    writeCustomers(customers);
                }
                customerAccount(scan, sellers, customer, customers);
            } else {
                System.out.println("Your shopping cart is empty!");
                customerAccount(scan, sellers, customer, customers);
            }
        } else if (selection.equalsIgnoreCase("stats")) {
            viewCustomerStats(customer, scan);
        }
    }

    public static void sellerAccount(Scanner scan, Seller seller, ArrayList<Seller> sellers) {
        System.out.println("Would you like to view statistics dashboard (1), import products (2), export products (3), edit products (4), or quit (q)?");
        String sOptions = scan.nextLine();
        if (sOptions.equals("1")) {
            //TODO: include amount of products in customer cart
            //TODO: allow for sorting by: name, amount in cart, amount in store, etc

            sellerAccount(scan, seller, sellers);
        } else if (sOptions.equals("2")) {
            System.out.println("Enter the filepath (products should be on separate lines):");
            String path = scan.nextLine();
            String[] sell = readFile(path);
            System.out.println("Enter the store associated with these products:");
            String temp = scan.nextLine();

            for (int j = 0; j < sell.length; j++) {
                String[] prodData = sell[j].split(",");
                Product product = new Product(prodData[0], prodData[1], prodData[2], Integer.parseInt(prodData[4]), Double.parseDouble(prodData[3]));
                for (int i = 0; i < seller.getStores().size(); i++) {
                    if (seller.getStores().get(i).getName().equals(temp)) {
                        seller.getStores().get(i).addProduct(product);
                    }
                }
            }
            sellerAccount(scan, seller, sellers);
        } else if (sOptions.equals("3")) {

            if (seller.getStores().size() > 0) {

                System.out.println("Enter the name of the store you would like to export: ");
                String storeSlec = scan.nextLine();
                //TODO: add a correction for if this is not a valid store

                for (int i = 0; i < seller.getStores().size(); i++) {
                    if (seller.getStores().get(i).getName().equals(storeSlec)) {
                        try {
                            File csvFile = new File(storeSlec + ".csv");
                            FileWriter fw = new FileWriter(csvFile);
                            for (int j = 0; j < seller.getStores().get(i).getProducts().size(); j++) {
                                fw.write(seller.getStores().get(i).getProducts().get(j).getName() + ", "
                                        + seller.getStores().get(i).getProducts().get(j).getNameOfStore() + ", "
                                        + seller.getStores().get(i).getProducts().get(j).getDescription() + ", "
                                        + seller.getStores().get(i).getProducts().get(j).getQuantityAvailable() + ", "
                                        + seller.getStores().get(i).getProducts().get(j).getPrice());
                            }
                            fw.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            } else {
                System.out.println("Store is empty.");
            }
            sellerAccount(scan, seller, sellers);
        } else if (sOptions.equals("4")) {
            //print dashboard
            System.out.println("Which store is this product located in?");
            String storeSlec = scan.nextLine();
            for (int i = 0; i < seller.getStores().size(); i++) {
                if (seller.getStores().get(i).getName().equals(storeSlec)) {
                    System.out.println("What is the product's name?");
                    String prodSlec = scan.nextLine();
                    for (int j = 0; j < seller.getStores().get(i).getProducts().size(); j++) {
                        if (seller.getStores().get(i).getProducts().get(j).getName().equals(prodSlec)) {
                            System.out.println("Enter the new name: ");
                            String newName = scan.nextLine();
                            System.out.println("Enter the new description: ");
                            String newDesc = scan.nextLine();
                            System.out.println("Enter the quantity available for purchase:");
                            int newQuant = scan.nextInt();
                            scan.nextLine();
                            System.out.println("Enter the price:");
                            double newPrice = scan.nextDouble();
                            scan.nextLine();
                            seller.getStores().get(i).modifyProduct(seller.getStores().get(i).getProducts().get(j),
                                    newName,
                                    storeSlec,
                                    newDesc,
                                    newPrice,
                                    newQuant,
                                    seller);
                            System.out.println("Success, the new product is now: " + seller.getStores().get(i).getProducts().get(j));
                        }
                    }
                }
            }
            sellerAccount(scan, seller, sellers);
        } else if (sOptions.equals("q")) {
            System.out.println("Goodbye!");
            writeSellers(sellers);
        }
    }

    public static void writeCustomers(ArrayList<Customer> customers) {
        try {
            FileOutputStream fos = new FileOutputStream("Customers.txt", false);
            PrintWriter pw = new PrintWriter(fos);

            for (int i = 0; i < customers.size(); i++) {
                pw.write(customers.get(i).getUsername());
                pw.write(";");
                pw.write(customers.get(i).getPassword());
                pw.write(";");
                if (customers.get(i).getShoppingCart() != null) {
                    for (int j = 0; j < customers.get(i).getShoppingCart().size(); j++) {
                        pw.write(customers.get(i).getShoppingCart().get(j).getNameOfStore() + ","
                                + customers.get(i).getShoppingCart().get(j).getName() + ","
                                + customers.get(i).getShoppingCart().get(j).getDescription() + ","
                                + customers.get(i).getShoppingCart().get(j).getQuantityAvailable() + ","
                                + customers.get(i).getShoppingCart().get(j).getPrice() + "0");
                        if (j < customers.get(i).getShoppingCart().size() - 1) {
                            pw.write(",");
                        }
                    }
                    pw.write(";");
                }
                if (customers.get(i).getPurchaseHistory() != null) {
                    for (int j = 0; j < customers.get(i).getPurchaseHistory().size(); j++) {
                        pw.write(customers.get(i).getPurchaseHistory().get(j).getNameOfStore() + ","
                                + customers.get(i).getPurchaseHistory().get(j).getName() + ","
                                + customers.get(i).getPurchaseHistory().get(j).getDescription() + ","
                                + customers.get(i).getPurchaseHistory().get(j).getQuantityAvailable() + ","
                                + customers.get(i).getPurchaseHistory().get(j).getPrice() + "0");
                        if (j < customers.get(i).getPurchaseHistory().size() - 1) {
                            pw.write(",");
                        }
                    }
                }
                pw.write("\n");
            }

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void writeSellers(ArrayList<Seller> sellers) {
        try {
            FileOutputStream fos = new FileOutputStream("Sellers.txt", false);
            PrintWriter pw = new PrintWriter(fos);

            for (int i = 0; i < sellers.size(); i++) {
                pw.write(sellers.get(i).getUsername());
                pw.write(";");
                pw.write(sellers.get(i).getPassword());
                pw.write(";");
                if (sellers.get(i).getStores() != null) {
                    for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                        pw.write(sellers.get(i).getStores().get(j).getName() + ",");
                        for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                            pw.write(sellers.get(i).getStores().get(j).getProducts().get(k).getName() + ","
                                    + sellers.get(i).getStores().get(j).getProducts().get(k).getDescription() + ","
                                    + sellers.get(i).getStores().get(j).getProducts().get(k).getQuantityAvailable() + ","
                                    + sellers.get(i).getStores().get(j).getProducts().get(k).getPrice() + "0");
                            if (k < sellers.get(i).getStores().get(j).getProducts().size() - 1) {
                                pw.write(",");
                            }
                        }
                        pw.write(";");
                    }
                    pw.write("\n");
                }
            }

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void viewCustomerStats(Customer customer, Scanner scan) {
        // Read in the products via readProduct function, not sorted
        ArrayList<String> quantityThenStore = readProducts(false);
        displayCusStats(quantityThenStore, false);

        // Formatting lines
        System.out.println("\n-----------------------------------");
        System.out.println("\n***************************************************************************\n");

        // Get history of previous purchases
        quantityThenStore = readPurchaseHistory(customer, false);
        displayCusHist(quantityThenStore);

        // Sorting. Buyers have 2 options, alphabetize by sort name or arrange lowest to highest
        System.out.println("\nWould you like to sort these statistics (yes/no)");
        String choice = scan.nextLine();

        // Formatting lines
        System.out.println("-----------------------------------");
        System.out.println("\n***************************************************************************\n");

        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("1. Alphabetize store names");
            System.out.println("2. Sort by lowest quantity first");
            String selection = scan.nextLine();

            switch(selection) {
                case "1":
                    ArrayList<String> sortedQuantityThenStore = readProducts(true);
                    displayCusStats(sortedQuantityThenStore, true);
                    break;

            }


        } else if (choice.equalsIgnoreCase("no")) {

        } else {
            do {
                System.out.println("Invalid Input. Try again.");
                System.out.println("\nWould you like to sort these statistics (yes/no)");
                choice = scan.nextLine();
            } while (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")));
        }

    }

    public static void displayCusStats(ArrayList<String> quantityThenStore, boolean sorted) {

        // sort them via most to least
        String[] sortQuantityThenStore = new String[quantityThenStore.size()];
        for (int i = 0; i < quantityThenStore.size(); i++) {
            sortQuantityThenStore[i] = quantityThenStore.get(i);
        }

        Arrays.sort(sortQuantityThenStore, Collections.reverseOrder());

        // split it again and store it. temp[0] is quantities, temp[1] is stores
        String[] temp = new String[2];
        String[] listStores = new String[sortQuantityThenStore.length];
        String[] listQuantities = new String[sortQuantityThenStore.length];
        if (sorted) {
            for (int i = 0; i < sortQuantityThenStore.length; i++) {
                temp = sortQuantityThenStore[i].split(",");
                listStores[i] = temp[1];
                listQuantities[i] = temp[0];
            }
        } else {
            for (int i = 0; i < sortQuantityThenStore.length; i++) {
                temp = sortQuantityThenStore[i].split(",");
                listStores[i] = temp[0];
                listQuantities[i] = temp[1];
            }
        }

        // Display a dashboard with store and quantities
        for (int i = 0; i < listStores.length; i++) {
            System.out.println("\n-----------------------------------");
            System.out.printf("Store: %s", listStores[i]);
            System.out.printf("\nNumber of Unique Products: %s", listQuantities[i]);
        }

    }
    public static void displayCusHist(ArrayList<String> quantityThenStore) {

        // sort them via most to least
        String[] sortQuantityThenStore = new String[quantityThenStore.size()];
        for (int i = 0; i < quantityThenStore.size(); i++) {
            sortQuantityThenStore[i] = quantityThenStore.get(i);
        }

        Arrays.sort(sortQuantityThenStore, Collections.reverseOrder());

        // split it again and store it. temp[0] is quantities, temp[1] is stores
        String[] temp = new String[2];
        String[] listStores = new String[sortQuantityThenStore.length];
        String[] listQuantities = new String[sortQuantityThenStore.length];
        for (int i = 0; i < sortQuantityThenStore.length; i++) {
            temp = sortQuantityThenStore[i].split(",");
            listStores[i] = temp[1];
            listQuantities[i] = temp[0];
        }

        // Display a dashboard with store and quantities
        for (int i = 0; i < listStores.length; i++) {
            System.out.println("\n-----------------------------------");
            System.out.printf("Store: %s", listStores[i]);
            System.out.printf("\nNumber of Unique Products Purchased: %s", listQuantities[i]);
        }

    }
    public static void viewSellerStats() {
        // Needs to be done, just follow bullet point guidlines and whatever means of sorting is fine, alphabetizing is ez.
    }

    public static ArrayList<String> readProducts(boolean sorted) {
        ArrayList<String> storesQuantities = new ArrayList<>();
        ArrayList<String> quantities = new ArrayList<>();

        // Change products to num products per store
        String store1 = "";
        String store2 = "";
        int quanityCounter = 1;
        for (int i = 1; i < storeNames.size(); i++) {
            store1 = storeNames.get(i - 1);
            store2 = storeNames.get(i);

            if (store1.equalsIgnoreCase(store2)) {
                quanityCounter++;
            } else {
                quantities.add(String.valueOf(quanityCounter));
                quanityCounter = 1;
            }

            if (i == storeNames.size() - 1) {
                quantities.add(String.valueOf(quanityCounter));
            }
        }

        // Cut repeats from stores
        String temp1 = "";
        String temp2 = "";
        for (int i = 0; i < storeNames.size() - 1; i++) {
            temp1 = storeNames.get(i);
            temp2 = storeNames.get(i + 1);

            if (temp1.equalsIgnoreCase(temp2)) {
                storeNames.remove(temp2);
                i--;
            }
        }

        // Check to see if the request need to be alphabetized (sorted) or not. If no then return quantity, store else flip it.
        String[] holding = new String[storeNames.size()];
        if (!sorted) {
            for (int i = 0; i < storeNames.size(); i++) {
                holding[i] = quantities.get(i) + "," + storeNames.get(i);
                storesQuantities.add(holding[i]);
            }
        } else {
            for (int i = 0; i < storeNames.size(); i++) {
                holding[i] = storeNames.get(i) + "," + quantities.get(i);
                storesQuantities.add(holding[i]);
            }
        }

        return storesQuantities;
    }

    public static ArrayList<String> readPurchaseHistory(Customer customer, boolean sorted) {
        ArrayList<String> storesQuantities = new ArrayList<>();
        ArrayList<String> stores = new ArrayList<>();
        ArrayList<String> quantities = new ArrayList<>();

        String[] splitHistory = customer.getWorkingPurchaseHistory().split(",");

        for (int i = 0; i < splitHistory.length; i+= 5) {
            stores.add(splitHistory[i]);
        }
        for (int i = 1; i < splitHistory.length; i+= 5) {
            quantities.add(splitHistory[i]);
        }

        // Change products to num products per store
        String store1 = "";
        String store2 = "";
        int quanityCounter = 1;
        ArrayList<String> quantities1 = new ArrayList<>();
        for (int i = 1; i < stores.size(); i++) {
            store1 = stores.get(i - 1);
            store2 = stores.get(i);

            if (store1.equalsIgnoreCase(store2)) {
                quanityCounter++;
            } else {
                quantities1.add(String.valueOf(quanityCounter));
                quanityCounter = 1;
            }

            if (i == stores.size() - 1) {
                quantities1.add(String.valueOf(quanityCounter));
            }
        }

        // Cut repeats from stores
        String temp1 = "";
        String temp2 = "";
        for (int i = 0; i < stores.size() - 1; i++) {
            temp1 = stores.get(i);
            temp2 = stores.get(i + 1);

            if (temp1.equalsIgnoreCase(temp2)) {
                stores.remove(temp2);
                i--;
            }
        }

        // Check to see if the request need to be alphabetized (sorted) or not. If no then return quantity, store else flip it.
        String[] holding = new String[stores.size()];
        if (!sorted) {
            for (int i = 0; i < stores.size(); i++) {
                holding[i] = quantities1.get(i) + "," + stores.get(i);
                storesQuantities.add(holding[i]);
            }
        } else {
            for (int i = 0; i < stores.size(); i++) {
                holding[i] = stores.get(i) + "," + quantities1.get(i);
                storesQuantities.add(holding[i]);
            }
        }

        return storesQuantities;
    }
}
