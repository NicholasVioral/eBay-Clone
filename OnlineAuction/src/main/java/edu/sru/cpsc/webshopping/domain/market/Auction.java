package edu.sru.cpsc.webshopping.domain.market;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * Auction attributes that will be dynamically changed when users bid
 */

@Entity
public class Auction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL)
    private MarketListing marketListing;

    private BigDecimal startingBid;
    private BigDecimal currentBid;

    private LocalDateTime startAuctionDate;
    
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endAuctionDate;

    public Auction() {
        this.startAuctionDate = LocalDateTime.now();
        this.endAuctionDate = LocalDateTime.now().plusDays(7);
    }
    
    // id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // marketListing
    public MarketListing getMarketListing() {
        return marketListing;
    }

    public void setMarketListing(MarketListing marketListing) {
        this.marketListing = marketListing;
    }

    // startingBid
    public BigDecimal getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(BigDecimal startingBid) {
        this.startingBid = startingBid;
    }

    // currentBid
    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(BigDecimal currentBid) {
        this.currentBid = currentBid;
    }

    // startAuctionDate
    public LocalDateTime getStartAuctionDate() {
        return startAuctionDate;
    }

    public void setStartAuctionDate(LocalDateTime startAuctionDate) {
        this.startAuctionDate = startAuctionDate;
    }

    // endAuctionDate
    public LocalDateTime getEndAuctionDate() {
        return endAuctionDate;
    }

    public void setEndAuctionDate(LocalDateTime endAuctionDate) {
        this.endAuctionDate = endAuctionDate;
    }
    
}
