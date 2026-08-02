package Bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankMenu {

    //DYNAMIC ARRAYLIST IMPLEMENTATION
    public static void main(String[] args) {
        System.out.println("--- BANKING SYSTEM POLYMORPHIC ARRAYLIST --- \n");

        ArrayList<Account> accounts = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        while(true) {
            try {
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

                int choice = sc.nextInt();
                sc.nextLine();

                if(choice == 9){
                    System.out.println("Exiting System.... Goodbye!");
                    break;
                }

                switch (choice) {

                    case 1:
                        createAccount(sc, accounts);
                        break;

                    case 2:
                        displayAllAccounts(accounts);
                        break;

                    case 3:
                        saveAccountsToFile(accounts);
                        break;

                    case 4:
                        loadAccountsFromFile(accounts);
                        break;

                    case 5:
                        deleteAccount(sc, accounts);
                        break;

                    case 6:
                        editAccountDetails(sc, accounts);
                        break;

                    case 7:
                        searchAccountByName(sc, accounts);
                        break;

                    case 8:
                        Account loggedInAccount = loginAccount(sc, accounts);

                        if (loggedInAccount != null) {
                            accountMenu(sc, loggedInAccount, accounts);
                        }
                        break;

                    default:
                        System.out.print("Invalid Option!! Please choose a option between 1 to 9.");

                }
            }

            catch(InputMismatchException e){
                System.out.println("ERROR: Invalid input type! Please enter numbers only.");
                sc.nextLine();
            }
        }
        sc.close();        
    }

    //Method for case 2 "Display all accounts"
    private static void displayAllAccounts(ArrayList<Account> accounts) {
        if(accounts.isEmpty()){
            System.out.println("There is nothing in our inventory! No active accounts.");
        }else {
            System.out.println("\n---PRINTING ALL THE ACCOUNTS---");
                for (Account acc : accounts) {
                    acc.displayAccountDetails();
                }
        }
    }

    //Method for case 1 "Create account"
    private static void createAccount(Scanner sc, ArrayList<Account> accounts) {

        System.out.println("Enter Account Holder's Name: ");
        String name = sc.nextLine();

        int accNum = Account.nextAccountNumber;
        Account.nextAccountNumber++;
        System.out.println("System generated Account Number: " + accNum);

        Account existingAccount = findAccountByNumber(accounts, accNum);
        if (existingAccount != null) {
            System.out.println("Error: Account Already exists! Try new Account number");
            return;
        } 

        System.out.println("Set PIN for your account: ");
        int accountPIN = sc.nextInt();

        System.out.println("Enter Initial Deposit Balance: ");
        Double initBalance = sc.nextDouble();
        sc.nextLine();

        accounts.add(new Account(name, accNum, initBalance, accountPIN));
        System.out.println("Account opened and initial amount added successfully!");

        System.out.println("\n===========================================");
        System.out.println("OPERATION SUCCESSFUL!!");
        System.out.println("Account Number : " + accNum);
        System.out.println("Holder : " + name);
        System.out.println("Balance : $" + initBalance);
        System.out.println("\n===========================================");
    }

    //Helper method to validate and find the account by number
    private static Account findAccountByNumber(ArrayList<Account> accounts, int accountNumber){
        for(Account acc : accounts){
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }
        return null;
    }

    //METHOD FOR CASE 3 "SAVE ACCOUNTS TO FILE"
    private static void saveAccountsToFile(ArrayList<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt"))) {
            for (Account acc : accounts) {
                writer.write(acc.getAccountNumber() + "," + acc.getAccountHolderName() + "," + acc.getBalance());
                writer.newLine();
            }
            System.out.println("Accounts saved to accounts.txt successfully!!");
        } catch (IOException e) {
            System.out.println("Error in saving account: " + e.getMessage());
        } 
    }

    //METHOD FOR CASE 4 "LOAD ACCOUNTS FROM FILE"
    private static void loadAccountsFromFile(ArrayList<Account> accounts) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {

            accounts.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int accountNumber = Integer.parseInt(parts[0].trim());
                String accountHolderName = parts[1].trim();
                double balance = Double.parseDouble(parts[2].trim());
                
                Account account = new Account(accountHolderName, accountNumber, balance, 0);
                accounts.add(account);
            }
            System.out.println("Accounts loaded from accounts.txt successfully!!");
        } catch (IOException e) {
            System.out.println("Error in loading accounts: " + e.getMessage());
        }
    }

    //METHOD FOR CASE 5 "DELETE ACCOUNT"
    private static void deleteAccount(Scanner sc, ArrayList<Account> accounts) {
        System.out.println("Enter Account number you want to delete: ");
        int deleteAccNum = sc.nextInt();
        sc.nextLine();

        Account account = findAccountByNumber(accounts, deleteAccNum);

        if(account == null) {
            System.out.println("Error: Account: " + deleteAccNum + " not found!!");
        } else {
            System.out.println("Are you sure? Type 'Y' to confirm or 'N' to cancel: ");
            char confirmationYN = sc.next().charAt(0);
            
            if(confirmationYN == 'Y' || confirmationYN == 'y'){
                accounts.remove(account);
                System.out.println("\n========================================");
                System.out.println("DELETION SUCCESSFUL!");
                System.out.println("Account Deleted : " + deleteAccNum + " has been deleted.");
                System.out.println("\n========================================");
            }
            else {
                System.out.println("Deletion cancelled, back to menu...");
            }
        }
    }

    //Method for case 6 "EDIT ACCOUNT DETAILS"
    private static void editAccountDetails(Scanner sc, ArrayList<Account> accounts) {
        System.out.println("Enter the Account Number: ");
        int editingAccNumber = sc.nextInt();
        sc.nextLine();

        Account account = findAccountByNumber(accounts, editingAccNumber);

        if(account == null) {
            System.out.println("Error: Account not found. Please check if the account number is correct?");    
            return;        
        } 

        System.out.println("What you want to edit? \n 1. Name \n 2. Cancel");
        int editChoice = sc.nextInt();
        sc.nextLine();

        if(editChoice == 1) {
            System.out.println("Enter new name: ");
            String newAccHolderName = sc.nextLine();
            account.setAccountHolderNewName(newAccHolderName);
            System.out.println("Account Holder's name changed successfully!!");
        } else if(editChoice == 2) {
            System.out.println("Cancelling Account's details editing...");
        } else {
            System.out.println("Error: Invalid choice!!");
        }
    }  

    //Method for case 7 "SEARCH ACCOUNT BY NAME"
    private static void searchAccountByName(Scanner sc, ArrayList<Account> accounts) {
        System.out.println("Enter the name of the account Holder: ");
        String searchName = sc.nextLine();

        boolean found = false;

        System.out.println("\n--- SEARCH RESULTS ---");
        for (Account acc :  accounts) {
            if(acc.getAccountHolderName().equalsIgnoreCase(searchName)) {
                System.out.println("\n===========================================");
                System.out.println("OPERATION SUCCESSFUL!!");
                System.out.println("Account Found!!");
                acc.displayAccountDetails();
                System.out.println("\n===========================================");
                found = true;
            }   
        }
        if (!found) {
            System.out.println("Error: No account found with this name: " + searchName + ".");
        }
    }
    
    //Method for case 8 "LOGIN"
    private static Account loginAccount(Scanner sc, ArrayList<Account> accounts) {
        System.out.println("Enter your Account Number: ");
        int loginAccountNumber = sc.nextInt();
        sc.nextLine();

        Account account = findAccountByNumber(accounts, loginAccountNumber);

        if(account == null){
            System.out.println("Error: Account does not exist!");
            return null;
        } else {
            System.out.println("Enter PIN: ");
            int accPIN = sc.nextInt();
            sc.nextLine();

            if (accPIN == account.getAccountPIN()) {
                System.out.println("=================================================");
                System.out.println("---LOGIN SUCCESSFUL!!---");
                System.out.println("Welcome, " + account.getAccountHolderName());
                System.out.println("=================================================");

                return account;

            } else {
                System.out.println("Error: Pin is wrong, retry again!");
                return null;
            }
        }
    }

    //METHOD for account menu under login option
    private static void accountMenu(Scanner sc, Account loggedInAccount, ArrayList<Account> accounts) {
        System.out.println("Welcome " + loggedInAccount.getAccountHolderName());
        while(true) {

            Account existingAccount = findAccountByNumber(accounts, loggedInAccount.getAccountNumber());

            if (existingAccount == null) {
                System.out.println("This account no longer exists.");
                return;
            }

            System.out.println("\n---LOGGED IN MENU---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. View Transaction History");
            System.out.println("5. View Monthly Statement");
            System.out.println("6. Change PIN");
            System.out.println("9. Logout");

            System.out.print("Enter your choice: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {

            case 1:
                depositForLoggedInUser(sc, loggedInAccount);
                break;

            case 2:
                withdrawForLoggedInUser(sc, loggedInAccount);
                break;

            case 3:
                transferForLoggedInUser(sc, loggedInAccount, accounts);
                break;

            case 4:
                transactionHistoryForLoggedInUser(loggedInAccount);
                break;

            case 5:
                monthlyStatementForLoggedInUser(loggedInAccount);
                break;

            case 6:
                changePINforLoggedInUser(sc, loggedInAccount);
                break;

            case 9:
                System.out.println("Logged out Successfully!");
                return;

            default:
                System.out.println("Invalid option! Please try again.");
            }
        }        
    }

    private static void depositForLoggedInUser(Scanner sc, Account loggedInAccount) {

        System.out.print("Enter the amount to deposit: $");
        double depositAmount = sc.nextDouble();
        sc.nextLine();

        try{
            loggedInAccount.deposit(depositAmount);

            System.out.println("\n===============================================");
            System.out.println("Deposit Successful!");
            System.out.println("Amount Deposited: $" + depositAmount);
            System.out.println("Current Balance: $" + loggedInAccount.getBalance());
            System.out.println("===============================================");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());         
        }
    }

    private static void withdrawForLoggedInUser(Scanner sc, Account loggedInAccount) {
        
        System.out.println("Enter the amount to withdraw: $");
        double withdrawAmount = sc.nextDouble();
        sc.nextLine();

        try{
            loggedInAccount.withdraw(withdrawAmount);

            System.out.println("\n===============================================");
            System.out.println("Withdrawal Successful!");
            System.out.println("Amount Withdrawn: $" + withdrawAmount);
            System.out.println("Current Balance: $" + loggedInAccount.getBalance());
            System.out.println("===============================================");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Method for transfer for logged in user
    private static void transferForLoggedInUser(Scanner sc, Account loggedInAccount, ArrayList<Account> accounts) {

        System.out.println("Enter Account number to transfer Money into: ");
        int receiverAccountNumber = sc.nextInt();
        sc.nextLine();

        Account receiverAccount = findAccountByNumber(accounts, receiverAccountNumber);

        if(receiverAccountNumber == loggedInAccount.getAccountNumber()) {
            System.out.println("Error: You cannot transfer money to your own account!");
            return;
        }

        if(receiverAccount == null) {
            System.out.println("Error: Receiver doesn't exist! Check another account number.");
            return;
        }

        System.out.println("Enter the Amount to transfer: $");
        double transferAmount = sc.nextDouble();
        sc.nextLine();

        try {
            loggedInAccount.withdraw(transferAmount);
            receiverAccount.deposit(transferAmount);

            loggedInAccount.addTransaction("[Transfer] $" + transferAmount + " transferred to Account: " + receiverAccount.getAccountNumber());
            receiverAccount.addTransaction("[Transfer] Received $" + transferAmount + " from Account: " + loggedInAccount.getAccountNumber());

            System.out.println("\n===============================================");
            System.out.println("Transfer Successful!");
            System.out.println("Transferred Amount: $" + transferAmount);
            System.out.println("Receiver Account: " + receiverAccount.getAccountNumber());
            System.out.println("Current Balance: $" + loggedInAccount.getBalance());
            System.out.println("===============================================");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } 
    }

    //Method for transaction history for logged in user
    private static void transactionHistoryForLoggedInUser(Account loggedInAccount) {
        System.out.println("\n========== TRANSACTION HISTORY ==========");
        loggedInAccount.displayTransactionHistory();
    }

    //Method for monthly statement for logged in user
    private static void monthlyStatementForLoggedInUser(Account loggedInAccount) {
        System.out.println("\n========== MONTHLY BANK STATEMENT ==========");
        System.out.println("Account Number: " + loggedInAccount.getAccountNumber());
        System.out.println("Account Holder: " + loggedInAccount.getAccountHolderName());

        System.out.println("\nTransaction History: ");
        loggedInAccount.displayTransactionHistory();

        System.out.println("\nCurrent Balance: $" + loggedInAccount.getBalance());
        System.out.println("===============================================");
    }

    //Method for changing PIN for logged in user
    private static void changePINforLoggedInUser(Scanner sc, Account loggedInAccount) {
        System.out.println("Enter your current PIN: ");
        int currentPin = sc.nextInt();
        sc.nextLine();
        
        if(currentPin != loggedInAccount.getAccountPIN()) {
            System.out.println("Error: Incorrect PIN!");
            return;
        }

        System.out.println("Enter your new PIN: ");
        int newPin = sc.nextInt();
        sc.nextLine(); 
        
        if(currentPin == newPin) {
            System.out.println("New PIN cannot be same as the current PIN");
            return;
        }

        System.out.println("Confirm your new PIN: ");
        int confirmPin = sc.nextInt();
        sc.nextLine();

        if(newPin == confirmPin) {
            System.out.println("PIN updated successfully!");
            loggedInAccount.setAccountPIN(newPin);
        } else {
            System.out.println("Error: PINs do not match.");
            return;
        }

        System.out.println("\n==============================================");
        System.out.println("PIN changed successfully!");
        System.out.println("==============================================");
    }
}