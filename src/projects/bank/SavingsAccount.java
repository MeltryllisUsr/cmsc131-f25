package projects.bank;

public class SavingsAccount extends Account {

    public SavingsAccount(String id, String name, double bal) {
        super(AccountType.SAVINGS, id, name, bal);
    }

    @Override
    public AccountType getType() {
        return AccountType.SAVINGS;

    }

}
