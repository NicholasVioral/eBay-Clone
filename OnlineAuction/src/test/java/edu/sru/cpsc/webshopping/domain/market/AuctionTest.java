package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {AuctionTest.class})
public class AuctionTest {

    @Test
    void testAuction() {
        Auction auction = new Auction();
        assertNotNull(auction);
    }

    @Test
    void testGetId() {
        Auction auction = new Auction();
        auction.setId(1L);
        assertEquals(1L, auction.getId());
    }

    @Test
    void testGetMarketListing() {
        Auction auction = new Auction();
        MarketListing marketListing = new MarketListing();
        auction.setMarketListing(marketListing);
        assertEquals(marketListing, auction.getMarketListing());
    }

    @Test
    void testGetStartingBid() {
        Auction auction = new Auction();
        BigDecimal startingBid = new BigDecimal("10.00");
        auction.setStartingBid(startingBid);
        assertEquals(startingBid, auction.getStartingBid());
    }

    @Test
    void testGetCurrentBid() {
        Auction auction = new Auction();
        BigDecimal currentBid = new BigDecimal("20.00");
        auction.setCurrentBid(currentBid);
        assertEquals(currentBid, auction.getCurrentBid());
    }

    @Test
    void testGetStartAuctionDate() {
        Auction auction = new Auction();
        LocalDateTime startAuctionDate = LocalDateTime.now();
        auction.setStartAuctionDate(startAuctionDate);
        assertEquals(startAuctionDate, auction.getStartAuctionDate());
    }

    @Test
    void testGetEndAuctionDate() {
        Auction auction = new Auction();
        LocalDateTime endAuctionDate = LocalDateTime.now().plusDays(7);
        auction.setEndAuctionDate(endAuctionDate);
        assertEquals(endAuctionDate, auction.getEndAuctionDate());
    }
}