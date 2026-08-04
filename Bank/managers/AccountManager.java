package Bank.managers;

import java.util.ArrayList;
import java.util.Scanner;

import Bank.core.Account;

public class AccountManager {
    
    private ArrayList<Account> accounts;
    
    // Constructor - initialize the accounts list
    public AccountManager() {
        this.accounts = new ArrayList<>();
    }
    
    // Get the accounts list (used by other managers)
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
    
    // Method for case 2 "Display all accounts"
    public void displayAllAccounts() {
        if(accounts.isEmpty()){
            System.out.println("There is nothing in our inventory! No active accounts.");
        } else {
            System.out.println("\n---PRINTING ALL THE ACCOUNTS---");
            for (Account acc : accounts) {
                acc.displayAccountDetails();
            }
        }
    }

    // Method for case 1 "Create account"
    public void createAccount(Scanner sc) {

        System.out.println("Enter Account Holder's Name: ");
        String name = sc.nextLine();

        int accNum = Account.nextAccountNumber;
        Account.nextAccountNumber++;
        System.out.println("System generated Account Number: " + accNum);

        Account existingAccount = findAccountByNumber(accNum);
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

    // Helper method to validate and find the account by number
    public Account findAccountByNumber(int accountNumber) {
        for(Account acc : accounts){
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }
        return null;
    }

    // METHOD FOR CASE 5 "DELETE ACCOUNT"
    public void deleteAccount(Scanner sc) {
        System.out.println("Enter Account number you want to delete: ");
        int deleteAccNum = sc.nextInt();
        sc.nextLine();

        Account account = findAccountByNumber(deleteAccNum);

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

    // Method for case 6 "EDIT ACCOUNT DETAILS"
    public void editAccountDetails(Scanner sc) {
        System.out.println("Enter the Account Number: ");
        int editingAccNumber = sc.nextInt();
        sc.nextLine();

        Account account = findAccountByNumber(editingAccNumber);

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

    // Method for case 7 "SEARCH ACCOUNT BY NAME"
    public void searchAccountByName(Scanner sc) {
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
}