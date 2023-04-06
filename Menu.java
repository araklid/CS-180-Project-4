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
                String[] dataS = findAccount("Sellers.txt", userName);
                if (dataS == null) {
                    System.out.println("Enter your password!\nQuit: (q)");
                    String password = scan.nextLine();

                    Seller seller = new Seller(userName, password);
                    //TODO: not sure if I implemented this right

                    System.out.println("Welcome " + userName + "!");

                    addNewUser("Sellers.txt", userName, password);
                    dataS = findAccount("Sellers.txt", userName);

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
                            seller.sellerAccount(scan, data);
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
                String[] data = findAccount("CustomersAndCart.txt", 0, userName);
                if (data != null) {

                    System.out.println("Enter your password!\nQuit: (q)");
                    String password = scan.nextLine();

                    if (!password.equals("q")) {
                        if (data[1].equals(password)) {
                            System.out.println("Welcome " + userName + "!");
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

    public static String[] readFile(String fileName) {
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
        return (listy.toArray(new String[listy.size()]));
    }

    //mongoose;mongoosed;Potted Plant,M1shop,description,4,10,Growth Dirt,M1shop,description,9,30;Painting,M2shop,description,7,10


    public static void setInfo(String fileName, String userName, Seller seller) {
        String[] data = findAccount(fileName,userName);
        for (int i = 1; i < data.length; i++) {
            String[] info = data[i].split(",");
            for (int j = 0; i < info.length; j = j + 4) {
                Product product = new Product(info[j], info[j + 1], info[j + 2], Double.parseDouble(info[j + 3]), Integer.parseInt(info[j + 4]), seller);
                for (int k = 0; k < seller.getStores().size(); i++) {
                    if (seller.getStores().get(i).equals(info[j + 1])) {
                        seller.getStores().get(i).addProduct(product);
                    } else {
                        Store store = new Store(info[j + 1]);
                    }
                }
            }
        }
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

}
