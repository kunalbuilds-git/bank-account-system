package Bank.managers;

import java.util.Scanner;

import Bank.model.Account;

public class LoggedInUserMenu {
    
    private AccountManager accountManager;
    
    // Constructor - takes AccountManager to access accounts for transfers
    public LoggedInUserMenu(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
    
    // METHOD for account menu under login option
    public void accountMenu(Scanner sc, Account loggedInAccount) {
        System.out.println("Welcome " + loggedInAccount.getAccountHolderName());
        while(true) {

            Account existingAccount = accountManager.findAccountByNumber(loggedInAccount.getAccountNumber());

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
                transferForLoggedInUser(sc, loggedInAccount);
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

    private void depositForLoggedInUser(Scanner sc, Account loggedInAccount) {

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

    private void withdrawForLoggedInUser(Scanner sc, Account loggedInAccount) {
        
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

    // Method for transfer for logged in user
    private void transferForLoggedInUser(Scanner sc, Account loggedInAccount) {

        System.out.println("Enter Account number to transfer Money into: ");
        int receiverAccountNumber = sc.nextInt();
        sc.nextLine();

        Account receiverAccount = accountManager.findAccountByNumber(receiverAccountNumber);

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

    // Method for transaction history for logged in user
    private void transactionHistoryForLoggedInUser(Account loggedInAccount) {
        System.out.println("\n========== TRANSACTION HISTORY ==========");
        loggedInAccount.displayTransactionHistory();
    }

    // Method for monthly statement for logged in user
    private void monthlyStatementForLoggedInUser(Account loggedInAccount) {
        System.out.println("\n========== MONTHLY BANK STATEMENT ==========");
        System.out.println("Account Number: " + loggedInAccount.getAccountNumber());
        System.out.println("Account Holder: " + loggedInAccount.getAccountHolderName());

        System.out.println("\nTransaction History: ");
        loggedInAccount.displayTransactionHistory();

        System.out.println("\nCurrent Balance: $" + loggedInAccount.getBalance());
        System.out.println("===============================================");
    }

    // Method for changing PIN for logged in user
    private void changePINforLoggedInUser(Scanner sc, Account loggedInAccount) {
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