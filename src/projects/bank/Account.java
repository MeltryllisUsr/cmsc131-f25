public class Account {
    private int accountId; // TODO should be String
    private double balance;
    private String accountName;
    private int accountType; // TODO should be String or custom AccountType enum

    // TODO add constructor

    public int getAccountId() {
        return accountId;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public double getBalance() {
        return balance;
    }
    public int getAccountType() {
        return accountType;
    }
    
}