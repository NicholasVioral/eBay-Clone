package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.user.User;

public class GroupTest {
    private Group group;

    @Mock
    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        group = new Group("Tech Enthusiasts", "A group for technology lovers");
    }

    @Test
    void testAddMember() {
        assertTrue(group.getMembers().isEmpty(), "Members set should be initially empty");

        group.addMember(mockUser);
        assertTrue(group.getMembers().contains(mockUser), "Members set should contain the added user");
    }

    @Test
    void testRemoveMember() {
        group.addMember(mockUser);
        assertTrue(group.getMembers().contains(mockUser), "Members set should contain the added user");

        group.removeMember(mockUser);
        assertFalse(group.getMembers().contains(mockUser), "Members set should no longer contain the removed user");
    }

    @Test
    void testSetProperties() {
        group.setGroupImage("path/to/image.jpg");
        assertEquals("path/to/image.jpg", group.getGroupImage(), "Group image path should be set correctly");

        group.setStatus("active");
        assertEquals("active", group.getStatus(), "Status should be set correctly");
    }
}
