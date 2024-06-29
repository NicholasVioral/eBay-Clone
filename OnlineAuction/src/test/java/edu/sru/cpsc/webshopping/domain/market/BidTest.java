package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.user.User;

@SpringBootTest(classes = {BidTest.class})
@ExtendWith(MockitoExtension.class)
public class BidTest {
    
    @Mock
    private Auction auction;
    
    @Mock
    private User bidder;
    
    @InjectMocks
    private Bid bid;
    
    @Test
    void testAuction() {
        Auction newAuction = mock(Auction.class);
        bid.setAuction(newAuction);
        assertEquals(newAuction, bid.getAuction());
    }
    
    @Test
    void testBidder() {
        User newBidder = mock(User.class);
        bid.setBidder(newBidder);
        assertEquals(newBidder, bid.getBidder());
    }
    
    @Test
    void testGetBidAmount() {
        BigDecimal amount = new BigDecimal(100);
        bid.setBidAmount(amount);
        assertEquals(amount, bid.getBidAmount());
    }
    
    @Test
    void testSetBidAmount() {
        BigDecimal newAmount = new BigDecimal(200);
        bid.setBidAmount(newAmount);
        assertEquals(newAmount, bid.getBidAmount());
    }
}