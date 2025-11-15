package projects.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckingAccountTest {

    @Test
    public void testCheckingPenaltyApplied() {
        CheckingAccount acc = new CheckingAccount("ca123", "Bob", 50.0);

        Audit audit = new Audit();
        audit.initialize("data/testAudit.csv");

        acc.doEndOfMonthActions(audit);
        audit.close();

        assertEquals(40.0, acc.getBalance(), 1e-9); // 50 - 10 penalty
    }

    @Test
    public void testCheckingNoPenaltyWhenAboveMin() {
        CheckingAccount acc = new CheckingAccount("ca123", "Bob", 200.0);

        Audit audit = new Audit();
        audit.initialize("data/testAudit.csv");

        acc.doEndOfMonthActions(audit);
        audit.close();

        assertEquals(200.0, acc.getBalance(), 1e-9);
    }
}
