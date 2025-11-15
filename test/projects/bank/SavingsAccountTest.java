package projects.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {

    @Test
    public void testSavingsAccountWithdrawalLimit() {
        SavingsAccount acc = new SavingsAccount("sa123", "Alice", 5000.0);

        // Simulate 6 withdrawals already done
        for (int i = 0; i < 6; i++) {
            acc.recordWithdrawal();
        }

        // Should now fail
        assertFalse(acc.canWithdraw(50.0));
        assertEquals(6, acc.getWithdrawalsThisMonth());
    }

    @Test
    public void testSavingsFraudThreshold() {
        SavingsAccount acc = new SavingsAccount("sa123", "Alice", 5000.0);

        assertFalse(acc.canWithdraw(3000.0));   // too large
        assertTrue(acc.canWithdraw(2499.99));  // allowed
    }

    @Test
    public void testSavingsRecordWithdrawal() {
        SavingsAccount acc = new SavingsAccount("sa123", "Alice", 5000.0);

        assertEquals(0, acc.getWithdrawalsThisMonth());
        acc.recordWithdrawal();
        assertEquals(1, acc.getWithdrawalsThisMonth());
    }

    @Test
    public void testSavingsMonthlyReset() {
        SavingsAccount acc = new SavingsAccount("sa123", "Alice", 5000.0);

        acc.recordWithdrawal();
        acc.recordWithdrawal();
        assertEquals(2, acc.getWithdrawalsThisMonth());

        // Apply end of month
        Audit audit = new Audit();
        audit.initialize("data/testAudit.csv");
        acc.doEndOfMonthActions(audit);
        audit.close();

        assertEquals(0, acc.getWithdrawalsThisMonth());
    }

    @Test
    public void testSavingsInterestApplied() {
        SavingsAccount acc = new SavingsAccount("sa123", "Alice", 1000.0);

        Audit audit = new Audit();
        audit.initialize("data/testAudit.csv");

        acc.doEndOfMonthActions(audit);
        audit.close();

        assertEquals(1010.0, acc.getBalance(), 1e-9); // 1% interest
    }
}
