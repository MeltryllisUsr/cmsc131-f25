package projects.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    public void testGetBalance() {
        Account acc = new CheckingAccount("xf123", "Lorenzo", 1000.0);
        assertEquals(1000.0, acc.getBalance(), 1e-9);
    }

    @Test
    public void testFromCSV_parsesFields() {
        Account acc = Account.fromCSV("checking,AC123,Lorenzo,1000.5");
        assertEquals("checking", acc.getType());
        assertEquals("AC123", acc.getAccountId());
        assertEquals("Lorenzo", acc.getAccountName());
        assertEquals(1000.5, acc.getBalance(), 1e-9);
    }

    @Test
    public void testFromCSV_invalidBalanceThrows() {
        assertThrows(NumberFormatException.class, () -> Account.fromCSV("savings,AC456,Alice,notanumber"));
    }

    // TODO add tests for 
    // static factory throws on null input
    // toCSV output is correct

}