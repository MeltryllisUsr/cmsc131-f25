package projects.bank;

public class Bank {
    private Account[] accounts;
    private int count;

    public class Bank() {
        accounts = new Account[100];
        count = 0;
    }

    public void addAccount(Account acc) {
        accounts[count]=acc;
        count++;
    }

    public Account getAccount(String accountID) {
        for (int i = 0; i <count; i++){
            if accounts[i].getAccountId().equals(accoundID){
            return accounts[i];
                }
            }
            return null;
        }
    public int getCount{
        return count;
        }
    public Account getAccountName{
        for (int i=0; i<count; i++){
            if accounts[i].getAccountName().equals(accountName){
                return accounts[i];
                }
            }
        }
    }
}