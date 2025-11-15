package projects.bank;

public class SavingsAccount extends Account {
    
    public static final double INTEREST_RATE = 0.01; // 1% per month
    public static final int MONTHLY_WITHDRAWAL_LIMIT = 6;

    private int withdrawalsThisMonth = 0;
        
    @Override
    public AccountType getType() { 
        return AccountType.SAVINGS; 
    }

    @Override
    public boolean canWithdraw(double amount) {
        // Fraud threshold
        if (amount > 2500.00) {
            return false;
        }

        // Monthly limit
        if (withdrawalsThisMonth >= MONTHLY_WITHDRAWAL_LIMIT) {
            return false;
        }

        // Balance check
        return amount <= getBalance();
    }

    // ---- Added public getters you need ----
    public int getWithdrawalsThisMonth() {
        return withdrawalsThisMonth;
    }

    public int getMonthlyLimit() {
        return MONTHLY_WITHDRAWAL_LIMIT;
    }

    public void recordWithdrawal() {
        withdrawalsThisMonth++;
    }

    public void resetMonthlyWithdrawals() {
        withdrawalsThisMonth = 0;
    }

    public SavingsAccount(String id, String name, double bal) {
        super(AccountType.SAVINGS, id, name, bal);
    }

    @Override
    public void doEndOfMonthActions(Audit audit) {
        double interest = getBalance() * INTEREST_RATE;
        credit(interest);

        audit.write(this,
            String.format("Interest applied: $%.2f", interest),
            Audit.EntryType.INFO);

        resetMonthlyWithdrawals(); 
    }
}
