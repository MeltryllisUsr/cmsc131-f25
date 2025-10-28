package projects.bank;

abstract class Transaction {

    private final String accountID;
    private final double amount;


    /**
     * Executes this transaction on the specified account.
     * Each subclass (Deposit, Withdrawal, etc.) will define its own behavior.
     *
     * @param account the account to apply the transaction to
     */
    abstract void execute(Account account);

    /**
     * Validates this transaction before execution.
     * Each subclass (Deposit, Withdrawal, etc.) will define its own logic.
     *
     * @param account the account to validate against
     * @return true if valid, false otherwise
     */
    abstract boolean validate(Account account);

    /**
     * Constructs a Transaction object with the given account ID and amount.
     *
     * @param accountID the ID of the account
     * @param amount the transaction amount
     */
    protected Transaction(String accountID, double amount) {
        this.accountID = accountID;
        this.amount = amount;
    }
    
    /**
     * Factory method that creates the correct Transaction subclass
     * based on the input line from the CSV file.
     *
     * Example line: "deposit,xf123456,200.0"
     *
     * @param inputLine a CSV-formatted line describing the transaction
     * @return a new Transaction object (Deposit or Withdrawal)
     */
    protected static Transaction create(String inputLine) {
        String[] parts = inputLine.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid transaction line: " + inputLine);
        }

        String type = parts[0].trim().toLowerCase();
        String accountID = parts[1].trim();
        double amount = Double.parseDouble(parts[2].trim());

        switch (type) {
            case "deposit":
                return new Deposit(accountID, amount);
            case "withdrawal":
                return new Withdrawal(accountID, amount);
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + type);
        }
    }

    /**
     * Returns the account ID associated with this transaction.
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * Returns the transaction amount.
     */
    public double getAmount() {
        return amount;
    }
}
