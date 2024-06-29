package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

    @Test
    void testDefaultValues() {
        assertNull(notification.getId(), "ID should be null by default");
        assertNull(notification.getUserId(), "User ID should be null by default");
        assertNull(notification.getMessage(), "Message should be null by default");
        assertFalse(notification.getIsRead(), "isRead should be false by default");
        assertNull(notification.getCreatedAt(), "CreatedAt should be null by default");
    }

    @Test
    void testConstructorWithParameters() {
        String expectedMessage = "Hello, you have an update!";
        Long expectedUserId = 1L;
        Notification customNotification = new Notification(expectedUserId, expectedMessage);

        assertEquals(expectedUserId, customNotification.getUserId(), "User ID should match constructor input");
        assertEquals(expectedMessage, customNotification.getMessage(), "Message should match constructor input");
        assertNotNull(customNotification.getCreatedAt(), "CreatedAt should not be null when set in constructor");
    }

    @Test
    void testSetters() {
        Long expectedId = 2L;
        Long expectedUserId = 3L;
        String expectedMessage = "Your request has been processed.";
        boolean expectedIsRead = true;
        LocalDateTime expectedCreatedAt = LocalDateTime.now();

        notification.setId(expectedId);
        notification.setUserId(expectedUserId);
        notification.setMessage(expectedMessage);
        notification.setIsRead(expectedIsRead);
        notification.setCreatedAt(expectedCreatedAt);

        assertEquals(expectedId, notification.getId(), "ID should match the set value");
        assertEquals(expectedUserId, notification.getUserId(), "User ID should match the set value");
        assertEquals(expectedMessage, notification.getMessage(), "Message should match the set value");
        assertEquals(expectedIsRead, notification.getIsRead(), "IsRead should match the set value");
        assertEquals(expectedCreatedAt, notification.getCreatedAt(), "CreatedAt should match the set value");
    }
}
