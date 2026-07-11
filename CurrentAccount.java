package Bank;

//separate class for Current Account
public class CurrentAccount extends Account {
    private double overdraftLimit;

    //Constructor taking 3 same arguments from the parent class 
    public CurrentAccount(String accountHolderName, int accountNumber, double balance, double overdraftLimit) {
        super(accountHolderName, accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }
    
    //Creating method to Current Account Details
    @Override
    public void displayAccountDetails(){
        System.out.println("\n===================================================");
        System.out.println("               ACCOUNT INFORMATION                 ");
        System.out.println("===================================================");
        super.displayAccountDetails();
        System.out.println("Account Type   : Current Account");
        System.out.println("Overdraft Limit: $" + this.overdraftLimit);
        System.out.println("===================================================");
    }

    //Creating method for withdrawing money
    @Override
    public void withdraw(double amount){
        System.out.println("\n--- Processing Withdrawal: $" + amount + " ---");
        if(amount <= 0){
            System.out.println("[REJECTED] Amount must be greater than zero.");
        }
        else if( amount <= (balance + overdraftLimit)) {
            this.balance = this.balance - amount;
            System.out.println("[SUCCESS]  Money withdrawn successfully.");
            System.out.println("[BALANCE]  Remaining Balance: $" + this.balance);
        }
        else{
            System.out.println("[DENIED]   Transaction failed! Overdraft limit exceeded.");
        }
        System.out.println("---------------------------------------------------");
    }

    //setting getter for the overdraftLimit
    public double getOverdraftLimit() {
        return this.overdraftLimit;
    }
}