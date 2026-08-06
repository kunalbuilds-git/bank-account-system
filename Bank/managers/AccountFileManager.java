package Bank.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Bank.model.Account;

public class AccountFileManager {
    
    private AccountManager accountManager;
    private static final String FILE_NAME = "accounts.txt";
    
    // Constructor - takes AccountManager to access accounts
    public AccountFileManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
    
    // METHOD FOR CASE 3 "SAVE ACCOUNTS TO FILE"
    public void saveAccountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            ArrayList<Account> accounts = accountManager.getAccounts();
            
            for (Account acc : accounts) {
                writer.write(acc.getAccountNumber() + "," + acc.getAccountHolderName() + "," + acc.getBalance());
                writer.newLine();
            }
            System.out.println("Accounts saved to " + FILE_NAME + " successfully!!");
        } catch (IOException e) {
            System.out.println("Error in saving account: " + e.getMessage());
        } 
    }

    // METHOD FOR CASE 4 "LOAD ACCOUNTS FROM FILE"
    public void loadAccountsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            ArrayList<Account> accounts = accountManager.getAccounts();
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
            System.out.println("Accounts loaded from " + FILE_NAME + " successfully!!");
        } catch (IOException e) {
            System.out.println("Error in loading accounts: " + e.getMessage());
        }
    }
}