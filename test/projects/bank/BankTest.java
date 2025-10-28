package projects.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    @Test
    public void testAddAccount() {
        Bank bank = new Bank();
        Account acc = new CheckingAccount("xf123", "Lorenzo", 1000.0);
        assertTrue(bank.addAccount(acc));
        assertEquals(1, bank.getCount());
    }
    @Test
    public void testLoadAccount(){
        Bank bank = new Bank();
        Account acc = new CheckingAccount("xf123", "Lorenzo", 1000.0);
        bank.addAccount(acc);
        try{
            bank.loadAccounts("data/testAccounts.csv");
        } catch (Exception e){
            fail("Exception thrown: " + e.getMessage());
        }
        
        assertEquals(1+4, bank.getCount());
        
        Account loadedAcc = bank.getAccountByName("Lorenzo");
        assertNotNull(loadedAcc);
        assertEquals("xf123", loadedAcc.getAccountId());
        assertEquals(1000.0, loadedAcc.getBalance(), 1e-9);

        // TODO test loaded account has expected attributers
        }

    // TODO add tests for
    // loadAccounts preserves data
    // loadAccounts returns true on succeed
    // writeAccounts failure mode
    // writeAccounts preserves data
    // writeAccounts returns true on succeed


   
}