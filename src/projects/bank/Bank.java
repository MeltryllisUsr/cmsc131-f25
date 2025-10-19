package projects.bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Bank {
    private Account[] accounts;
    private int count;

    public Bank() {
        accounts = new Account[100];
        count = 0;
    }

    public boolean addAccount(Account acc) { 
    if (count == accounts.length) {
        Account[] biggerArray = new Account[accounts.length * 2];
        for (int i = 0; i < accounts.length; i++) {
            biggerArray[i] = accounts[i];
        }
        accounts = biggerArray;
    }
    accounts[count] = acc;
    count++;
    return true;
}

    public Account getAccount(int accountId) { 
        for (int i = 0; i <count; i++){
            if (accounts[i].getAccountId().equals(accountId)){
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

    // TODO javadoc
    // TODO use try-catch so this method does not throw
    // TODO return boolean
    public void loadAccounts(String filename) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Account acc = Account.fromCSV(line);
            addAccount(acc);
        }
        input.close();
    }

    // TODO javadoc
    public boolean writeAccounts(String filename){
        boolean result = true;
        File file = new File(filename); // BUG: this is never used
        FileWriter writer = null;
        try {
            // TODO initialize writer from file
            for (int i = 0; i <accounts.length; i++){
                Account acc = accounts[i];
                writer.write(acc.getAccountId() + "," + acc.getAccountName() + "," + acc.getBalance() + "," + acc.getAccountType() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
        

    } 
    }
