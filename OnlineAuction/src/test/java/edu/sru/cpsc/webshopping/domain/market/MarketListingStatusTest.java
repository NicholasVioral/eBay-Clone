package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MarketListingStatusTest {

    @Test
    public void testEnumValues() {
        // Check that all expected values are present
        MarketListingStatus[] statuses = MarketListingStatus.values();
        assertEquals(6, statuses.length, "There should be exactly six statuses.");
        
        // Verify each enum value
        assertTrue(contains(statuses, MarketListingStatus.ACTIVE), "Enum should contain ACTIVE");
        assertTrue(contains(statuses, MarketListingStatus.EXPIRED), "Enum should contain EXPIRED");
        assertTrue(contains(statuses, MarketListingStatus.INACTIVE), "Enum should contain INACTIVE");
        assertTrue(contains(statuses, MarketListingStatus.PENDING), "Enum should contain PENDING");
        assertTrue(contains(statuses, MarketListingStatus.SOLD), "Enum should contain SOLD");
        assertTrue(contains(statuses, MarketListingStatus.CLOSED), "Enum should contain CLOSED");
    }

    @Test
    public void testEnumOrder() {
        // Verify the order of enum constants
        assertEquals(MarketListingStatus.ACTIVE, MarketListingStatus.values()[0], "ACTIVE should be at index 0");
        assertEquals(MarketListingStatus.EXPIRED, MarketListingStatus.values()[1], "EXPIRED should be at index 1");
        assertEquals(MarketListingStatus.INACTIVE, MarketListingStatus.values()[2], "INACTIVE should be at index 2");
        assertEquals(MarketListingStatus.PENDING, MarketListingStatus.values()[3], "PENDING should be at index 3");
        assertEquals(MarketListingStatus.SOLD, MarketListingStatus.values()[4], "SOLD should be at index 4");
        assertEquals(MarketListingStatus.CLOSED, MarketListingStatus.values()[5], "CLOSED should be at index 5");
    }

    private boolean contains(MarketListingStatus[] array, MarketListingStatus value) {
        for (MarketListingStatus status : array) {
            if (status == value) {
                return true;
            }
        }
        return false;
    }
}
