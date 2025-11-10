package projects.bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Bank {
    private Account[] accounts;
    private int count;
    private Audit audit;


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

    public Account getAccount(String accountId) { 
        for (int i = 0; i <count; i++){
            if (accounts[i].getAccountId().equals(accountId)){
            return accounts[i];
                }
            }
            return null;
        }
    public int indexOfAccount(String accountId) { 
        for (int i = 0; i <count; i++){
            if (accounts[i].getAccountId().equals(accountId)){
            return i;
                }
            }
            return -1;
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

 /**
 * Loads accounts from accounts.csv file and add them to the bank.
 * 
 * @param filename the name (or path) of the accounts CSV file
 * @return true if file was read and all lines processed (or at least method completed),
 *         false if the file could not be opened or a read/parse error occurred
 */
    public boolean loadAccounts(String filename){
        audit = new Audit();
        if (!audit.initialize("audit.csv")) {
        System.out.println("Failed to initialize audit log!");
    } 
        try (Scanner input = new Scanner(new File(filename))){
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Account acc = Account.fromCSV(line);
            addAccount(acc);
        }
        return true;}
         catch (FileNotFoundException e) {
        System.err.println("File not found: " + filename);
        return false;
        }
    }
 /**
 * Writes all accounts to a CSV file.
 * Each line will be in the format: accountType,accountId,accountName,balance
 *
 * @param filename the name (or path) of the accounts CSV file
 * @return true if writing succeeds, false if an I/O error occurs
 */    
    public boolean writeAccounts(String filename){
        boolean result = true;
        File file = new File(filename);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
           for (int i = 0; i < count; i++) {
        Account acc = accounts[i];
        if (acc != null) {
            writer.write(acc.toCSV() + "\n");
    }
}
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    } 
    public void processTransactions(String filename) {
    try (Scanner input = new Scanner(new File(filename))) {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Transaction trs = Transaction.create(line);

            int index = indexOfAccount(trs.getAccountID());
            if (index >= 0) {
                Account acct = getAccount(trs.getAccountID());

                if (trs.validate(acct)) {
                    trs.execute(acct, audit);
                } else {
                    audit.write(acct, "Transaction failed validation: " + line, Audit.EntryType.ERROR);
                }
            } else {
                // account not found
                audit.write("Transaction for unknown account ID: " + trs.getAccountID());
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Error: Could not open file " + filename);
    }
}

    }
    