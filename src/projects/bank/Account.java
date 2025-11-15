package projects.bank;

public abstract class Account {

    private final String accountId;
    private double balance;
    private final String accountName;
    private final AccountType accountType;

    /**
     * Constructs a new Account with the specified type, ID, name, and balance.
     */
    public Account(AccountType accountType, String accountId, String accountName, double balance) {
        this.accountType = accountType;
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = roundToTwoDecimals(balance);
    }

    /**
     * Creates an Account object from a CSV-formatted string.
     * Expected format: "savings,xf123456,Name,1000.00"
     */
    public static Account fromCSV(String csv) {

        String[] parts = csv.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid CSV account line: " + csv);
        }

        AccountType type = AccountType.valueOf(parts[0].trim().toUpperCase());
        String id = parts[1].trim();
        String name = parts[2].trim();
        double bal = Double.parseDouble(parts[3].trim());

        return switch (type) {
            case SAVINGS -> new SavingsAccount(id, name, bal);
            case CHECKING -> new CheckingAccount(id, name, bal);
        };
    }

    /** Deposits the given amount into the account. */
    public void credit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Credit amount must be positive.");
        }
        balance = roundToTwoDecimals(balance + amount);
    }

    /** Withdraws the given amount from the account. */
    public void debit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Debit amount must be positive.");
        }
        balance = roundToTwoDecimals(balance - amount);
    }

    /** Utility: round to 2 decimals. */
    private double roundToTwoDecimals(double value) {
        return Math.floor(value * 100) / 100.0;
    }

    /** CSV representation */
    public String toCSV() {
        return accountType + "," + accountId + "," + accountName + "," + balance;
    }

    // ----------- Getters -----------
    public String getAccountId() { return accountId; }
    public String getAccountName() { return accountName; }
    public double getBalance() { return balance; }

    /**
     * Basic withdrawal rule: must have enough balance.
     * SavingsAccount will override this to add:
     *  - monthly limits
     *  - threshold checks
     */
    public boolean canWithdraw(double amount) {
        return amount <= balance;
    }

    /** Must be implemented by subclasses. */
    public abstract AccountType getType();

    /** Monthly interest/penalties. */
    public abstract void doEndOfMonthActions(Audit audit);
}
