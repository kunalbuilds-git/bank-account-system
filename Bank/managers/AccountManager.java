package Bank.managers;

import Bank.model.Account;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * AccountManager - Handles all Account CRUD operations
 * Manages: Create, Read (Display/Search), Update (Edit), Delete accounts
 * Single Responsibility: Account management only
 */
public class AccountManager {
    
    private ArrayList<Account> accounts;
    
    // ==================== CONSTRUCTOR ====================
    /**
     * Initialize AccountManager with empty accounts list
     */
    public AccountManager() {
        this.accounts = new ArrayList<>();
    }
    
    // ==================== GETTERS ====================
    /**
     * Get the accounts list (used by other managers like FileManager, Transfer operations)
     */
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
    
    // ==================== DISPLAY OPERATIONS ====================
    /**
     * Display all accounts in the system
     * Case 2 in Main Menu
     */
    public void displayAllAccounts() {
        if(accounts.isEmpty()) {
            System.out.println("There is nothing in our inventory! No active accounts.");
        } else {
            System.out.println("\n---PRINTING ALL THE ACCOUNTS---");
            for (Account acc : accounts) {
                acc.displayAccountDetails();
            }
        }
    }

    // ==================== CREATE OPERATIONS ====================
    /**
     * Create a new account with user input
     * Case 1 in Main Menu
     * Auto-generates account number
     */
    public void createAccount(Scanner sc) {
        System.out.println("Enter Account Holder's Name: ");
        String name = sc.nextLine();

        // Generate unique account number
        int accNum = Account.nextAccountNumber;
        Account.nextAccountNumber++;
        System.out.println("System generated Account Number: " + accNum);

        // Check if account already exists
        Account existingAccount = findAccountByNumber(accNum);
        if (existingAccount != null) {
            System.out.println("Error: Account Already exists! Try new Account number");
            return;
        } 

        // Get PIN from user
        System.out.println("Set PIN for your account: ");
        int accountPIN = sc.nextInt();

        // Get initial balance
        System.out.println("Enter Initial Deposit Balance: ");
        Double initBalance = sc.nextDouble();
        sc.nextLine(); // consume newline

        // Create and add account
        try {
            accounts.add(new Account(name, accNum, initBalance, accountPIN));
            
            System.out.println("Account opened and initial amount added successfully!");
            System.out.println("\n===========================================");
            System.out.println("OPERATION SUCCESSFUL!!");
            System.out.println("Account Number : " + accNum);
            System.out.println("Holder : " + name);
            System.out.println("Balance : $" + initBalance);
            System.out.println("===========================================");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== SEARCH OPERATIONS ====================
    /**
     * Find account by account number
     * Used by other methods (login, transfer, etc.)
     * Returns null if not found
     */
    public Account findAccountByNumber(int accountNumber) {
        for(Account acc : accounts) {
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }
        return null;
    }

    /**
     * Search account by account holder name
     * Case 7 in Main Menu
     * Supports case-insensitive search
     */
    public void searchAccountByName(Scanner sc) {
        System.out.println("Enter the name of the account Holder: ");
        String searchName = sc.nextLine();

        boolean found = false;
        System.out.println("\n--- SEARCH RESULTS ---");
        
        for (Account acc : accounts) {
            if(acc.getAccountHolderName().equalsIgnoreCase(searchName)) {
                System.out.println("\n===========================================");
                System.out.println("OPERATION SUCCESSFUL!!");
                System.out.println("Account Found!!");
                acc.displayAccountDetails();
                System.out.println("===========================================");
                found = true;
            }   
        }
        
        if (!found) {
            System.out.println("Error: No account found with this name: " + searchName + ".");
        }
    }

    // ==================== UPDATE OPERATIONS ====================
    /**
     * Edit account details (currently only name)
     * Case 6 in Main Menu
     */
    public void editAccountDetails(Scanner sc) {
        System.out.println("Enter the Account Number: ");
        int editingAccNumber = sc.nextInt();
        sc.nextLine(); // consume newline

        Account account = findAccountByNumber(editingAccNumber);

        if(account == null) {
            System.out.println("Error: Account not found. Please check if the account number is correct?");    
            return;        
        } 

        System.out.println("What you want to edit? \n 1. Name \n 2. Cancel");
        int editChoice = sc.nextInt();
        sc.nextLine(); // consume newline

        switch(editChoice) {
            case 1:
                System.out.println("Enter new name: ");
                String newAccHolderName = sc.nextLine();
                account.setAccountHolderNewName(newAccHolderName);
                System.out.println("Account Holder's name changed successfully!!");
                break;
            case 2:
                System.out.println("Cancelling Account's details editing...");
                break;
            default:
                System.out.println("Error: Invalid choice!!");
        }
    }

    // ==================== DELETE OPERATIONS ====================
    /**
     * Delete an account from the system
     * Case 5 in Main Menu
     * Requires user confirmation before deletion
     */
    public void deleteAccount(Scanner sc) {
        System.out.println("Enter Account number you want to delete: ");
        int deleteAccNum = sc.nextInt();
        sc.nextLine(); // consume newline

        Account account = findAccountByNumber(deleteAccNum);

        if(account == null) {
            System.out.println("Error: Account: " + deleteAccNum + " not found!!");
            return;
        }
        
        System.out.println("Are you sure? Type 'Y' to confirm or 'N' to cancel: ");
        char confirmationYN = sc.next().charAt(0);
        sc.nextLine(); // consume newline
        
        if(confirmationYN == 'Y' || confirmationYN == 'y') {
            accounts.remove(account);
            System.out.println("\n========================================");
            System.out.println("DELETION SUCCESSFUL!");
            System.out.println("Account Deleted : " + deleteAccNum + " has been deleted.");
            System.out.println("========================================");
        } else {
            System.out.println("Deletion cancelled, back to menu...");
        }
    }
}