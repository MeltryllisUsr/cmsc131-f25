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
        return true;
    }
}