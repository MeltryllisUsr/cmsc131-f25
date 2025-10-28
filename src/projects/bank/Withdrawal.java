package projects.bank;

public class Withdrawal extends Transaction {

    public Withdrawal(String accountID, double amount) {
        super(accountID, amount);
    }

    @Override
    public void execute(Account account) {
        // Perform withdrawal only if validated
        if (validate(account)) {
            account.setBalance(account.getBalance() - getAmount());
        } else {
            System.out.println("Invalid withdrawal: insufficient funds or invalid data.");
        }
    }

@Override
    public boolean validate(Account account) {
        if (account == null) return false;
        if (!account.getAccountId().equals(getAccountID())) return false;
        if (getAmount() <= 0) return false;
        if (account.getBalance() < getAmount()) return false; 
        return true;
    }
}
