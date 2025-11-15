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
        audit = new Audit();
        audit.initialize("audit.csv");
    }

    /** Adds an account, resizing array if needed. */
    public boolean addAccount(Account acc) {
        if (count == accounts.length) {
            Account[] bigger = new Account[accounts.length * 2];
            System.arraycopy(accounts, 0, bigger, 0, accounts.length);
            accounts = bigger;
        }
        accounts[count++] = acc;
        return true;
    }

    /** Retrieves an account by ID. */
    public Account getAccount(String accountId) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(accountId)) {
                return accounts[i];
            }
        }
        return null;
    }

    /** Returns the index of the given account ID, or -1 if missing. */
    public int indexOfAccount(String accountId) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(accountId)) {
                return i;
            }
        }
        return -1;
    }

    public int getCount() {
        return count;
    }

    /** Retrieves an account by owner name. */
    public Account getAccountByName(String accountName) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountName().equals(accountName)) {
                return accounts[i];
            }
        }
        return null;
    }

    /**
     * Loads accounts from a CSV file.
     */
    public boolean loadAccounts(String filename) {

        try (Scanner input = new Scanner(new File(filename))) {

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    Account acc = Account.fromCSV(line);
                    addAccount(acc);
                }
            }
            return true;

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
            return false;
        }
    }

    /**
     * Saves all accounts to a CSV file.
     */
    public boolean writeAccounts(String filename) {

        try (FileWriter writer = new FileWriter(new File(filename))) {

            for (int i = 0; i < count; i++) {
                writer.write(accounts[i].toCSV() + "\n");
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Processes each transaction in the transaction file,
     * then performs end-of-month actions.
     */
    public void processTransactions(String filename) {

        try (Scanner input = new Scanner(new File(filename))) {

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.isEmpty())
                    continue;

                Transaction trs = Transaction.create(line);
                Account acct = getAccount(trs.getAccountID());

                if (acct == null) {
                    audit.write("Transaction for unknown account ID: " + trs.getAccountID());
                    continue;
                }

                if (trs.validate(acct)) {
                    trs.execute(acct, audit);
                } else {
                    audit.write(acct,
                        "Transaction failed validation: " + line,
                        Audit.EntryType.ERROR
                    );
                }
            }

            // End-of-month actions
            for (int i = 0; i < count; i++) {
                accounts[i].doEndOfMonthActions(audit);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not open file " + filename);
        }
    }
}
