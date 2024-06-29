package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.user.User;

class SocialFriendRequestTest {
    private SocialFriendRequest friendRequest;
    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        friendRequest = new SocialFriendRequest();
        sender = new User();  // Assume User class has a default constructor
        receiver = new User();  // Assume User class has a default constructor
    }

    @Test
    void testGettersAndSetters() {
        // Test ID
        Long idValue = 1L;
        friendRequest.setId(idValue);
        assertEquals(idValue, friendRequest.getId(), "ID should match the set value");

        // Test sender and receiver
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        assertEquals(sender, friendRequest.getSender(), "Sender should match the set value");
        assertEquals(receiver, friendRequest.getReceiver(), "Receiver should match the set value");

        // Test status
        friendRequest.setStatus(FriendStatus.PENDING);
        assertEquals(FriendStatus.PENDING, friendRequest.getStatus(), "Status should match the set value");

        // Test timestamp
        LocalDateTime now = LocalDateTime.now();
        friendRequest.setTimestamp(now);
        assertEquals(now, friendRequest.getTimestamp(), "Timestamp should match the set value");
    }

    @Test
    void testInitialValues() {
        assertNull(friendRequest.getId(), "ID should initially be null");
        assertNull(friendRequest.getSender(), "Sender should initially be null");
        assertNull(friendRequest.getReceiver(), "Receiver should initially be null");
        assertNull(friendRequest.getStatus(), "Status should initially be null");
        assertNull(friendRequest.getTimestamp(), "Timestamp should initially be null");
    }
}
