package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RevenueTest {
    Revenue revenue;

    @BeforeEach
    void setUp() {
        // Assuming you can modify the Revenue class to include a reset method
        revenue = Revenue.getInstance();
        revenue.setRevenue(0); // Resetting revenue to 0 before each test
    }

    @Test
    void testSingletonInstance() {
        Revenue anotherInstance = Revenue.getInstance();
        assertSame(revenue, anotherInstance, "Both variables should refer to the same instance");
    }

    @Test
    void testSetAndGetRevenue() {
        revenue.setRevenue(100.0f);
        assertEquals(100.0f, revenue.getRevenue(), "Revenue should be exactly what was set");
    }

    @Test
    void testAddRevenue() {
        revenue.setRevenue(100.0f);
        revenue.addRevenue(50.0f);
        assertEquals(150.0f, revenue.getRevenue(), "Revenue should be correctly added");
    }

    @Test
    void testRemoveRevenue() {
        revenue.setRevenue(200.0f);
        revenue.removeRevenue(75.0f);
        assertEquals(125.0f, revenue.getRevenue(), "Revenue should be correctly subtracted");
    }
}
