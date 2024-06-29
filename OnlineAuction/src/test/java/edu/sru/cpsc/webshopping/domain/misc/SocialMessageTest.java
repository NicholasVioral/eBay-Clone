package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.user.User;

class SocialMessageTest {

    private SocialMessage socialMessage;
    private User sender;
    private User receiver;
    private Group group;

    @BeforeEach
    void setUp() {
        socialMessage = new SocialMessage();
        sender = new User();  // Assume User class has a default constructor
        receiver = new User();  // Assume User class has a default constructor
        group = new Group();  // Assume Group class has a default constructor
    }

    @Test
    void testInitialValues() {
        assertNull(socialMessage.getId(), "ID should initially be null");
        assertNull(socialMessage.getSender(), "Sender should initially be null");
        assertNull(socialMessage.getReceiver(), "Receiver should initially be null");
        assertNull(socialMessage.getContent(), "Content should initially be null");
        assertNotNull(socialMessage.getSentTimestamp(), "SentTimestamp should be initialized but not null");
        assertNull(socialMessage.getReadTimestamp(), "ReadTimestamp should initially be null");
        assertFalse(socialMessage.isDelivered(), "isDelivered should be false initially");
        assertFalse(socialMessage.isRead(), "isRead should be false initially");
    }

    @Test
    void testSetters() {
        socialMessage.setId(1L);
        socialMessage.setSender(sender);
        socialMessage.setReceiver(receiver);
        socialMessage.setContent("Hello, this is a test message!");
        socialMessage.setSentTimestamp(LocalDateTime.now());
        socialMessage.setReadTimestamp(LocalDateTime.now());
        socialMessage.setDelivered(true);
        socialMessage.setRead(true);
        socialMessage.setGroup(group);

        assertEquals(1L, socialMessage.getId());
        assertEquals(sender, socialMessage.getSender());
        assertEquals(receiver, socialMessage.getReceiver());
        assertEquals("Hello, this is a test message!", socialMessage.getContent());
        assertNotNull(socialMessage.getSentTimestamp());
        assertNotNull(socialMessage.getReadTimestamp());
        assertTrue(socialMessage.isDelivered());
        assertTrue(socialMessage.isRead());
        assertEquals(group, socialMessage.getGroup());
    }
}
