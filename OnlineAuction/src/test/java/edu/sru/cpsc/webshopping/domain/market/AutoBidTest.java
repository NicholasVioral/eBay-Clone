package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.user.User;

@SpringBootTest(classes = {AutoBidTest.class})
public class AutoBidTest {

    private AutoBid autoBid;
    private Auction auction;
    private User bidder;

    @BeforeEach
    public void setUp() {
        auction = new Auction();
        bidder = new User();
        autoBid = new AutoBid(new BigDecimal("100.00"), auction, bidder);
    }

    @Test
    public void testGetId() {
        assertNotNull(autoBid.getId());
    }

    @Test
    public void testGetMaxBid() {
        assertEquals(new BigDecimal("100.00"), autoBid.getMaxBid());
    }

    @Test
    public void testSetMaxBid() {
        autoBid.setMaxBid(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("200.00"), autoBid.getMaxBid());
    }

    @Test
    public void testGetAuction() {
        assertEquals(auction, autoBid.getAuction());
    }

    @Test
    public void testSetAuction() {
        Auction newAuction = new Auction();
        autoBid.setAuction(newAuction);
        assertEquals(newAuction, autoBid.getAuction());
    }

    @Test
    public void testGetBidder() {
        assertEquals(bidder, autoBid.getBidder());
    }

    @Test
    public void testSetBidder() {
        User newBidder = new User();
        autoBid.setBidder(newBidder);
        assertEquals(newBidder, autoBid.getBidder());
    }

    @Test
    public void testConstructor() {
        assertNotNull(autoBid.getId());
        assertEquals(new BigDecimal("100.00"), autoBid.getMaxBid());
        assertEquals(auction, autoBid.getAuction());
        assertEquals(bidder, autoBid.getBidder());
    }

    @Test
    public void testEmptyConstructor() {
        AutoBid emptyAutoBid = new AutoBid();
        assertNull(emptyAutoBid.getAuction());
        assertNull(emptyAutoBid.getBidder());
        assertNull(emptyAutoBid.getMaxBid());
    }
}