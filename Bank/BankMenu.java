package Bank;

import java.util.InputMismatchException;
import java.util.Scanner;

import Bank.managers.AccountFileManager;
import Bank.managers.AccountManager;
import Bank.managers.LoggedInUserMenu;
import Bank.managers.LoginManager;
import Bank.model.Account;

public class BankMenu {

    // Manager instances
    private AccountManager accountManager;
    private AccountFileManager fileManager;
    private LoginManager loginManager;
    private LoggedInUserMenu loggedInUserMenu;

    // Constructor - initialize all managers
    public BankMenu() {
        this.accountManager = new AccountManager();
        this.fileManager = new AccountFileManager(accountManager);
        this.loginManager = new LoginManager(accountManager);
        this.loggedInUserMenu = new LoggedInUserMenu(accountManager);
    }

    // Main method - entry point
    public static void main(String[] args) {
        BankMenu bankMenu = new BankMenu();
        bankMenu.start();
    }

    // Start the banking system
    public void start() {
        System.out.println("--- BANKING SYSTEM POLYMORPHIC ARRAYLIST --- \n");

        Scanner sc = new Scanner(System.in);

        while(true) {
            try {
                displayMainMenu();
                int choice = sc.nextInt();
                sc.nextLine();

                if(choice == 9){
                    System.out.println("Exiting System.... Goodbye!");
                    break;
                }

                handleMainMenuChoice(choice, sc);

            } catch(InputMismatchException e){
                System.out.println("ERROR: Invalid input type! Please enter numbers only.");
                sc.nextLine();
            }
        }
        sc.close();        
    }

    // Display main menu options
    private void displayMainMenu() {
        System.out.println("\n---MAIN MENU---");
        System.out.println("1. Create New Account");
        System.out.println("2. Display All Accounts");
        System.out.println("3. Save Accounts to File");
        System.out.println("4. Load Accounts from File");
        System.out.println("5. Delete Account");
        System.out.println("6. Edit Account Details");
        System.out.println("7. Search Account by Name");
        System.out.println("8. Login");
        System.out.println("9. Exit System");
        System.out.println("Enter Your Choice (1-9): ");
    }

    // Handle main menu choices and delegate to appropriate manager
    private void handleMainMenuChoice(int choice, Scanner sc) {
        switch (choice) {

            case 1:
                accountManager.createAccount(sc);
                break;

            case 2:
                accountManager.displayAllAccounts();
                break;

            case 3:
                fileManager.saveAccountsToFile();
                break;

            case 4:
                fileManager.loadAccountsFromFile();
                break;

            case 5:
                accountManager.deleteAccount(sc);
                break;

            case 6:
                accountManager.editAccountDetails(sc);
                break;

            case 7:
                accountManager.searchAccountByName(sc);
                break;

            case 8:
                Account loggedInAccount = loginManager.loginAccount(sc);
                if (loggedInAccount != null) {
                    loggedInUserMenu.accountMenu(sc, loggedInAccount);
                }
                break;

            default:
                System.out.println("Invalid Option!! Please choose a option between 1 to 9.");
        }
    }
}