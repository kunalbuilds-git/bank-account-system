package Bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner; //Imported the utility framework

public class Account implements Transaction {
    //Core fields set to protected so child classes can use them directly
    protected String accountHolderName;
    protected int accountNumber;
    protected double balance;
    private ArrayList<String> transactionHistory;
    static int nextAccountNumber = 1001;
    private int accountPIN;
    
    // Main constructor - sets up name, number, and checks for negative starting balance, transaction logs
    public Account(String name, int accountNo, double accountbalance, int pin) {
        this.accountHolderName = name;
        this.accountNumber = accountNo;
        this.accountPIN = pin;
        if (accountbalance >= 0) {
            this.balance = accountbalance;
        } else {
            throw new IllegalArgumentException("Initial balance cannot be negative!");
        }

        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add("Account was created with the initial balance of: $" + accountbalance);
    }

    // Standard deposit method
    public void deposit(double amount) {
        if (amount > 0) { 
            this.balance = this.balance + amount;
            System.out.println("Amount deposited successfully: $" + amount);
            this.addTransaction(" [Deposit] $" + amount + " deposited successfully.");
        } else {
           throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }        
    }

    // Standard withdraw method
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero!");
        } else if (amount <= balance) {
            this.balance = this.balance - amount;
            System.out.println("Amount withdrew successfully: $" + amount);
            this.addTransaction(" [Withdraw] $" + amount + " withdrawn successfully");
            
        } else {
            throw new IllegalArgumentException("Insufficient amount for this withdrawal!");
        }           
    }

    // Base layout to display account summary
    public void displayAccountDetails() {
        System.out.println("\n═══════════════════════════════");
        System.out.println("Account Holder:  " + this.accountHolderName);
        System.out.println("Account Number:  " + this.accountNumber);
        System.out.println("Balance:         $" + this.balance);
        System.out.println("═══════════════════════════════");
    }

    // Getters for safely fetching data
    public String getAccountHolderName() {
        return this.accountHolderName;
    }

    public double getBalance() {
        return this.balance;
    }

    //DYNAMIC ARRAYLIST IMPLEMENTATION
    public static void main(String[] args) {
        System.out.println("--- BANKING SYSTEM POLYMORPHIC ARRAYLIST --- \n");

        ArrayList<Account> accounts = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        while(true) {
            try {
                System.out.println("\n---MAIN MENU---");
                System.out.println("1. Create New Account");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Display All Accounts");
                System.out.println("5. Exit System");
                System.out.println("6. Transfer Money");
                System.out.println("7. Transaction History");
                System.out.println("8. Save Accounts to File");
                System.out.println("9. Load Accounts from File");
                System.out.println("10. Delete Account");
                System.out.println("11. Edit Account Details");
                System.out.println("12. Search Account by Name");
                System.out.println("13. Generate Monthly Bank Statement");
                System.out.println("14. Login");
                System.out.println("Enter Your Choice (1-14): ");

                int choice = sc.nextInt();
                sc.nextLine(); //Clears enter key from the stream

                if(choice == 5){
                    System.out.println("Exiting System.... Goodbye!");
                    break;
                }

                switch (choice) {

                    case 1:
                        createAccount(sc, accounts);
                        break;

                    case 2:
                        depositMoney(sc, accounts);
                        break;

                    case 3:
                        withdrawMoney(sc, accounts);
                        break;

                    case 4:
                        displayAccountDetails(accounts);
                        break;

                    case 6:
                        transferMoney(sc, accounts);
                        break;                      

                    case 7:
                        viewTransactionHistory(sc, accounts);
                        break;

                    case 8:
                        saveAccountsToFile(accounts);
                        break;

                    case 9:
                        loadAccountsFromFile(accounts);
                        break;

                    case 10:
                        deleteAccount(sc, accounts);
                        break;

                    case 11:
                        editAccountDetails(sc, accounts);
                        break;

                    case 12:
                        searchAccountByName(sc, accounts);
                        break;

                    case 13:
                        monthlyBankStatement(sc, accounts);
                        break;

                    case 14:
                        Account loggedInAccount = loginAccount(sc, accounts);

                        if (loggedInAccount != null) {
                            accountMenu(sc, loggedInAccount, accounts);
                        }
                        break;


                    default:
                        System.out.print("Invalid Option!! Please choose a option between 1 to 14.");

                }
            }

            catch(InputMismatchException e){
                System.out.println("ERROR: Invalid input type! Please enter numbers only.");
                sc.nextLine(); //avoiding infinite loop 
            }
        }
        sc.close();        
    }

    // --- HELPER METHODS FOR the cases created before in the main method ---

    //Method for case 4 "Display all accounts"
    private static void displayAccountDetails(ArrayList<Account> accounts) {
        if(accounts.isEmpty()){
            System.out.println("There is nothing in our inventory! No active accounts.");
        }else {
            System.out.println("\n---PRINTING ALL THE ACCOUNTS---");
                for (Account acc : accounts) {
                    acc.displayAccountDetails();
                }
        }
    }

    //Setting getter method for the account PIN
    public int getAccountPIN() {
        return this.accountPIN;
    }

    //Method for case 1 "Create account"
    private static void createAccount(Scanner sc, ArrayList<Account> accounts) {

        System.out.println("Enter Account Holder's Name: ");
        String name = sc.nextLine();

        int accNum = nextAccountNumber;
        nextAccountNumber++;
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

    //Method for case 2 "Deposit Money"
    private static void depositMoney(Scanner sc, ArrayList<Account> accounts){ 
        System.out.println("Enter the target Account Number for Deposit: ");
        int depositaccNum = sc.nextInt();

        System.out.print("Enter the amount to deposit: ");
        double depositAmt = sc.nextDouble();
        sc.nextLine();

        Account account = findAccountByNumber(accounts, depositaccNum);
        
        if(account == null) {
            System.out.println("Error: Account Number " + depositaccNum + " not found!");
        } else {
            try {
                account.deposit(depositAmt);

                System.out.println("\n======================================");
                System.out.println("$" + depositAmt + " deposited successfully!" );
                System.out.println("\n Current Balance: $" + account.getBalance());
                System.out.println("\n Transaction Recorded!");
                System.out.println("\n======================================");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    //Method for case 3 "Withdraw Money"
    private static void withdrawMoney(Scanner sc, ArrayList<Account> accounts){ 
        System.out.println("Enter the target Account Number for withdrawal: ");
        int withdrawalaccNum = sc.nextInt();

        System.out.print("Enter the amount to withdraw: ");
        double withdrawalAmt = sc.nextDouble();
        sc.nextLine();

        Account account = findAccountByNumber(accounts, withdrawalaccNum);

        if(account == null) {
            System.out.println("Error: Account: " + withdrawalaccNum + " not found!");
        } else {
            try {
                account.withdraw(withdrawalAmt);
                System.out.println("\n===========================================");
                System.out.println("OPERATION SUCCESSFUL!!");
                System.out.println("Account Number : " + withdrawalaccNum);
                System.out.println("Withdrawal amount : $" + withdrawalAmt); 
                System.out.println("\n===========================================");

            } catch(IllegalArgumentException e) {
                System.out.println("Error: "  + e.getMessage());
            }
        }
    }

    //Method for "transferring money" case 6:
    private static void transferMoney(Scanner sc, ArrayList<Account> accounts) {
        System.out.println("Enter sender's Account Number: ");
        int senderAccNum = sc.nextInt();

        System.out.println("Enter Receiver's Account Number: ");
        int receiverAccNum = sc.nextInt();

        System.out.println("Enter amount to transfer: ");
        double transferAmt = sc.nextDouble();

        Account senderAccount = findAccountByNumber(accounts, senderAccNum);
        if(senderAccount == null) {
            System.out.println("Error: Sender not found!!");
            return;
        } 

        Account receiverAccount = findAccountByNumber(accounts, receiverAccNum);

        if(receiverAccount == null){
            System.out.println("Error: Receiver not found!!");
            return;
        }
        
        if (transferAmt > senderAccount.getBalance()) {
            System.out.println("Error: Insufficient balance. Available Amount: $" + senderAccount.getBalance());
            return;
        }

        try {
             senderAccount.withdraw(transferAmt);
             receiverAccount.deposit(transferAmt);

             senderAccount.addTransaction(" [Transfer] $" + transferAmt + " transferred to Account: " + receiverAccNum + ".");
             receiverAccount.addTransaction(" [Transfer] Received: $" + transferAmt + " from Account: " + senderAccNum + ".");

             System.out.println("Transfer Successful!");
             System.out.println("\n===========================================");
             System.out.println("OPERATION SUCCESSFUL!!");
             System.out.println("Sender's Account Number : " + senderAccNum);
             System.out.println("Receiver's Account Number : " + receiverAccNum);
             System.out.println("Amount transferred : $" + transferAmt);
             System.out.println("===========================================");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());            
        }
    }

    //Adding transactions to history
    public void addTransaction(String description) {
        transactionHistory.add(description);
    }

    //Displaying transaction history
    public void displayTransactionHistory() {
        System.out.println("\n--- Transaction History for Account number: " + this.accountNumber + " ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions performed yet!!");
        } else {
            for ( String transaction : transactionHistory) {
                System.out.println(" -" + transaction);  
            }
        }
    }

    //Method for case 7 "View Transaction History"
    private static void viewTransactionHistory(Scanner sc, ArrayList<Account> accounts){
        System.out.println("Enter the account number to check transaction history for: ");
        int accNum = sc.nextInt();
        sc.nextLine();
        
        Account account = findAccountByNumber(accounts, accNum);

        if(account == null){
            System.out.println("Error: Account Number: " + accNum + " not found!");            
        } else{
            System.out.println("\n---TRANSACTION HISTORY---");
            account.displayTransactionHistory();
        }
    }   

    //Helper method to validate and find the account by number
    private static Account findAccountByNumber(ArrayList<Account> accounts, int accountNumber){
        for(Account acc : accounts){
            if (acc.accountNumber == accountNumber) {
                return acc;
            }
        }
        return null;
    }

    //METHOD FOR CASE 8 "SAVE ACCOUNTS TO FILE"
    private static void saveAccountsToFile(ArrayList<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt"))) {
            for (Account acc : accounts) {
                writer.write(acc.accountNumber + "," + acc.accountHolderName + "," + acc.balance);
                writer.newLine();
            }
            System.out.println("Accounts saved to accounts.txt successfully!!");
        } catch (IOException e) {
            System.out.println("Error in saving account: " + e.getMessage());
        } 
    }

    //METHOD FOR CASE 9 "LOAD ACCOUNTS FROM FILE"
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

    //METHOD FOR CASE 10 "DELETE ACCOUNT"
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

    //Method for case 11 "EDIT ACCOUNT DETAILS"
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

    //Setter to update account holder name
    public void setAccountHolderNewName(String newName) {
        this.accountHolderName = newName;
    }

    //Creating Method for case 12 "SEARCH ACCOUNT BY NAME"
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

    //METHOD FOR CASE 13 "GENERATE MONTHLY BANK STATEMENT"
    private static void monthlyBankStatement(Scanner sc, ArrayList<Account> accounts) {
        System.out.println("Enter Account number to generate statement: ");
        int statementAccNum = sc.nextInt();

        Account account = findAccountByNumber(accounts, statementAccNum);
        if(account == null) {
            System.out.println("Error: Account not found. Please check if the account number is correct?");
            return;
        } else {
            System.out.println("\n=====================================");
            System.out.println("   MONTHLY BANK STATEMENT");
            System.out.println("=====================================");
            System.out.println("\nAccount Number  : " + account.accountNumber);
            System.out.println("Account Holder  : " + account.getAccountHolderName());
            System.out.println("\n--- TRANSACTIONS ---");

            account.displayTransactionHistory();
            
            System.out.println("\n--- SUMMARY ---");
            System.out.println("Current Balance : $" + account.getBalance());
            System.out.println("\n=====================================");
        }
    }
    
    //Method for case 14 "LOGIN"
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

    //Getter for account number
    public int getAccountNumber() {
        return this.accountNumber;
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

            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Transaction History");
            System.out.println("5. Monthly Statement");
            System.out.println("6. Change Pin");
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

    //Setter method to update the account PIN
    public void setAccountPIN(int newPIN) {
        this.accountPIN = newPIN;
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