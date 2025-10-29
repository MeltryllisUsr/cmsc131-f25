package projects.bank;

public class CheckingAccount extends Account {

    public CheckingAccount(String id, String name, double bal) {
        super(AccountType.CHECKING, id, name, bal);
    }

    @Override
    public AccountType getType() {
        return AccountType.CHECKING;
    }
}
