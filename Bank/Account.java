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
    
    // Main constructor - sets up name, number, and checks for negative starting balance, transaction logs
    public Account(String name, int accountNo, double accountbalance) {
        this.accountHolderName = name;
        this.accountNumber = accountNo;
        if (accountbalance >= 0) {
            this.balance = accountbalance;
        } else {
            throw new IllegalArgumentException("Initial balance cannot be negative!");
        }

        //initializing transaction history 
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add("Account was created with the initial balance of: $" + accountbalance);
    }

    // Standard deposit method
    public void deposit(double amount) {
        if (amount > 0) { 
            this.balance = this.balance + amount;
            System.out.println("Amount deposited successfully: $" + amount);
            //modifying for deposite tracking log 
            this.addTransaction(" [Deposit] $" + amount + " deposited Successfully.");
        } else {
           throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }        
    }

    // Standard withdraw method
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("withdrawal amount must be greater than zero!");
        } else if (amount <= balance) {
            this.balance = this.balance - amount;
            System.out.println("Amount withdrew Successfully: $" + amount);
            //adding logging to this method
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

    //DAY 70: DYNAMIC ARRAYLIST IMPLEMENTATION
    public static void main(String[] args) {
        System.out.println("--- BANKING SYSTEM POLYMORPHIC ARRAYLIST --- \n");

        // Declare a resizable ArrayList that manages references of type 'Account'
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
                System.out.println("Enter Your Choice (1-12): ");

                int choice = sc.nextInt();
                sc.nextLine(); //Clears enter key from the stream

                if(choice == 5){
                    System.out.println("Exiting System.... Goodbye!");
                    break;
                }

                switch(choice){

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
                        viewTransactionhistory(sc, accounts);
                        break;

                    case 8:
                        saveAccountsToFile(sc, accounts);
                        break;

                    case 9:
                        loadAccountsFromFile(sc, accounts);
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


                    default:
                        System.out.print("Invalid Option!! Please choose a option between 1 to 7.");

                }
            }

            catch(InputMismatchException e){
                System.out.println("ERROR: Invalid input type! Please enter numbers only.");
                sc.nextLine(); //avoiding infite loop 
            }
        }
        sc.close();        
    }
    // --- HELPER METHODS FOR the cases created before in the main method ---

    private static void showMenu() {
        System.out.println("\n---MAIN MENU---");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Display All Accounts");
        System.out.println("5. Exit System");
        System.out.println("6. Transfer Money");
        System.out.println("7. Transaction History.");
        System.out.println("8. Save Accounts to File");
        System.out.println("10. Delete Account");
        System.out.println("11. Edit Account Details");
        System.out.println("12. Search Account by Name");
        System.out.println("Enter Your Choice (1-12): ");
    }
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

    //Method for case 1 "Create account"
    private static void createAccount(Scanner sc, ArrayList<Account> accounts) {

        System.out.println("Enter Account Holder's Name: ");
        String name = sc.nextLine();

        System.out.println("Enter Your Unique Account Number: ");
        int accNum = sc.nextInt();

        //Preventing duplicate accounts
        Account existingAccount = findAccountByNumber(accounts, accNum);
        if (existingAccount != null) {
            //Account already exists
            System.out.println("Error: Account Already exists! Try new Account number");
            return;
        } 

        System.out.println("Enter Initial Deposite Balance: ");
        Double initBalance = sc.nextDouble();
        sc.nextLine();

        //Instantiates and saves it inside your dynamic list!
        accounts.add(new Account(name, accNum, initBalance));
        System.out.println("Account opened and initial amount added Successfully!");

        //Showing account details after creation of account
        System.out.println("\n===========================================");
        System.out.println("OPERATION SUCCESSFUL!!");
        System.out.println("Account Number : " + accNum);
        System.out.println("Holder : " + name);
        System.out.println("Balance : $" + initBalance); // EDITED HERE: Changed literal string to actual variable printing
        System.out.println("\n===========================================");
    }

    //Method for case 2 "Deposite Money"
    private static void depositMoney(Scanner sc, ArrayList<Account> accounts){ 
        System.out.println("Enter the target Account Number for Deposit: ");
        int depositaccNum = sc.nextInt();

        System.out.print("Enter the amount to deposit: ");
        double depositAmt = sc.nextDouble();
        sc.nextLine(); // buffer clearing

        Account account = findAccountByNumber(accounts, depositaccNum);
        
        if(account == null) {
            System.out.println("Error: Account Number " + depositaccNum + " not found!");
        } else {
            try {
                account.deposit(depositAmt);

                //UI upgrade 
                System.out.println("\n======================================");
                System.out.println("$" + depositAmt + " deposited Successfully!" );
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
        sc.nextLine(); // buffer clearing

        Account account = findAccountByNumber(accounts, withdrawalaccNum);

        if(account == null) {
            System.out.println("Error: Account: " + withdrawalaccNum + "not found!");
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

    //Method for "transfering money" case 6:
    private  static void transferMoney(Scanner sc, ArrayList<Account> accounts) {
        System.out.println("Enter sender's Account Number: ");
        int senderAccNum = sc.nextInt();

        System.out.println("Enter Receiver's Account Number: ");
        int receiverAccNum = sc.nextInt();

        System.out.println("Enter amount to transfer: ");
        double transferAmt = sc.nextDouble();

        //calling helper method for sender account number verification

       Account senderAccount = findAccountByNumber(accounts, senderAccNum);
       if(senderAccount == null) {
        System.out.println("Error: Sender not found!!");
        return;
       } 

       //calling helper method for receiver account number verification 
       Account receiverAccount = findAccountByNumber(accounts, receiverAccNum);

       if( receiverAccount == null){
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

             //showing messages to for withdrawal and deposit
            senderAccount.addTransaction(" [Transfer] " + transferAmt + ", Transferred to Account: " + receiverAccNum + ".");
            receiverAccount.addTransaction(" [Transfer] Received: $" + transferAmt + ", from Account: " + senderAccNum + ".");

             System.out.println("Transfer Successful!");
             System.out.println("\n===========================================");
             System.out.println("OPERATION SUCCESSFUL!!");
             System.out.println("Sender's Account Number : " + senderAccNum);
             System.out.println("Receiver's Account Number : " + receiverAccNum);
             System.out.println("Amount transffered : $" + transferAmt);
             System.out.println("===========================================");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());            
        }
    }

    //adding transactions method

    public void addTransaction(String description) {
        transactionHistory.add(description);
    }

    //Displaying that transsaction history
    public void displayTransactionhistory() {
        System.out.println("\n--- Transaction History for Account number: " + this.accountNumber + " ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions performed yet!!");
        } else {
            for ( String transaction : transactionHistory) {
                System.out.println(" -" + transaction);  
            }
        }
    }

    //Transaction History method
    private static void viewTransactionhistory(Scanner sc, ArrayList<Account> accounts){
        System.out.println("Enter the account number to check transaction history for: ");
        int accNum = sc.nextInt();
        sc.nextLine();
        
        Account account = findAccountByNumber(accounts, accNum);

        if(account == null){
            System.out.println("Error: Account Number: " + accNum + " not found!");            
        } else{
            System.out.println("\n---TRANSACTION HISTORY---");
            account.displayTransactionhistory();
        }
    }   

    //helper method to validate and find the account number
    private static Account findAccountByNumber(ArrayList<Account> accounts, int accountNumber){
        for(Account acc : accounts){
            if (acc.accountNumber == accountNumber) {
                return acc; //returning the account object if found
            }
        }
        return null; //returning null if not found
    }

    //METHOD FOR CASE 8 "SAVE ACCOUNTS TO FILE"
    private static void saveAccountsToFile(Scanner sc, ArrayList<Account> accounts) {
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
    private static void loadAccountsFromFile(Scanner sc, ArrayList<Account> accounts) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {

            //removing old accounts from loding in the ArrayList
            accounts.clear(); //clears old and deleted accounts from loading 

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int accountNumber = Integer.parseInt(parts[0].trim());
                String accountHolderName = parts[1].trim();
                double balance = Double.parseDouble(parts[2].trim());
                
                Account account = new Account(accountHolderName, accountNumber, balance);
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

        //calling helper method to check the account number
        Account account = findAccountByNumber(accounts, deleteAccNum);

        if(account == null) {
            System.out.println("Error: Account: " + deleteAccNum + " not found!!");
        } else {
            System.out.println("Are you sure? Type 'Y' to confirm or 'N' to cancel: ");
            char confirmationYN = sc.next().charAt(0);
            
            if(confirmationYN == 'Y' || confirmationYN == 'y'){
                //deleting the account
                accounts.remove(account);
                // EDITED HERE: Moved the success layout inside the confirmation block so it won't fire incorrectly
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
        sc.nextLine(); //clearing buffer

        //checking if the account number is valid or not
        Account account = findAccountByNumber(accounts, editingAccNumber);

        if(account == null) {
            System.out.println("Error: Account not found. Please check if the account number is correct?");    
            return;        
        } 

        // EDITED HERE: Flattened structure out beautifully, removed unnecessary else wrapper block, and handled scanner stream issues
        System.out.println("What you want to edit? \n 1. Name \n 2. Cancel");
        int editChoice = sc.nextInt();
        sc.nextLine(); //another buffer cleared 

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

    //using a setter to set a new name 
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
}