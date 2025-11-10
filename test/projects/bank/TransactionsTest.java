package projects.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionsTest {

    @Test
    public void testConstructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Withdrawal("xf123", -50.0);
       });
       assertThrows(IllegalArgumentException.class, () -> {
            new Withdrawal("xf123", 0.0);
       });
       assertThrows(NullPointerException.class, () -> {
           new Deposit(null, 100.0);
       });
       assertThrows(IllegalArgumentException.class, () -> {
           new Deposit("bad id", 100.0);
       });
    }

    @Test
    public void testFactoryCreatesCorrectTransaction() {
    Transaction t = Transaction.create("deposit,xf123,200.0");
    assertTrue(t instanceof Deposit);
    assertEquals("xf123", t.getAccountID());
    assertEquals(200.0, t.getAmount(), 1e-9);
    }

    @Test
    public void testFactoryRejectsBadInput() {
    assertThrows(IllegalArgumentException.class, () -> Transaction.create("bad,data"));
    }

    @Test
    public void testWithdrawalValidationFailsForLowFunds() {
    Account acc = new CheckingAccount("xf123", "Lorenzo", 50.0);
    Withdrawal w = new Withdrawal("xf123", 100.0);
    assertFalse(w.validate(acc));
    }

    @Test
     void testWithdrawalValidationSucceedsWhenEnoughFunds() {
    Account acc = new CheckingAccount("xf123", "Lorenzo", 200.0);
    Withdrawal w = new Withdrawal("xf123", 100.0);
    assertTrue(w.validate(acc));
    }

    @Test
    public void testDepositExecuteAddsToBalance() {
    Account acc = new CheckingAccount("xf123", "Lorenzo", 100.0);
    Deposit d = new Deposit("xf123", 50.0);
    Audit audit = new Audit();
    audit.initialize("data/testAudit.csv"); 

    d.execute(acc, audit);
    assertEquals(150.0, acc.getBalance(), 1e-9); 

    audit.close();
    }

    @Test
    public void testWithdrawalExecuteSubtractsFromBalance() {
    Account acc = new CheckingAccount("xf123", "Lorenzo", 200.0);
    Withdrawal w = new Withdrawal("xf123", 50.0);
    Audit audit = new Audit();
    audit.initialize("data/testAudit.csv");

    w.execute(acc, audit);
    assertEquals(150.0, acc.getBalance(), 1e-9); 
    audit.close();
    }
}
