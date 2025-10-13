import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import projects.bank.Account;

public class AccountTest {

    @Test
    public void testGetBalance() {
        Account acc = new Account("checking", "xf123", "Lorenzo", 1000.0);
        assertEquals(1000.0, acc.getBalance(), 1e-9);
    }

    @Test
    public void testFromCSV_parsesFields() {
        Account acc = Account.fromCSV("checking,AC123,Lorenzo,1000.5");
        assertEquals("checking", acc.getAccountType());
        assertEquals("AC123", acc.getAccountId());
        assertEquals("Lorenzo", acc.getAccountName());
        assertEquals(1000.5, acc.getBalance(), 1e-9);
    }

    @Test
    public void testFromCSV_invalidBalanceThrows() {
        assertThrows(NumberFormatException.class, () -> Account.fromCSV("savings,AC456,Alice,notanumber"));
    }

}