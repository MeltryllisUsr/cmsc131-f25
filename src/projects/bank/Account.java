package projects.bank;

public class Account {
    private String accountId; 
    private double balance;
    private String accountName;
    private String accountType; // consider using enum

    // TODO javadoc
    public Account(String accountType, String accountId, String accountName, double balance){
        this.accountType = accountType;
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }

    // TODO javadoc
    public static Account fromCSV(String csv) {
        // TODO validate csv
        String[] parts = csv.split(",");
        String accountType = parts[0];
        String accountId = parts[1];
        String accountName = parts[2];
        double balance = Double.parseDouble(parts[3]);
        return new Account(accountType, accountId, accountName, balance);
    }

    // TODO
    // public String toCSV() {}

    public String getAccountId() {
        return accountId;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public double getBalance() {
        return balance;
    }
    public String getAccountType() {
        return accountType;
    }
    
}