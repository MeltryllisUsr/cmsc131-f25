package projects.bank;

public class Deposit extends Transaction {

    public Deposit(String accountID, double amount) {
        super(accountID, amount);
    }

    @Override
    public void execute(Account account) {
        // Perform deposit
        account.setBalance(account.getBalance() + getAmount());
    }

    @Override
    public boolean validate(Account account) {
        // Validate before executing
        if (account == null) return false;
        if (!account.getAccountId().equals(getAccountID())) return false;
        if (getAmount() <= 0) return false;
        return true;
    }
}
