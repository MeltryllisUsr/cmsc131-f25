/** TODO LIST
 * 
 * Have fromCSV return a CheckingAccount or SavingsAccount based on value.
 * 
 * Move logic checking that amount > 0 out of credit/debit methods. It should go in the constructor for a Transaction.
 * 
 * Move nonsufficient funds logic out of credit/debit methods. It should go in a validate method for a Transaction.
 */
package projects.bank;

abstract class Account {
    private String accountId; 
    private double balance;
    private String accountName;

/**
 * Constructs a new Account with the specified type, ID, name, and balance.
 *
 * @param accountType the type of account (e.g., "savings" or "checking")
 * @param accountId the unique ID of the account
 * @param accountName the name of the account owner
 * @param balance the balance of the account
 */    
    protected Account(String accountId, String accountName, double balance){
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
        return new Account(id, name, bal);
    }
     /**
     * Adds money to the account.
     * @param amount amount to add (must be positive)
     */
    public void credit(double amount) {
        if (amount <= 0) {
            System.out.println("Error: credit amount must be positive.");
            return;
        }
        balance += amount;
        balance = roundToTwoDecimals(balance);
    }
    /**
     * Subtracts money from the account if there’s enough balance.
     * @param amount amount to subtract (must be positive and <= balance)
     */
    public void debit(double amount) {
        if (amount <= 0) {
            System.out.println("Error: debit amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("Error: insufficient funds.");
            return;
        }
        balance -= amount;
        balance = roundToTwoDecimals(balance);
    }

    /**
     * Utility method to round balance to two decimal places.
     */
    private double roundToTwoDecimals(double value) {
        return Math.floor(value * 100) / 100.0;
    }
    
    public String toCSV() {
        return getType() + "," + accountId + "," + accountName + "," + balance;
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
    abstract AccountType getType();
    
}