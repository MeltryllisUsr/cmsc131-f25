import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import projects.bank.Account;
import projects.bank.Bank;

public class BankTest {

    @Test
    public void testAddAccount() {
        Bank bank = new Bank();
        Account acc = new Account("checking", "xf123", "Lorenzo", 1000.0);
        assertTrue(bank.addAccount(acc));
        assertEquals(1, bank.getCount());
    }
    @Test
    public void testLoadAccount(){
        Bank bank = new Bank();
        Account acc = new Account("checking", "xf123", "Lorenzo", 1000.0);
        bank.addAccount(acc);
        try{
            bank.loadAccounts("data/testAccounts.csv");
        } catch (Exception e){
            fail("Exception thrown: " + e.getMessage());
        }
        assertEquals(1, bank.getCount());
        Account loadedAcc = bank.getAccountByName("Lorenzo");
        assertNotNull(loadedAcc);
        assertEquals("xf123", loadedAcc.getAccountId());
        assertEquals(1000.0, loadedAcc.getBalance(), 1e-9);
        }


   
}