import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import src.PJ4.*;
/** Dilara Baysal
 * 4/6/2023
 *
 */

public class Menu {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Marketplace!");

        String acc = "";

        while (!acc.equals("new") && !acc.equals("log in")) {
            System.out.println("Would you like to create a new account (new), log in (log in), or quit (q)?");
            acc = scan.nextLine();
            if (acc.equals("new")) {
                create(scan);
            } else if (acc.equals("log in")) {
                logIn(scan);
            } else if (acc.equals("q")) {
                goodbye();
                return;
            } else {
                System.out.println("Not a valid entry!");
            }
        }
    }

    public static void create(Scanner scan) {
        System.out.println("Account creation: Are you a seller (s) or a customer (c)?\nQuit: (q)");
        String slec = scan.nextLine();

        if (slec.equals("s")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();
            if (!userName.equals("q")) {
                String[] data = findAccount("Sellers.txt", userName);
                if (data == null) {
                    System.out.println("Enter your password!\nQuit: (q)");
                    String password = scan.nextLine();

                    Seller seller = new Seller(userName, password);
                    System.out.println("Welcome " + seller.getUsername() + "!");
                    writeNewUser("Sellers.txt", userName, password);

                } else {
                    System.out.println("That account already exists! Would you like to try a different username?");
                    //TODO: create redo capability
                }
            }

        } else if (slec.equals("c")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();
            if (!userName.equals("q")) {
                String[] data = findAccount("CustomersAndCart.txt", userName);
                if (data != null) {
                    System.out.println("Enter your password!\nQuit: (q)");
                    String password = scan.nextLine();

                    if (!password.equals("q")) {

                        Customer customer = new Customer(userName, password);
                        //not sure if I implemented this right
                        System.out.println("Welcome " + userName + "!");
                    }
                } else {
                    System.out.println("That account already exists! Would you like to try a different username?");
                    //TODO: create redo capability
                }

            } else {
                goodbye();
                return;
            }

        } else if (slec.equals("q")) {
            goodbye();
            return;
        } else {
            System.out.println("Not a valid entry!");
            create(scan);
        }
    }

    public static void logIn(Scanner scan) {

        System.out.println("Login: Are you a seller (s) or a customer (c)?\nQuit: (q)");
        String slec = scan.nextLine();

        if (slec.equals("s")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();

            if (!userName.equals("q")) {
                String[] data = findAccount("Sellers.txt", userName);

                if (data != null) {

                    System.out.println("Enter your password!\nQuit: (q)");
                    String password = scan.nextLine();

                    if (!password.equals("q")) {
                        if (data[1].equals(password)) {
                            System.out.println("Welcome " + userName + "!");
                            Seller seller = new Seller(userName, password);
                            setInfo("Sellers.txt", userName, seller);

                        } else {
                            System.out.println("Your password is incorrect!");
                            redo(scan);
                        }
                    }

                } else {
                    System.out.println("You are not in the system!");
                    redo(scan);
                }
            }

        } else if (slec.equals("c")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();

            if (!userName.equals("q")) {
                String[] data = findAccount("CustomersAndCart.txt", userName);
                if (data != null) {

                    System.out.println("Enter your password!\nQuit: (q)");
                    String password = scan.nextLine();

                    if (!password.equals("q")) {
                        if (data[1].equals(password)) {
                            System.out.println("Welcome " + userName + "!");
                            Customer customer = new Customer(userName, password);

                        } else {
                            System.out.println("Your password is incorrect!");
                            redo(scan);
                        }
                    }

                } else {
                    System.out.println("You are not in the system!");
                    redo(scan);
                }
            }
        } else if (slec.equals("q")) {
            goodbye();
            return;
        } else {
            System.out.println("Not a valid entry!");
            logIn(scan);
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

    //mongoose;mongoosed;Potted Plant,M1shop,description,4,10 dollars,Growth Dirt,M1shop,description,9,30 dollars;Painting,M2shop,description,7,10 dollars


    public static void setInfo(String fileName, String userName, Seller seller) {
        String[] data = findAccount(fileName, userName);
        for (int i = 1; i < data.length; i++) {
            String[] info = data[i].split(",");
            for (int j = 0; i < info.length; j = j + 4) {
                Product product = new Product(info[j], info[j + 1], info[j + 2], Double.parseDouble(info[j + 3]), Integer.parseInt(info[j + 4]));
                for (int k = 0; k < seller.getStores().size(); i++) {
                    if (seller.getStores().get(i).getName().equals(info[j + 1])) {
                        seller.getStores().get(i).addProduct(product);
                    } else {
                        Store store = new Store(info[j + 1]);
                        store.addProduct(product);
                        seller.addStores(store);
                    }
                }
            }
        }
    }


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
    }

    public static void redo(Scanner scan) {
        System.out.println("Would you like to try to log in again (log in) or create a new account (new)?\nQuit: (q)");
        String redo = scan.nextLine();
        if (redo.equals("log in")) {
            logIn(scan);
        } else if (redo.equals("new")) {
            create(scan);
        } else if (redo.equals("q")) {
            goodbye();
            return;
        } else {
            System.out.println("Not a valid entry!");
            redo(scan);
        }
    }

    public static void customerActions() {
        //dashboard


        //cart
        //purchase history
    }

    public static void sellerAccount(Scanner scan, Seller seller, String[] data) {
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
                Product product = new Product(prodData[0], prodData[1], prodData[2], Double.parseDouble(prodData[3]), Integer.parseInt(prodData[4]));
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
                            seller.getStores().get(i).modifyProduct(seller.getStores().get(i).getProducts().get(j).getName(),
                                    newName,
                                    storeSlec,
                                    newDesc,
                                    newPrice,
                                    newQuant);
                        }
                    }
                }
            }
        }
    }

    public static void dashboard() {

    }
}
