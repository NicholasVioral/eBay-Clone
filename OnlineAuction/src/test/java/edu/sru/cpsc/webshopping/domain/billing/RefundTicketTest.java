package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.util.enums.TicketState;

public class RefundTicketTest {

    @Test
    void testIsResolved_WhenStateIsResolved() {
        RefundTicket ticket = new RefundTicket();
        ticket.setState(TicketState.RESOLVED);

        assertTrue(ticket.isResolved());
    }

    @Test
    void testIsResolved_WhenStateIsNotResolved() {
        RefundTicket ticket = new RefundTicket();
        ticket.setState(TicketState.PENDING);

        assertFalse(ticket.isResolved());
    }

    @Test
    void testTransactionAssignment() {
        RefundTicket ticket = new RefundTicket();
        Transaction transaction = new Transaction();

        long expectedId = 1L;
        transaction.setId(expectedId);
        ticket.setTransaction(transaction);

        // First, check if the transaction ID directly fetched is what we expect.
        long actualId = ticket.getTransaction().getId();
        assertEquals(expectedId, actualId, "Transaction ID should match the set value");

        // Now verify using assertTrue for a boolean condition
        assertTrue(actualId == expectedId, "Transaction should be correctly set and retrieved.");
    }
}
