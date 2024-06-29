package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class MarketListingCSVModelTest {

    @Test
    public void testWidgetId() {
        MarketListingCSVModel model = new MarketListingCSVModel();
        long expectedWidgetId = 123L;
        model.setWidgetId(expectedWidgetId);
        assertEquals(expectedWidgetId, model.getWidgetId(), "Widget ID should match the set value.");
    }

    @Test
    public void testPricePerItem() {
        MarketListingCSVModel model = new MarketListingCSVModel();
        BigDecimal expectedPrice = new BigDecimal("19.99");
        model.setPricePerItem(expectedPrice);
        assertEquals(expectedPrice, model.getPricePerItem(), "Price per item should match the set value.");
    }

    @Test
    public void testAuctionPrice() {
        MarketListingCSVModel model = new MarketListingCSVModel();
        BigDecimal expectedAuctionPrice = new BigDecimal("25.50");
        model.setAuctionPrice(expectedAuctionPrice);
        assertEquals(expectedAuctionPrice, model.getAuctionPrice(), "Auction price should match the set value.");
    }

    @Test
    public void testQtyAvailable() {
        MarketListingCSVModel model = new MarketListingCSVModel();
        long expectedQtyAvailable = 50L;
        model.setQtyAvailable(expectedQtyAvailable);
        assertEquals(expectedQtyAvailable, model.getQtyAvailable(), "Quantity available should match the set value.");
    }
}
