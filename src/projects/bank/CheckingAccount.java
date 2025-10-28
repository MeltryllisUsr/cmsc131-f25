package projects.bank;

public class CheckingAccount extends Account {

    public CheckingAccount(String id, String ownerName, double balance) {
        super(AccountType.CHECKING, id, ownerName, balance);
    }

    @Override
    public AccountType getType() {
        return AccountType.CHECKING;
    }
}
