package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FriendStatusTest {

    @Test
    void testEnumValues() {
        // Ensure all enum values are present
        FriendStatus[] statuses = FriendStatus.values();
        assertEquals(3, statuses.length, "There should be exactly three statuses.");

        // Verify each enum value and their order
        assertEquals(FriendStatus.PENDING, FriendStatus.values()[0], "PENDING should be at index 0");
        assertEquals(FriendStatus.ACCEPTED, FriendStatus.values()[1], "ACCEPTED should be at index 1");
        assertEquals(FriendStatus.DECLINED, FriendStatus.values()[2], "DECLINED should be at index 2");
    }
}
