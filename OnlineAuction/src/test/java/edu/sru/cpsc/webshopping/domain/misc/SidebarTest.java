package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.sidebar.Sidebar;

class SidebarTest {
    private Sidebar sidebar;

    @BeforeEach
    void setUp() {
        sidebar = new Sidebar();
    }

    @Test
    void testInitialValues() {
        assertNull(sidebar.getTabID(), "Initial tab ID should be null");
        assertNull(sidebar.getDisplayText(), "Initial display text should be null");
        assertNull(sidebar.getLink(), "Initial link should be null");
        assertNull(sidebar.getRole(), "Initial role should be null");
    }

    @Test
    void testSettersAndGetters() {
        Long expectedTabID = 1L;
        String expectedDisplayText = "Home";
        String expectedLink = "/home";
        String expectedRole = "USER";

        sidebar.setTabID(expectedTabID);
        sidebar.setDisplayText(expectedDisplayText);
        sidebar.setLink(expectedLink);
        sidebar.setRole(expectedRole);

        assertEquals(expectedTabID, sidebar.getTabID(), "Tab ID should match the set value");
        assertEquals(expectedDisplayText, sidebar.getDisplayText(), "Display text should match the set value");
        assertEquals(expectedLink, sidebar.getLink(), "Link should match the set value");
        assertEquals(expectedRole, sidebar.getRole(), "Role should match the set value");
    }
}
