package edu.sru.cpsc.webshopping.domain.market;

import java.math.BigDecimal;

import edu.sru.cpsc.webshopping.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AutoBid {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@ManyToOne
    private Auction auction;

    @ManyToOne
    private User bidder;
    
    private BigDecimal maxBid;
    
    public AutoBid() {
    	
    }
    
    public AutoBid(BigDecimal maxBid, Auction auction, User bidder) {
    	this.maxBid = maxBid;
    	this.auction = auction;
    	this.bidder = bidder;
    }
    
    public long getId() {
    	return this.id;
    }
    
    public void setMaxBid(BigDecimal maxBid) {
    	this.maxBid = maxBid;
    }
    
    public BigDecimal getMaxBid() {
    	return this.maxBid;
    }
    
    public void setAuction(Auction auction) {
    	this.auction = auction;
    }
    
    public Auction getAuction() {
    	return this.auction;
    }
    
    public void setBidder(User bidder) {
    	this.bidder = bidder;
    }
    
    public User getBidder() {
    	return this.bidder;
    }
}
