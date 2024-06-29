package edu.sru.cpsc.webshopping.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactUsTicketTest {
    private ContactUsTicket ticket;

    @BeforeEach
    void setUp() {
        ticket = new ContactUsTicket();
    }

    @Test
    void testInitialValues() {
        assertNull(ticket.getId(), "ID should be null initially");
        assertNull(ticket.getFirstName(), "First name should be null initially");
        assertNull(ticket.getLastName(), "Last name should be null initially");
        assertNull(ticket.getEmail(), "Email should be null initially");
        assertNull(ticket.getMessage(), "Message should be null initially");
        assertFalse(ticket.isProcessed(), "Processed should be false initially");
    }

    @Test
    void testSettersAndGetters() {
        Long expectedId = 1L;
        String expectedFirstName = "John";
        String expectedLastName = "Doe";
        String expectedEmail = "john.doe@example.com";
        String expectedMessage = "Need help with an order";
        boolean expectedProcessed = true;

        ticket.setId(expectedId);
        ticket.setFirstName(expectedFirstName);
        ticket.setLastName(expectedLastName);
        ticket.setEmail(expectedEmail);
        ticket.setMessage(expectedMessage);
        ticket.setProcessed(expectedProcessed);

        assertEquals(expectedId, ticket.getId(), "ID should match the set value");
        assertEquals(expectedFirstName, ticket.getFirstName(), "First name should match the set value");
        assertEquals(expectedLastName, ticket.getLastName(), "Last name should match the set value");
        assertEquals(expectedEmail, ticket.getEmail(), "Email should match the set value");
        assertEquals(expectedMessage, ticket.getMessage(), "Message should match the set value");
        assertEquals(expectedProcessed, ticket.isProcessed(), "Processed status should match the set value");
    }
}
