/** TODO list
 * 
 * You can simplify the validate method by writing the upstream code to (1) ensure that account != null at this point, (2) ensure that the transaction account ID matches the target account's ID, (3) ensure that amount > 0. Then this method will just return true.
 */
package projects.bank;

public class Deposit extends Transaction {

    public Deposit(String accountID, double amount) {
        super(accountID, amount);
    }

    @Override
    public void execute(Account account) {
        // Perform deposit
        account.credit(getAmount());
    }

    @Override
    public boolean validate(Account account) {
        // Validate before executing
        return true;
    }
}
