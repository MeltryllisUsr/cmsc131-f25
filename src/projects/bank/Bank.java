public class Bank {
    private Account[] accounts;
    private int count;

    public Bank() {
        accounts = new Account[100];
        count = 0;
    }

    public void addAccount(Account acc) {
    if (count == accounts.length) {
        Account[] biggerArray = new Account[accounts.length * 2];
        for (int i = 0; i < accounts.length; i++) {
            biggerArray[i] = accounts[i];
        }
        accounts = biggerArray;
    }
    accounts[count] = acc;
    count++;
}

    public Account getAccount(int accountID) {
        for (int i = 0; i <count; i++){
            if (accounts[i].getAccountId()==accountID){
            return accounts[i];
                }
            }
            return null;
        }
    public int getCount(){
        return count;
        }
        public Account getAccountByName(String accountName){
            for (int i = 0; i < count; i++) {
                if (accounts[i].getAccountName().equals(accountName)) {
                    return accounts[i];
                }
            }
            return null;
        }
    }