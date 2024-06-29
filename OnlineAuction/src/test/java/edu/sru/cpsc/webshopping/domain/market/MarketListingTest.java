package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;

@SpringBootTest(classes = {MarketListingTest.class})
public class MarketListingTest {

    private MarketListing marketListing;

    @Mock
    private User seller;

    @Mock
    private Widget widgetSold;

    @Mock
    private WidgetImage widgetImage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        marketListing = new MarketListing();
    }

    @Test
    public void testGetId() {
        long idValue = 4L;
        marketListing.setId(idValue);
        assertEquals(idValue, marketListing.getId());
    }

    @Test
    public void testGetPricePerItem() {
        BigDecimal pricePerItem = new BigDecimal("10.00");
        marketListing.setPricePerItem(pricePerItem);
        assertEquals(pricePerItem, marketListing.getPricePerItem());
    }

    @Test
    public void testGetQtyAvailable() {
        long qtyAvailable = 10L;
        marketListing.setQtyAvailable(qtyAvailable);
        assertEquals(qtyAvailable, marketListing.getQtyAvailable());
    }

    @Test
    public void testGetSeller() {
        marketListing.setSeller(seller);
        assertNotNull(marketListing.getSeller());
    }

    @Test
    public void testGetWidgetSold() {
        marketListing.setWidgetSold(widgetSold);
        assertNotNull(marketListing.getWidgetSold());
    }

    @Test
    public void testIsDeleted() {
        marketListing.setDeleted(true);
        assertEquals(true, marketListing.isDeleted());
    }

    @Test
    public void testGetTransactions() {
        Set<Transaction> transactions = new HashSet<>();
        transactions.add(new Transaction());
        marketListing.setTransactions(transactions);
        assertEquals(transactions, marketListing.getTransactions());
    }

    @Test
    public void testGetImages() {
        Set<WidgetImage> images = new HashSet<>();
        images.add(widgetImage);
        marketListing.setImages(images);
        assertEquals(images, marketListing.getImages());
    }

    @Test
    public void testGetCoverImage() {
        String coverImage = "image.jpg";
        marketListing.setCoverImage(coverImage);
        assertEquals(coverImage, marketListing.getCoverImage());
    }

    @Test
    public void testGetAuction() {
        Auction auction = new Auction();
        marketListing.setAuction(auction);
        assertNotNull(marketListing.getAuction());
    }

    @Test
    public void testGetSetAutomatically() {
        marketListing.setSetAutomatically(true);
        assertEquals(true, marketListing.getSetAutomatically());
    }

    @Test
    public void testGetLocalPickup() {
        Pickup localPickup = new Pickup();
        marketListing.setLocalPickup(localPickup);
        assertNotNull(marketListing.getLocalPickup());
    }

    @Test
    public void testGetIsLocalPickupOnly() {
        marketListing.setIsLocalPickupOnly(true);
        assertEquals(true, marketListing.getIsLocalPickupOnly());
    }

    @Test
    public void testSetId() {
        long idValue = 4L;
        marketListing.setId(idValue);
        assertEquals(idValue, marketListing.getId());
    }

    @Test
    public void testSetPricePerItem() {
        BigDecimal pricePerItem = new BigDecimal("10.00");
        marketListing.setPricePerItem(pricePerItem);
        assertEquals(pricePerItem, marketListing.getPricePerItem());
    }

    @Test
    public void testSetQtyAvailable() {
        long qtyAvailable = 10L;
        marketListing.setQtyAvailable(qtyAvailable);
        assertEquals(qtyAvailable, marketListing.getQtyAvailable());
    }

    @Test
    public void testSetSeller() {
        marketListing.setSeller(seller);
        assertNotNull(marketListing.getSeller());
    }

    @Test
    public void testSetWidgetSold() {
        marketListing.setWidgetSold(widgetSold);
        assertNotNull(marketListing.getWidgetSold());
    }

    @Test
    public void testSetDeleted() {
        marketListing.setDeleted(true);
        assertEquals(true, marketListing.isDeleted());
    }

    @Test
    public void testSetTransactions() {
        Set<Transaction> transactions = new HashSet<>();
        transactions.add(new Transaction());
        marketListing.setTransactions(transactions);
        assertEquals(transactions, marketListing.getTransactions());
    }

    @Test
    public void testSetImages() {
        Set<WidgetImage> images = new HashSet<>();
        images.add(widgetImage);
        marketListing.setImages(images);
        assertEquals(images, marketListing.getImages());
    }

    @Test
    public void testSetCoverImage() {
        String coverImage = "image.jpg";
        marketListing.setCoverImage(coverImage);
        assertEquals(coverImage, marketListing.getCoverImage());
    }

    @Test
    public void testSetAuction() {
        Auction auction = new Auction();
        marketListing.setAuction(auction);
        assertNotNull(marketListing.getAuction());
    }

    @Test
    public void testSetSetAutomatically() {
        marketListing.setSetAutomatically(true);
        assertEquals(true, marketListing.getSetAutomatically());
    }

    @Test
    public void testSetLocalPickup() {
        Pickup localPickup = new Pickup();
        marketListing.setLocalPickup(localPickup);
        assertNotNull(marketListing.getLocalPickup());
    }

    @Test
    public void testSetIsLocalPickupOnly() {
        marketListing.setIsLocalPickupOnly(true);
        assertEquals(true, marketListing.getIsLocalPickupOnly());
    }
    
    @Test
    public void testHasAcceptedOffer() {
        marketListing.setHasAcceptedOffer(true);
        assertTrue(marketListing.hasAcceptedOffer());
        marketListing.setHasAcceptedOffer(false);
        assertFalse(marketListing.hasAcceptedOffer());
    }
}