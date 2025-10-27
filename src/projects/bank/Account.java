package projects.bank;

public class Account {
    private String accountId; 
    private double balance;
    private String accountName;
    public enum AccountType{
        SAVINGS,
        CHECKING
    } 
    private AccountType accountType;

/**
 * Constructs a new Account with the specified type, ID, name, and balance.
 *
 * @param accountType the type of account (e.g., "savings" or "checking")
 * @param accountId the unique ID of the account
 * @param accountName the name of the account owner
 * @param balance the balance of the account
 */    
    public Account(AccountType accountType, String accountId, String accountName, double balance){
        this.accountType = accountType;
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }

/**
 * Creates an Account object from a CSV-formatted string.
 * The CSV string must contain the account type, ID, name, and balance separated by commas.
 *
 * @param csv a comma-separated string representing account data
 *             (e.g. "savings,xf123456,Lorenzo de Medici,1000.0")
 * @return a new Account object parsed from the CSV string
 */
    public static Account fromCSV(String csv) {
        String[] parts = csv.split(",");
        AccountType type = AccountType.valueOf(parts[0].toUpperCase()); // converts "savings" to SAVINGS
        String id = parts[1];
        String name = parts[2];
        double bal = Double.parseDouble(parts[3]);
        return new Account(type, id, name, bal);
    }

    
    public String toCSV() {
        return accountType + "," + accountId + "," + accountName + "," + balance;
    }

    public String getAccountId() {
        return accountId;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public double getBalance() {
        return balance;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    
}