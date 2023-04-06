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
                String[] dataS = findAccount("Sellers.txt", 0, userName);
                if (dataS == null) {
                    System.out.println("Enter your password!\nQuit: (q)");
                    String password = scan.nextLine();

                    Seller seller = new Seller(userName, password);
                    //TODO: not sure if I implemented this right

                    System.out.println("Welcome " + userName + "!");

                    addNewUser("Sellers.txt", userName, password);
                    dataS = findAccount("Sellers.txt", 0, userName);
                    sellerAccount(scan, seller, dataS);

                } else {
                    System.out.println("That account already exists! Would you like to try a different username?");
                    //TODO: create redo capability
                }
            }

        } else if (slec.equals("c")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();
            String[] data = findAccount("CustomersAndCart.txt", 0,userName);
            if (!userName.equals("q")) {
                String[] dataC = findAccount("CustomersAndCart.txt", 0, userName);
                if (dataC != null) {
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
                String[] data = findAccount("Sellers.txt", 0, userName);

                if (data != null) {

                    System.out.println("Enter your password!\nQuit: (q)");
                    String password = scan.nextLine();

                    if (!password.equals("q")) {
                        if (data[1].equals(password)) {
                            System.out.println("Welcome " + userName + "!");
                            Seller seller = new Seller(userName, password);
                            sellerAccount(scan, seller, data);
                        } else {
                            System.out.println("Your password is incorrect!");
                            System.out.println("Would you like to try to log in again (log in) or create a new account (new)?");
                            String redo = "";
                            while (!redo.equals("log in") && !redo.equals("new") && !redo.equals("q")) {
                                redo = scan.nextLine();
                                if (redo.equals("log in")) {
                                    logIn(scan);
                                } else if (redo.equals("new")) {
                                    create(scan);
                                } else if (redo.equals("q")) {
                                    goodbye();
                                    return;
                                } else {
                                    System.out.println("Not a valid entry!");
                                    System.out.println("Would you like to try to log in again (log in) or create a new account (new)?");
                                    redo = scan.nextLine();
                                }
                            }
                        }
                    }

                } else {
                    System.out.println("You are not in the system!");
                    System.out.println("Would you like to try to log in again (log in) or create a new account (new)?\nQuit: (q)");
                    String redo = "";
                    while (!redo.equals("log in") && !redo.equals("new") && !redo.equals("q")) {
                        redo = scan.nextLine();
                        if (redo.equals("log in")) {
                            logIn(scan);
                        } else if (redo.equals("new")) {
                            create(scan);
                        } else if (redo.equals("q")) {
                            goodbye();
                            return;
                        } else {
                            System.out.println("Not a valid entry!");
                            System.out.println("Would you like to try to log in again (log in) or create a new account (new)?\nQuit: (q)");
                            redo = scan.nextLine();
                        }
                    }
                }
            }

        } else if (slec.equals("c")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();

            if (!userName.equals("q")) {
                String[] data = findAccount("CustomersAndCart.txt", 0, userName);
                if (data != null) {

                    System.out.println("Enter your password!\nQuit: (q)");
                    String password = scan.nextLine();

                    if (!password.equals("q")) {
                        if (data[1].equals(password)) {
                            System.out.println("Welcome " + userName + "!");
                        } else {
                            System.out.println("Your password is incorrect!");
                            System.out.println("Would you like to try to log in again (log in) or create a new account (new)?");
                            String redo = "";
                            while (!redo.equals("log in") && !redo.equals("new") && !redo.equals("q")) {
                                redo = scan.nextLine();
                                if (redo.equals("log in")) {
                                    logIn(scan);
                                } else if (redo.equals("new")) {
                                    create(scan);
                                } else if (redo.equals("q")) {
                                    goodbye();
                                    return;
                                } else {
                                    System.out.println("Not a valid entry!");
                                    System.out.println("Would you like to try to log in again (log in), create a new account (new), or quit (q)?");
                                    redo = scan.nextLine();
                                }
                            }
                        }
                    }

                } else {
                    System.out.println("You are not in the system!");
                    System.out.println("Would you like to try to log in again (log in) or create a new account (new)?\nQuit: (q)");
                    String redo = "";
                    while (!redo.equals("log in") && !redo.equals("new") && !redo.equals("q")) {
                        redo = scan.nextLine();
                        if (redo.equals("log in")) {
                            logIn(scan);
                        } else if (redo.equals("new")) {
                            create(scan);
                        } else if (redo.equals("q")) {
                            goodbye();
                            return;
                        } else {
                            System.out.println("Not a valid entry!");
                            System.out.println("Would you like to try to log in again (log in), create a new account (new), or quit (q)?");
                            redo = scan.nextLine();
                        }
                    }
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

    public static String[] findAccount(String fileName, int index, String input) {
        ArrayList<String> listy = new ArrayList<String>();

        try (FileReader fr = new FileReader(fileName);
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

        String[] userInfo = null;

        for (int i = 0; i < listy.size(); i++) {
            if (listy.get(i) != null && listy.get(i).toLowerCase().contains(input)) {
                userInfo = listy.get(i).split(",");
                if (userInfo[0].equals(input)) {
                    return userInfo;
                }
            }
        }
        return userInfo = null;
    }

    public static void addNewUser(String fileName, String userName, String password) {
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

    public static void sellerAccount(Scanner scan, Seller seller, String[] data) {
        System.out.println("Would you like to view statistics dashboard (1), import products (2), or export products (3)?");
        String sOptions = scan.nextLine();
        if (sOptions.equals("1")){
            //TODO: include amount of products in customer cart
            //TODO: allow for sorting by: name, amount in cart, amount in store, etc

        } else if (sOptions.equals("2")) {
            //TODO: Import a CSV file. I don't want to do this.

        } else if (sOptions.equals("3")) {
            //TODO: print the stores, ask which store to look at
            //TODO: once store is selected, print the products

            if (data.length > 2) {
                String stores[] = new String[data.length - 2];
                for (int i = 0; i < stores.length; i++) {
                    stores[i] = data[i + 2];
                }// creating a new string[] with just store names

                for (int i = 0; i < stores.length; i++) {
                    System.out.printf((i + 1) + ". " + stores[i] + "\n");
                } //printing the stores
                System.out.println("Enter the name of the store you would like to export, or select all by entering (all).");
                String storeSlec = scan.nextLine();

                if (!storeSlec.equals("all")) {
                    try (FileReader fr = new FileReader("Stores.txt");
                         BufferedReader bfr = new BufferedReader(fr)) {
                        String line = bfr.readLine(); //reading data about the products the stores have
                        while (line != null) {
                            if (line != null) {
                                String[] storeInfo = line.split(","); //reading from the stores
                                //TODO: AS OF RIGHT NOW IT IS CREATING A TXT FILE. FIX THIS
                                if (storeInfo[0].equals(storeSlec)) {
                                    try {
                                        FileOutputStream fos = new FileOutputStream(storeSlec + ".txt", false);
                                        PrintWriter pw = new PrintWriter(fos);
                                        pw.print(storeInfo[0] + ":\n");
                                        for (int i = 1; i < storeInfo.length; i++) {
                                            if (i%2 != 0) { //checking if the entry is odd or not, since it switches between item and quantity
                                                pw.print("   " + storeInfo[i] + "\n");//printing name
                                            } else {
                                                pw.print("      " + storeInfo[i] + "\n");//printing quantity
                                            }
                                        }
                                        pw.close();
                                        //TODO: Do the CSV thing, I don't want to
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            line = bfr.readLine();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (storeSlec.equals("all")) {
                    //implement stores[]
                    try (FileReader fr = new FileReader("Stores.txt");
                         BufferedReader bfr = new BufferedReader(fr)) {
                        String line = bfr.readLine(); //reading data about the products the stores have
                        int j = 0;
                        while (line != null && j < stores.length) {
                            if (line != null) {
                                String[] storeInfo = line.split(","); //reading from the stores
                                //TODO: AS OF RIGHT NOW IT IS CREATING A TXT FILE. FIX THIS
                                if (storeInfo[0].equals(stores[j])) {
                                    try {
                                        FileOutputStream fos = new FileOutputStream("all.txt", true);
                                        PrintWriter pw = new PrintWriter(fos);
                                        pw.print(storeInfo[0] + ": \n");
                                        for (int i = 1; i < storeInfo.length; i++) {
                                            if (i % 2 != 0) { //checking if the entry is odd or not, since it switches between item and quantity
                                                pw.print("   " + storeInfo[i] + "\n");//printing name
                                            } else {
                                                pw.print("      " + storeInfo[i] + "\n");//printing quantity
                                            }
                                        }
                                        pw.close();
                                        //TODO: Do the CSV thing, I don't want to
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            j++;
                            line = bfr.readLine();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Please enter a valid entry.");
                }

                ////
                ArrayList<String> productData = new ArrayList<>();

                try (FileReader fr = new FileReader("Stores.txt");
                     BufferedReader bfr = new BufferedReader(fr)) {
                    String line = bfr.readLine();
                    while (line != null) {
                        line = bfr.readLine();
                        if (line != null) {
                            String[] storeInfo = line.split(",");
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("You don't have any products to export yet! Please select another option.");
                sellerAccount(scan, seller, data);
            }

        }

    }

}
