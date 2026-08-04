package Bank;

import java.util.Scanner;

public class LoginManager {
    
    private AccountManager accountManager;
    
    // Constructor - takes AccountManager to access accounts
    public LoginManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
    
    // Method for case 8 "LOGIN"
    public Account loginAccount(Scanner sc) {
        System.out.println("Enter your Account Number: ");
        int loginAccountNumber = sc.nextInt();
        sc.nextLine();

        Account account = accountManager.findAccountByNumber(loginAccountNumber);

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
}