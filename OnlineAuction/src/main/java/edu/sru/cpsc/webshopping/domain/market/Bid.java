package edu.sru.cpsc.webshopping.domain.market;

import java.math.BigDecimal;

import edu.sru.cpsc.webshopping.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Auction auction;

    @ManyToOne
    private User bidder;

    private BigDecimal bidAmount;

    public Bid() {
    }

    public Bid(Auction auction, User bidder, BigDecimal bidAmount) {
        this.auction = auction;
        this.bidder = bidder;
        this.bidAmount = bidAmount;
    }

    public Auction getAuction() {
        return auction;
    }
    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    public User getBidder() {
        return bidder;
    }
    public void setBidder(User bidder) {
        this.bidder = bidder;
    }
    public BigDecimal getBidAmount() {
        return bidAmount;
    }
    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

}
