package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OfferNotificationTest {
    private OfferNotification offer;

    @BeforeEach
    void setUp() {
        offer = new OfferNotification("buyerUser", 1L, "500", 2L);
    }

    @Test
    void testConstructor() {
        assertNotNull(offer.getCreatedOn(), "CreatedOn should be initialized");
        assertEquals("buyerUser", offer.getPotentialBuyerUserName(), "Username should match constructor argument");
        assertEquals(1L, offer.getPotentialBuyerUserId(), "User ID should match constructor argument");
        assertEquals("500", offer.getOfferAmount(), "Offer amount should match constructor argument");
        assertEquals(2L, offer.getMarketListingId(), "Market listing ID should match constructor argument");
    }

    @Test
    void testAcceptOffer() {
        assertNull(offer.getAcceptedOn(), "Initially, acceptedOn should be null");
        assertFalse(offer.isAccepted(), "Offer should not be initially accepted");

        offer.setAccepted(true);
        offer.setAcceptedOn(new Date());

        assertTrue(offer.isAccepted(), "Offer should be marked as accepted");
        assertNotNull(offer.getAcceptedOn(), "AcceptedOn should be set after acceptance");
    }

    @Test
    void testRejectOffer() {
        assertFalse(offer.isRejected(), "Offer should not be initially rejected");

        offer.setRejected(true);

        assertTrue(offer.isRejected(), "Offer should be marked as rejected");
    }

    @Test
    void testIdField() {
        offer.setId(10L);
        assertEquals(10L, offer.getId(), "ID should match the set value");
    }

    @Test
    void testDefaultValues() {
        assertFalse(offer.isAccepted(), "Offer should not be accepted by default");
        assertFalse(offer.isRejected(), "Offer should not be rejected by default");
        assertNull(offer.getAcceptedOn(), "AcceptedOn should be null by default");
    }
}
