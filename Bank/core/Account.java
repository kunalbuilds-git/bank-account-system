package Bank;

import java.util.ArrayList;

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

    // Getter for account PIN
    public int getAccountPIN() {
        return this.accountPIN;
    }

    // Adding transactions to history
    public void addTransaction(String description) {
        transactionHistory.add(description);
    }

    // Displaying transaction history
    public void displayTransactionHistory() {
        System.out.println("\n--- Transaction History for Account number: " + this.accountNumber + " ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions performed yet!!");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(" -" + transaction);  
            }
        }
    }

    // Setter to update account holder name
    public void setAccountHolderNewName(String newName) {
        this.accountHolderName = newName;
    }

    // Getter for account number
    public int getAccountNumber() {
        return this.accountNumber;
    }

    // Setter method to update the account PIN
    public void setAccountPIN(int newPIN) {
        this.accountPIN = newPIN;
    }
}