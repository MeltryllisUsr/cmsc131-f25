package projects.bank;

abstract class Transaction {

    private final String accountID;
    private final double amount;

    /**
     * Executes this transaction on the specified account, using the provided audit logger.
     * Each subclass (Deposit, Withdrawal, etc.) will define its own behavior.
     *
     * @param account the account to apply the transaction to
     * @param audit   the Audit object used to log actions and errors
     */
    abstract void execute(Account account, Audit audit);

    /**
     * Validates this transaction before execution.
     * Each subclass will define its own logic.
     *
     * @param account the account to validate against
     * @return true if valid, false otherwise
     */
    abstract boolean validate(Account account);

    /**
     * Constructs a Transaction object with the given account ID and amount.
     *
     * @param accountID the ID of the account
     * @param amount    the transaction amount
     */
    protected Transaction(String accountID, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be > 0");
        }

        // Validate account ID format
        if (!accountID.matches("[a-zA-Z]{1,2}\\d{3,}")) {
            throw new IllegalArgumentException("Invalid account ID format: " + accountID);
        }

        this.accountID = accountID;
        this.amount = amount;
    }

    /**
     * Factory method that creates the correct Transaction subclass
     * based on the input line from the CSV file.
     *
     * Expected format: "deposit,xf123456,200.0"
     *
     * @param inputLine CSV-formatted transaction line
     * @return a Transaction object (Deposit or Withdrawal)
     */
    protected static Transaction create(String inputLine) {

        if (inputLine == null || inputLine.isBlank()) {
            throw new IllegalArgumentException("Transaction line is empty.");
        }

        String[] parts = inputLine.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid transaction line: " + inputLine);
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(parts[0].trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid transaction type: " + parts[0]);
        }

        String accountID = parts[1].trim();
        double amount;

        try {
            amount = Double.parseDouble(parts[2].trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid amount in transaction: " + parts[2]);
        }

        switch (type) {
            case DEPOSIT:
                return new Deposit(accountID, amount);

            case WITHDRAWAL:
                return new Withdrawal(accountID, amount);

            default:
                // Should never happen unless enum updated incorrectly
                throw new IllegalStateException("Unhandled transaction type: " + type);
        }
    }

    /** Returns the account ID associated with this transaction. */
    public String getAccountID() {
        return accountID;
    }

    /** Returns the transaction amount. */
    public double getAmount() {
        return amount;
    }
}
