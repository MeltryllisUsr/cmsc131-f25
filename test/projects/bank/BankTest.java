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
        assertEquals(1000.0, loadedAcc.getBalance(), 1e-9);
        assertEquals("Lorenzo", loadedAcc.getAccountName());
        assertEquals("xf123", loadedAcc.getAccountId());
        assertEquals(AccountType.CHECKING, loadedAcc.getType());
        }
     
    @Test
    public void testWriteAccounts(){
        Bank bank = new Bank();
        Account acc = new CheckingAccount("xf123", "Lorenzo", 1000.0);
        bank.addAccount(acc);
        try{
            bank.writeAccounts("data/testAccounts.csv");
        } catch (Exception e){
            fail("Exception thrown: " + e.getMessage());
        }
        assertEquals(1, bank.getCount());
        Account writtenAcc = bank.getAccountByName("Lorenzo");
        assertNotNull(writtenAcc);
        assertEquals(1000.0, writtenAcc.getBalance(), 1e-9);
        assertEquals("Lorenzo", writtenAcc.getAccountName());
        assertEquals("xf123", writtenAcc.getAccountId());
        assertEquals(AccountType.CHECKING, writtenAcc.getType());
    }    

    @Test
    public void testLoadAccountsReturnsTrue() {
        Bank bank = new Bank();
        boolean result = bank.loadAccounts("data/testAccounts.csv");
        assertTrue(result);
    }

    @Test
    public void testWriteAccountsReturnsTrue() {
        Bank bank = new Bank();
        boolean result = bank.writeAccounts("data/testAccounts.csv");
        assertTrue(result);
    }
}


   
