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

        Seller[] sellers = setSellers();
        Customer[] customers = setCustomers();

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
                goodbye();
                return;
            } else {
                System.out.println("Not a valid entry!");
            }
        }
    }

    public static Seller[] setSellers() {
        String[] sellerData = readFile("Sellers.txt");
        Seller[] sellers = new Seller[sellerData.length];
        for (int i = 0; i < sellerData.length; i++) {
            String[] dataLine = sellerData[i].split(";");
            Seller seller = new Seller(dataLine[0], dataLine[1]);
            sellers[i] = seller;
            Store[] stores = new Store[dataLine.length - 2];
            for (int j = 0; j < dataLine.length - 2; j++) {
                String[] storeData = dataLine[j].split(",");
                stores[j] = new Store(storeData[0]);
                for (int k = 0; k < storeData.length - 1; k = k + 4) {
                    Product product = new Product(storeData[0], storeData[k],
                            storeData[k + 1], Integer.parseInt(storeData[k + 3]),
                            Double.parseDouble(storeData[k + 2]));
                    stores[j].addProduct(product);
                }
            }
        }
        return sellers;
    }

    public static Customer[] setCustomers() {
        String[] customerData = readFile("Customers.txt");
        Customer[] customers = new Customer[customerData.length];
        for (int i = 0; i < customerData.length; i++) {
            String[] dataLine = customerData[i].split(";");
            Customer customer = new Customer(dataLine[0], dataLine[1]);
            customers[i] = customer;
            //customer file: username;pass;shoppingcart;purchasehistory
            //shoppingcart and purchase history are not organized by shop

            if (dataLine.length > 2) {
                String[] shoppingCart = dataLine[2].split(",");

                for (int j = 0; j < shoppingCart.length; j = j + 4) {
                    customer.addToShoppingCart(new Product(shoppingCart[j],
                            shoppingCart[j + 1], shoppingCart[j + 2],
                            Integer.parseInt(shoppingCart[j + 3]),
                            Double.parseDouble(shoppingCart[j + 4])));
                }
            }

            if (dataLine.length > 3) {
                String[] purchaseHistory = dataLine[3].split(",");

                for (int j = 0; j < purchaseHistory.length; j = j + 4) {
                    customer.addToShoppingCart(new Product(purchaseHistory[j],
                            purchaseHistory[j + 1], purchaseHistory[j + 2],
                            Integer.parseInt(purchaseHistory[j + 3]),
                            Double.parseDouble(purchaseHistory[j + 4])));
                }
            }


        }
        return customers;
    }


    public static void create(Scanner scan, Seller[] sellers, Customer[] customers) {
        System.out.println("Account creation: Are you a seller (s) or a customer (c)?\nQuit: (q)");
        String slec = scan.nextLine();

        if (slec.equals("s")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();
            if (!userName.equals("q")) {
                for (int i = 0; i < sellers.length; i++) {
                    if (sellers[i].getUsername().equals(userName)) {
                        System.out.println("This username is already taken!");
                        redo(scan, sellers, customers);
                    }
                }

                System.out.println("Enter your password!\nQuit: (q)");
                String password = scan.nextLine();

                Seller seller = new Seller(userName, password);
                System.out.println("Welcome " + seller.getUsername() + "!");
                sellerAccount(scan, seller);
            } else {
                goodbye();
            }
        } else if (slec.equals("c")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();
            if (!userName.equals("q")) {
                for (int i = 0; i < sellers.length; i++) {
                    if (customers[i].getUsername().equals(userName)) {
                        System.out.println("This username is already taken!");
                        redo(scan, sellers, customers);
                    }
                }

                System.out.println("Enter your password!\nQuit: (q)");
                String password = scan.nextLine();

                Customer customer = new Customer(userName, password);
                System.out.println("Welcome " + customer.getUsername() + "!");
                //sellerAccount(scan, customer);
            } else {
                goodbye();
            }

        } else if (slec.equals("q")) {
            goodbye();
            return;
        } else {
            System.out.println("Not a valid entry!");
            create(scan, sellers, customers);
        }
    }

    public static void display(Scanner scan, Seller[] sellers) {
        for (int i = 0; i < sellers.length; i++) {
            for (int j = 0; j < sellers[i].getStores().size(); j++) {
                System.out.println(sellers[i].getStores().get(j).getName());
                for (int k = 0; k < sellers[i].getStores().get(i).getProducts().size(); k++) {
                    System.out.println(sellers[i].getStores().get(i).getProducts().get(k).getName());
                    System.out.println(sellers[i].getStores().get(i).getProducts().get(k).getDescription());
                    System.out.println(sellers[i].getStores().get(i).getProducts().get(k).getQuantityAvailable());
                    System.out.println(sellers[i].getStores().get(i).getProducts().get(k).getPrice());
                }
            }
        }

        System.out.println("Search by name, store, or description of products: ");
        String product = scan.nextLine();

        boolean temp = false;

        for (int i = 0; i < sellers.length; i++) {
            for (int j = 0; j < sellers[i].getStores().size(); j++) {
                for (int k = 0; k < sellers[i].getStores().get(i).getProducts().size(); k++) {
                    if (sellers[i].getStores().get(i).getProducts().get(k).getName().toLowerCase().contains(product.toLowerCase()) ||
                        sellers[i].getStores().get(i).getProducts().get(k).getDescription().toLowerCase().contains(product.toLowerCase()) ||
                        sellers[i].getStores().get(i).getProducts().get(k).getNameOfStore().toLowerCase().contains(product.toLowerCase())) {

                        System.out.println(sellers[i].getStores().get(i).getProducts().get(k).getName());
                        System.out.println(sellers[i].getStores().get(i).getProducts().get(k).getDescription());
                        System.out.println(sellers[i].getStores().get(i).getProducts().get(k).getQuantityAvailable());
                        System.out.println(sellers[i].getStores().get(i).getProducts().get(k).getPrice());

                    } else {
                        temp = false;
                    }
                }
            }
        }

        if (temp == false) {
            System.out.println("No product matches this entry, would you like to select another (y/n)?");
            String yesOrNo = scan.nextLine();
            if (yesOrNo.equals("y")) {
                display(scan, sellers);
            } else {

            }
        }

        System.out.println("Enter the name of the product you would like to select, or (b) to go back: ");
        String entry = scan.nextLine();

        for (int i = 0; i < sellers.length; i++) {
            for (int j = 0; j < sellers[i].getStores().size(); j++) {
                for (int k = 0; k < sellers[i].getStores().get(i).getProducts().size(); k++) {
                    if (sellers[i].getStores().get(i).getProducts().get(k).getName().equals(entry)) {
                        System.out.println("You have selected: " + sellers[i].getStores().get(i).getProducts().get(k).getName());
                        temp = true;
                    } else {
                        temp = false;
                    }
                }
            }
        }

        if (temp == false) {
            System.out.println("No product matches this entry, would you like to select another (y/n)?");
            String yesOrNo = scan.nextLine();
            if (yesOrNo.equals("y")) {
                display(scan, sellers);
            } else {

            }
        }

    }

    public static void logIn(Scanner scan, Seller[] sellers, Customer[] customers) {

        System.out.println("Login: Are you a seller (s) or a customer (c)?\nQuit: (q)");
        String slec = scan.nextLine();

        if (slec.equals("s")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();

            if (!userName.equals("q")) {
                boolean found = false;
                for (int i = 0; i < sellers.length; i++) {
                    if (sellers[i].getUsername().equals(userName)) {
                        System.out.println("Enter your password!\nQuit: (q)");
                        String password = scan.nextLine();

                        if (!password.equals("q")) {
                            if (sellers[i].getPassword().equals(password)) {
                                System.out.println("Welcome " + userName + "!");
                                Seller seller = new Seller(userName, password);
                                sellerAccount(scan, seller);

                            } else {
                                System.out.println("Your password is incorrect!");
                                redo(scan, sellers, customers);
                            }
                        }
                        found = true;
                    } else {
                        found = false;
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
                for (int i = 0; i < customers.length; i++) {
                    if (customers[i].getUsername().equals(userName)) {
                        System.out.println("Enter your password!\nQuit: (q)");
                        String password = scan.nextLine();

                        if (!password.equals("q")) {
                            if (customers[i].getPassword().equals(password)) {
                                System.out.println("Welcome " + userName + "!");
                                Customer customer = new Customer(userName, password);
                                //sellerAccount(scan, seller);

                            } else {
                                System.out.println("Your password is incorrect!");
                                redo(scan, sellers, customers);
                            }
                        }
                        found = true;
                    } else {
                        found = false;
                    }
                }
                if (!found) {
                    System.out.println("You are not in the system!");
                    redo(scan, sellers, customers);
                }
            }
        } else if (slec.equals("q")) {
            goodbye();
        } else {
            System.out.println("Not a valid entry!");
            logIn(scan, sellers, customers);
        }
    }

    public static String[] findAccount(String fileName, String input) {
        String[] listy = readFile(fileName);
        String[] userInfo = null;

        for (int i = 0; i < listy.length; i++) {
            if (listy[i] != null && listy[i].toLowerCase().contains(input)) {
                userInfo = listy[i].split(";");
                if (userInfo[0].equals(input)) {
                    return userInfo;
                }
            }
        }
        return userInfo = null;
    }

    public static String[] readFile(String filePath) {
        ArrayList<String> listy = new ArrayList<String>();

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

    //mongoose;mongoosed;Potted Plant,M1shop,description,4,10 dollars,Growth Dirt,M1shop,description,9,30 dollars;Painting,M2shop,description,7,10 dollar

    public static void writeNewUser(String fileName, String userName, String password) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, true);
            PrintWriter pw = new PrintWriter(fos);

            pw.println(userName + ";" + password);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void goodbye() {
        System.out.println("Goodbye!");
        return;
    }

    public static void redo(Scanner scan, Seller[] sellers, Customer[] customers) {
        System.out.println("Would you like to log in again (log in) or create a new account (new)?\nQuit: (q)");
        String redo = scan.nextLine();
        if (redo.equals("log in")) {
            logIn(scan, sellers, customers);
        } else if (redo.equals("new")) {
            create(scan, sellers, customers);
        } else if (redo.equals("q")) {
            goodbye();
            return;
        } else {
            System.out.println("Not a valid entry!");
            redo(scan, sellers, customers);
        }
    }

    public static void customerAccount(Scanner scan, Seller[] sellers, Customer customer) {
        //dashboard
        display(scan, sellers);

        //cart
        //purchase history
    }

    public static void sellerAccount(Scanner scan, Seller seller) {
        System.out.println("Would you like to view statistics dashboard (1), import products (2), export products (3), edit products (4)?");
        String sOptions = scan.nextLine();
        if (sOptions.equals("1")) {
            //TODO: include amount of products in customer cart
            //TODO: allow for sorting by: name, amount in cart, amount in store, etc

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



        } else if (sOptions.equals("3")) {
            //TODO: print the stores, ask which store to look at
            //TODO: once store is selected, print the products

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
        } else if (sOptions.equals("4")) {
            //print dashboard
            System.out.println("Which store is this product located in?");
            String storeSlec = scan.nextLine();
            for (int i = 0; i < seller.getStores().size(); i++) {
                if (seller.getStores().get(i).getName().equals(storeSlec)) {
                    System.out.println("What is the product's name?");
                    String prodSlec = scan.nextLine();
                    for (int j = 0; j < seller.getStores().get(i).getProducts().size(); j++) {
                        if (seller.getStores().get(i).getProducts().get(j).equals(prodSlec)) {
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
                        }
                    }
                }
            }
        }
    }

    public static void dashboard() {

    }
}
