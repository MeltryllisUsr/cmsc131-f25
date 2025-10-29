package projects.bank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        phase1();
        phase2();
        Bank bank = new Bank();
    bank.loadAccounts( "accounts.txt" );
    bank.processTransactions( "transactions.txt" );
    bank.writeAccounts( "accounts.end.txt " );
    }

    public static void phase1() {
        String logName = "phase1.log";
        try {
            FileWriter writer = new FileWriter(new File(logName));

            String acctId = "id1";
            String acctOwner = "Owner Name";
            double acctBalance = 1.0;
            
            writer.write(
                String.format(
                    "Account setup: acct id=%s, owner=%s, balance=%f",
                    acctId,
                    acctOwner,
                    acctBalance
                ) + System.lineSeparator()
            );

            Bank bank = new Bank();
            int numAccounts0 = bank.getCount();
            Account findAcct0 = bank.getAccount(acctId);

            boolean addResult = false; // skipping addAccount since Account constructor is not available here
            int numAccounts1 = numAccounts0;
            Account findAcct1 = findAcct0;

            writer.write(
                String.format(
                    "Bank init: getCount=%d, find=%s",
                    numAccounts0, 
                    findAcct0
                ) + System.lineSeparator()
            );

            writer.write(
                String.format(
                    "After add: result=%b, getCount=%d, find=%s",
                    addResult,
                    numAccounts1, 
                    findAcct1
                ) + System.lineSeparator()
            );

            writer.close();
        } catch (IOException e) { e.printStackTrace(); }

    }

    public static void phase2() {
        String accountsFilename = "data/accounts.csv";
        Bank bank = new Bank();
        boolean result = bank.loadAccounts(accountsFilename); // TODO convert from void to boolean

        System.out.println("Result of loading account: " + result);
        System.out.println("Number of accounts: " + bank.getCount());

        String outputFilename = "data/phase2.csv";
        bank.writeAccounts(outputFilename);
    }
}