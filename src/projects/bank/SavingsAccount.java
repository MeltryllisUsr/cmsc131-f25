package projects.bank;

public class SavingsAccount extends Account {

    public SavingsAccount(String id, String ownerName, double balance) {
        super(AccountType.SAVINGS, id, ownerName, balance);
    }

    @Override
    public AccountType getType() {
        return AccountType.SAVINGS;

    }

}
