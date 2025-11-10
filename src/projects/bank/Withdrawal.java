package projects.bank;

public class Withdrawal extends Transaction {

    public Withdrawal(String accountID, double amount) {
        super(accountID, amount);
    }

@Override
public void execute(Account account, Audit audit) {
    if (validate(account)) {
        account.debit(getAmount());
        audit.write(account, String.format("Withdrawal $%.2f", getAmount()), Audit.EntryType.INFO);
    } else {
        audit.write(account, String.format("Withdrawal of $%.2f failed.", getAmount()), Audit.EntryType.ERROR);
    }
}

    @Override
    public boolean validate(Account account) {
        return account.getBalance() >= getAmount();
    }
}
