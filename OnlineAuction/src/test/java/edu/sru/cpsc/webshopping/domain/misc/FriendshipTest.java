package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.user.User;

class FriendshipTest {
    private Friendship friendship;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        friendship = new Friendship();
        user1 = new User(); // Assume User class has a default constructor
        user2 = new User(); // Assume User class has a default constructor
        
        // Assuming User class has setId method for demonstration
        user1.setId(1L);
        user2.setId(2L);
    }

    @Test
    void testFriendshipSettersAndGetters() {
        // Test setting and getting ID
        friendship.setId(100L);
        assertEquals(100L, friendship.getId(), "IDs should match");

        // Test setting and getting user1
        friendship.setUser1(user1);
        assertNotNull(friendship.getUser1(), "User1 should not be null");
        assertEquals(user1, friendship.getUser1(), "User1 should match the set user");

        // Test setting and getting user2
        friendship.setUser2(user2);
        assertNotNull(friendship.getUser2(), "User2 should not be null");
        assertEquals(user2, friendship.getUser2(), "User2 should match the set user");
    }

    @Test
    void testInitialValues() {
        // Test the initial state of an unmodified Friendship object
        assertNull(friendship.getId(), "ID should be null initially");
        assertNull(friendship.getUser1(), "User1 should be null initially");
        assertNull(friendship.getUser2(), "User2 should be null initially");
    }
}
