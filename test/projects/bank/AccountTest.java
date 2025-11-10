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
        assertEquals(AccountType.CHECKING, acc.getType());
        assertEquals("AC123", acc.getAccountId());
        assertEquals("Lorenzo", acc.getAccountName());
        assertEquals(1000.5, acc.getBalance(), 1e-9);
    }

    @Test
    public void testFromCSV_invalidBalanceThrows() {
        assertThrows(NumberFormatException.class, () -> Account.fromCSV("savings,AC456,Alice,notanumber"));
    }

    @Test
    public void testFromCSV_invalidTypeThrows() {
        assertThrows(IllegalArgumentException.class, () -> Account.fromCSV("invalidtype,AC789,Bob,500.0"));
    }

    @Test
    public void testToCSV_outputsCorrectFormat() {
        Account acc = new CheckingAccount("AC789", "Dana", 250.75);
        String csv = acc.toCSV();
        assertEquals("CHECKING,AC789,Dana,250.75", csv);
    }

    @Test
    public void testCredit() {
        Account acc = new SavingsAccount("AC789", "Dana", 250.75);
        acc.credit(99.25);
        assertEquals(350.0, acc.getBalance(), 1e-9);
    }

     @Test
    public void testDebit() {
        Account acc = new SavingsAccount("AC789", "Dana", 250.75);
        acc.debit(100.75);
        assertEquals(150.0, acc.getBalance(), 1e-9);
    }
}