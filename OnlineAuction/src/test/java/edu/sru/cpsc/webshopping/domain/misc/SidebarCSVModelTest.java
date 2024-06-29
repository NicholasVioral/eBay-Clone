package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.sidebar.SidebarCSVModel;

class SidebarCSVModelTest {
    private SidebarCSVModel sidebar;

    @BeforeEach
    void setUp() {
        sidebar = new SidebarCSVModel();
    }

    @Test
    void testSettersAndGetters() {
        long expectedTabId = 100L;
        String expectedDisplayText = "Dashboard";
        String expectedLinkTo = "/dashboard";
        String expectedRole = "ADMIN";

        sidebar.setTabid(expectedTabId);
        sidebar.setDisplayText(expectedDisplayText);
        sidebar.setLinkTo(expectedLinkTo);
        sidebar.setRole(expectedRole);

        assertEquals(expectedTabId, sidebar.getTabid(), "Tab ID should match the set value");
        assertEquals(expectedDisplayText, sidebar.getDisplayText(), "Display text should match the set value");
        assertEquals(expectedLinkTo, sidebar.getLinkTo(), "Link to should match the set value");
        assertEquals(expectedRole, sidebar.getRole(), "Role should match the set value");
    }
}
