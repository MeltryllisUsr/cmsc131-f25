package examples.csvio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// proxy for your enum AccountType, defined for bank phase 1
enum WhateverEnum {
    CHECKING, SAVINGS
};


public class CsvParsing {

    /**
     * Demonstration of reading from / writing to a CSV file.
     * @param args - Default parameter; not used.
     */
    public static void main(String[] args) {

        // absolute path starts a filesystem root "/"
        // ok to use posix b/c dev container runs linux
        String accountsFilename = "/workspaces/cmsc131-f25/data/accounts.csv";
        String outputFilename = "/workspaces/cmsc131-f25/data/20250929.csv";

        openAndParseCsv(accountsFilename);
        writeDataToCsv(outputFilename);
        
    } // end: main

    /**
     * Demonstration of the steps to read and parse a CSV file.
     * 1. Open file
     * 2. Read file line-by-line using `Scanner` object
     * 3. Parse each line into tokens
     * 4. Create a parameter for Account constructor out of each token
     * 5. Create new Account object from those parameters
     * 
     * @param filename - Posix path to input CSV file.
     */
    public static void openAndParseCsv(String filename) {
        File accountsFile = new File(filename);
        
        Scanner scan; // used to read accountsFile line-by-line
        
        try{ // `new Scanner` might throw FileNotFoundException
            scan = new Scanner(accountsFile);
            while (scan.hasNextLine()) {
                String curLine = scan.nextLine();
                // ok to assume tokens are 
                //    0: accountType (must be parsed into enum value)
                //    1: id 
                //    2: ownerName
                String[] tokens = curLine.split(","); // object method
                WhateverEnum accountType = WhateverEnum.valueOf(
                    tokens[0].toUpperCase()
                );
                System.out.println("breakpoint");  // for single-stepping
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace(); // report error and move on
        }
    } // end: openAndParseCsv

    /**
     * Demonstration of the steps to write data to a CSV file
     * 1. Iterate through Accounts in Bank
     * 2. Convert info for each Account into CSV line
     * 3. Write CSV line to CSV file
     * 
     * @param filename - Posix path to output CSV file.
     */
    public static void writeDataToCsv(String filename) {
        File file = new File(filename);
        FileWriter writer;
        try {  // new FileWriter might throw IOException
            writer = new FileWriter(file);

            // pretend this loop goes over Accounts in Bank
            for (int idx = 0; idx < 10; idx++) {
                
                String id = "id" + String.valueOf(idx);
                
                // pretend name was accessed from account
                String ownerName = "Augusta Ada Lovelace";
                
                // pretend accountType was accessed from Account
                WhateverEnum accountType = WhateverEnum.CHECKING;
                String type = accountType.name().toLowerCase();

                String[] tokens = {type, id, ownerName};
                String csvLine = String.join(",", tokens); // static method
                System.out.println("breakpoint");  // for single-stepping
                writer.write(csvLine + System.lineSeparator());
            }

            writer.close();
        } catch(IOException e) {
            e.printStackTrace(); // report error and move on
        }
    } // end: writeDataToCsv

} // end: class CsvParsing
