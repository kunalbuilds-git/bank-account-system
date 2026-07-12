package Bank;

//creating another class/file for accounts - savings and Current
public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountHolderName, int accountNumber, double balance, double interestRate) {
        super(accountHolderName, accountNumber, balance);
        this.interestRate = interestRate;
    }
    
    //making METHOD for savings account
    @Override
    public void displayAccountDetails() {
        System.out.println("\n===================================================");
        System.out.println("               ACCOUNT INFORMATION                 ");
        System.out.println("===================================================");
        super.displayAccountDetails();
        System.out.println("Account Type   : Savings Account");
        System.out.println("Interest Rate  : " + this.interestRate + "%");
        System.out.println("===================================================");
    }
 
    //adding the Interest method
    public void addInterest() {
        System.out.println("\n--- Processing Monthly Interest ---");
        double interest = balance * (interestRate / 100);
        super.deposit(interest);
        System.out.println("[INTEREST] Earned Amount   : $" + interest);
        System.out.println("[BALANCE]  Updated Balance : $" + this.balance);
        System.out.println("---------------------------------------------------");
    }

    //setting getter for interestRate
    public double getInterestRate(){
        return this.interestRate;
    }

}