package projects.bank;

public class Withdrawal extends Transaction {

    private static final double NSF_PENALTY = 10.00;

    public Withdrawal(String accountID, double amount) {
        super(accountID, amount);
    }

    @Override
    public void execute(Account account, Audit audit) {

        double amount = getAmount();

        // --- 1. Fraud / threshold check (Savings and Checking share this rule)
        if (amount > 2500.00) {
            audit.write(account,
                String.format("Withdrawal denied: $%.2f exceeds $2500 limit.", amount),
                Audit.EntryType.ERROR);
            return;
        }

        // --- 2. Savings monthly withdrawal limit check ---
        if (account instanceof SavingsAccount sa) {
            if (sa.getWithdrawalsThisMonth() >= SavingsAccount.MONTHLY_WITHDRAWAL_LIMIT) {
                audit.write(account,
                    "Withdrawal denied: monthly withdrawal limit reached.",
                    Audit.EntryType.ERROR);
                return;
            }
        }

        // --- 3. Sufficient funds? ---
        if (!account.canWithdraw(amount)) {
            // NOT fraud or limit — purely insufficient funds → apply penalty
            if (account.getBalance() >= NSF_PENALTY) {
                account.debit(NSF_PENALTY);
                audit.write(account,
                    String.format("NSF Withdrawal of $%.2f. Penalty applied: $%.2f",
                        amount, NSF_PENALTY),
                    Audit.EntryType.ERROR);
            } else {
                audit.write(account,
                    "NSF withdrawal denied (insufficient funds to apply penalty).",
                    Audit.EntryType.ERROR);
            }
            return;
        }

        // --- 4. Valid withdrawal, execute ---
        account.debit(amount);

        if (account instanceof SavingsAccount sa) {
            sa.recordWithdrawal();
        }

        audit.write(account,
            String.format("Withdrawal successful: $%.2f", amount),
            Audit.EntryType.INFO);
    }

    @Override
    public boolean validate(Account account) {
        return account.canWithdraw(getAmount());
    }
}
