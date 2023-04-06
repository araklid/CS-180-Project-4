import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** Dilara Baysal
 * 4/6/2023
 * Main menu for this project. Individual classes need to be added, as of right now basic implementation has been added.
 */

public class Menu {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Marketplace!");

        String acc = "";

        while (!acc.equals("new") && !acc.equals("log in")) {
            System.out.println("Would you like to create a new account (new) or log in (log in)?\nQuit: (q)");
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

            System.out.println("Enter your password!\nQuit: (q)");
            String password = scan.nextLine();

            Seller seller = new Seller(userName, password);
            //not sure if I implemented this right

            System.out.println("Welcome " + userName + "!");

        } else if (slec.equals("c")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();
            if (!userName.equals("q")) {

                System.out.println("Enter your password!\nQuit: (q)");
                String password = scan.nextLine();

                if (!password.equals("q")) {

                    Customer customer = new Customer(userName, password);
                    //not sure if I implemented this right
                    System.out.println("Welcome " + userName + "!");
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
                        } else {
                            System.out.println("You are not in the system!");
                            System.out.println("Would you like to try again (try) or create a new account (new)?");
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
                    //create redo capability
                }
            }

        } else if (slec.equals("c")) {
            System.out.println("Enter your username!\nQuit: (q)");
            String userName = scan.nextLine();

            if (!userName.equals("q")) {
                String[] data = findAccount("Customers.txt", 0, userName);
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
                            System.out.println("Would you like to try to log in again (log in) or create a new account (new)?");
                            redo = scan.nextLine();
                        }
                    }
                    //create redo capability
                }
            }
        } else if (slec.equals("q")) {
            goodbye();
            return;
        } else {
            System.out.println("Not a valid entry!");
            logIn(scan);
        }
        //user goes back to beginning, enter implementation for this
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
                userInfo = listy.get(i).split(";");
            }
        }
        return userInfo;
    }

    public static void goodbye() {
        System.out.println("Goodbye!");
    }

}
