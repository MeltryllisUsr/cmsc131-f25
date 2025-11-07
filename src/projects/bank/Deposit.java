/** TODO / comments
 * 
 * you should use credit instead of setBalance
 */
package projects.bank;

public class Deposit extends Transaction {

    public Deposit(String accountID, double amount) {
        super(accountID, amount);
    }

    @Override
    public void execute(Account account, Audit audit) {
        if (validate(account)) {
            // Perform deposit
            account.setBalance(account.getBalance() + getAmount());
            audit.write(account, String.format("Deposited $%.2f", getAmount()), Audit.EntryType.INFO);
        } else {
            audit.write(account, String.format("Deposit of $%.2f failed.", getAmount()), Audit.EntryType.ERROR);
        }
    }

    @Override
    public boolean validate(Account account) {
        // Always valid, but method must exist
        return true;
    }
}
