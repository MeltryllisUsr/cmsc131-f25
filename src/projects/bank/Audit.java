package projects.bank;
import java.io.FileWriter;
import java.io.IOException;

public class Audit {
    public enum EntryType { INFO, ERROR }
    public void write(String text) {
        text = text.replace(",", "");
        try {
            writer.write(text + System.lineSeparator());
        }
        catch (IOException e) {
            System.out.println("Error in writing to audit file.");
        }
    }
    public void write(Account acct, String text, EntryType type) {
        String line = String.format(
            "%s, AccountID: %s, Name: %s, %s",
            type, acct.getAccountId(), acct.getAccountName(), text
        );
        write(line);
    }
    public boolean initialize(String filename) {
        try {
            writer = new FileWriter(filename);
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }
    public void close() {
        try {
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Error in closing audit file.");
        }
    }
    private FileWriter writer;
}
