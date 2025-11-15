package projects.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WithdrawalBehaviorTest {

    @Test
    public void testNSFPenaltyApplied() {
        Account acc = new CheckingAccount("xf123", "Lorenzo", 20.0);
        Withdrawal w = new Withdrawal("xf123", 100.0);

        Audit audit = new Audit();
        audit.initialize("data/testAudit.csv");

        w.execute(acc, audit);

        assertEquals(10.0, acc.getBalance(), 1e-9); // 20 - 10 penalty
        audit.close();
    }

    @Test
    public void testSavingsFraudDenied() {
        SavingsAccount acc = new SavingsAccount("sa123", "Alice", 5000.0);
        Withdrawal w = new Withdrawal("sa123", 3000.00);

        Audit audit = new Audit();
        audit.initialize("data/testAudit.csv");

        w.execute(acc, audit);

        // Balance unchanged
        assertEquals(5000.0, acc.getBalance(), 1e-9);
        audit.close();
    }

    @Test
    public void testSavingsWithdrawalLimitDenied() {
        SavingsAccount acc = new SavingsAccount("sa123", "Alice", 5000.0);

        // Fill up withdrawals
        for (int i = 0; i < 6; i++) {
            acc.recordWithdrawal();
        }

        Withdrawal w = new Withdrawal("sa123", 50.0);
        Audit audit = new Audit();
        audit.initialize("data/testAudit.csv");

        w.execute(acc, audit);

        // No withdrawal, no penalty
        assertEquals(5000.0, acc.getBalance(), 1e-9);
        audit.close();
    }

    @Test
    public void testSavingsWithdrawalIncrementsCounter() {
        SavingsAccount acc = new SavingsAccount("sa123", "Alice", 5000.0);
        Withdrawal w = new Withdrawal("sa123", 50.00);

        Audit audit = new Audit();
        audit.initialize("data/testAudit.csv");

        w.execute(acc, audit);

        assertEquals(1, acc.getWithdrawalsThisMonth());
        audit.close();
    }
}
