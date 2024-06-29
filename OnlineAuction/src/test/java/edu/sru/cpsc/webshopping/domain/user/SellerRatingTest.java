package edu.sru.cpsc.webshopping.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SellerRatingTest {

    private SellerRating sellerRating;

    @BeforeEach
    public void setUp() {
        sellerRating = new SellerRating();
    }

    @Test
    public void testSetRating() {
        sellerRating.setRating(3.0f);
        assertEquals(3.0f, sellerRating.getRating(), 0.01f);
    }

    @Test
    public void testSetRatingWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            sellerRating.setRating(-1.0f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sellerRating.setRating(6.0f);
        });
    }

    @Test
    public void testSetNumRatings() {
        sellerRating.setNumRatings(5);
        assertEquals(5, sellerRating.getNumRatings());
    }

    @Test
    public void testSetNumRatingsWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            sellerRating.setNumRatings(-1);
        });
    }

    @Test
    public void testGetRatingName() {
        sellerRating.setRating(4.8f);
        assertEquals("Excellent", sellerRating.getRatingName());
        sellerRating = new SellerRating();
        sellerRating.setRating(4.2f);
        assertEquals("Good", sellerRating.getRatingName());
        sellerRating = new SellerRating();
        sellerRating.setRating(3.5f);
        assertEquals("Average", sellerRating.getRatingName());
        sellerRating = new SellerRating();
        sellerRating.setRating(2.5f);
        assertEquals("Poor", sellerRating.getRatingName());
        sellerRating = new SellerRating();
        sellerRating.setRating(1.0f);
        assertEquals("Bad", sellerRating.getRatingName());
    }

    @Test
    public void testNoRatings() {
        assertEquals(true, sellerRating.noRatings());
        sellerRating.setNumRatings(1);
        assertEquals(false, sellerRating.noRatings());
    }
}