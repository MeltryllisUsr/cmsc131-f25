package projects.bank;

public class CheckingAccount extends Account {

    private static final double MIN_BALANCE = 100.0;
    private static final double PENALTY = 10.0;

    public CheckingAccount(String id, String name, double bal) {
        super(AccountType.CHECKING, id, name, bal);
    }

    @Override
    public AccountType getType() {
        return AccountType.CHECKING;
    }

    /**
     * Allows Withdrawal to access the penalty amount
     * without instanceof or type checking.
     */
    public static double getPenaltyAmount() {
        return PENALTY;
    }

    @Override
    public void doEndOfMonthActions(Audit audit) {
        if (getBalance() < MIN_BALANCE) {
            debit(PENALTY);
            audit.write(this,
                String.format("Low balance penalty charged: $%.2f", PENALTY),
                Audit.EntryType.INFO
            );
        }
    }
}
