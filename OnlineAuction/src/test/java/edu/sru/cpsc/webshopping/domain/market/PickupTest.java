package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {PickupTest.class})
public class PickupTest {

    private Pickup pickup;

    @BeforeEach
    public void setUp() {
        pickup = new Pickup();
    }

    @Test
    public void testGetAndSetId() {
        long id = 123;
        pickup.setId(id);
        assertEquals(id, pickup.getId());
    }

    @Test
    public void testGetAndSetLocation() {
        PickupAddress location = new PickupAddress();
        assertNull(pickup.getLocation());
        pickup.setLocation(location);
        assertNotNull(pickup.getLocation());
        assertEquals(location, pickup.getLocation());
    }

    @Test
    public void testGetAndSetListing() {
        MarketListing listing = new MarketListing();
        assertNull(pickup.getListing());
        pickup.setListing(listing);
        assertNotNull(pickup.getListing());
        assertEquals(listing, pickup.getListing());
    }

    @Test
    public void testGetAndSetTransaction() {
        Transaction transaction = new Transaction();
        assertNull(pickup.getTransaction());
        pickup.setTransaction(transaction);
        assertNotNull(pickup.getTransaction());
        assertEquals(transaction, pickup.getTransaction());
    }

}