import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/** Dilara Baysal
 * 4/6/2023
 *
 */

public class Menu {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

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
                            System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getName());
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
                    System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getDescription());
                    System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getQuantityAvailable() + " units");
                    System.out.printf("$%.2f", sellers.get(i).getStores().get(j).getProducts().get(k).getPrice());
                    list.add(sellers.get(i).getStores().get(j).getProducts().get(k));
                    System.out.println("\n------------------------");

                }
            }
        }
        return list;
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
            System.out.println(sortedProducts.get(i).getDescription());
            System.out.println(sortedProducts.get(i).getQuantityAvailable() + " units");
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
            System.out.println(sortedProducts.get(i).getDescription());
            System.out.println(sortedProducts.get(i).getQuantityAvailable() + " units");
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
                        System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getDescription());
                        System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getQuantityAvailable());
                        System.out.println(sellers.get(i).getStores().get(j).getProducts().get(k).getPrice());
                        temp = true;
                        System.out.println("------------------------------------");
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
        //dashboard
        System.out.println("Would you like to view products (view), view your shopping cart (cart), export your purchase history (hist), view your statistics (stats), or quit (q)?");
        String selection = scan.nextLine();

        if (selection.equals("view")) {
            Product productSelection = displayFunctions(scan, sellers, customers);
            System.out.println("Would you like to purchase " + productSelection.getName() + ", or add to cart?");
            System.out.println("1. Purchase\n2. Add");
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
            viewCustomerStats(customer);
        } else {
            do {
                System.out.println("Invalid Selection. Please try again.");
                System.out.println("Would you like to view products (view), view your shopping cart (cart), export your purchase history (hist), view your statistics (stats) or quit (q)?");
                selection = scan.nextLine();
            } while (!(selection.equalsIgnoreCase("view") || selection.equalsIgnoreCase("cart") || selection.equalsIgnoreCase("hist") || selection.equalsIgnoreCase("stats") || selection.equalsIgnoreCase("q")));
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
        } else if (sOptions.equalsIgnoreCase("5")) {
            viewSellerStats();
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
                        pw.write(sellers.get(i).getStores().get(j).getName());
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
    public static void viewCustomerStats(Customer customer) {
        // Read in the products via readProduct function
        ArrayList<String> productList = readProducts();



        // Make the dashboard
        // List stores by num of products sold
        //
        // Need history of previous purchases
        //
        // Sort by diff things
    }

    public static void viewSellerStats() {

    }

    public static ArrayList<String> readProducts() {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Sellers.txt"));


            ArrayList<String> storesQuantities = new ArrayList<>();
            ArrayList<String> lines = new ArrayList<>();
            String fileLine = bfr.readLine();

            while (fileLine != null) {
                lines.add(fileLine);
                fileLine = bfr.readLine();
            }

            String[] splitLine = new String[3];
            String[] hold = new String[lines.size()];
            for (int i = 0; i < lines.size(); i++) {
                splitLine = lines.get(i).split(";");
                hold[i] = splitLine[2];
            }


            for (int i = 0; i < hold.length; i++) {


            }

            return storesQuantities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
