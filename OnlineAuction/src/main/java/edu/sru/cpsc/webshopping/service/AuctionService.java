package edu.sru.cpsc.webshopping.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.controller.StatisticsDomainController;
import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.AutoBid;
import edu.sru.cpsc.webshopping.domain.market.Bid;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.market.AuctionRepository;
import edu.sru.cpsc.webshopping.repository.market.AutoBidRepository;
import edu.sru.cpsc.webshopping.repository.market.BidRepository;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;
    
    @Autowired
    private AutoBidRepository autoBidRepository;
    
    @Autowired
    private StatisticsDomainController statControl;

    /**
     * Creates or updates an auction with the provided details.
     *
     */
     
    public Auction saveAuction(Auction auction) {
  
        return auctionRepository.save(auction);
    }
    
    public Bid bid(Auction auction, User user, BigDecimal bidAmount) {
        Bid bid = new Bid(auction, user, bidAmount);
        
        // log event
	    StatsCategory cat = StatsCategory.AUCTION;
	    Statistics stat = new Statistics(cat, 1);
	    stat.setDescription(user.getUsername() + " bid $" + bidAmount.toString() + " on auction " + auction.getId());
	    statControl.addStatistics(stat);
        
    	return bidRepository.save(bid);
    }
    
    public AutoBid autoBid(Auction auction, User user, BigDecimal maxBid) {
    	AutoBid autoBid = new AutoBid(maxBid, auction, user);
    	return autoBidRepository.save(autoBid);
    }
    
    public void removeAutoBid(long id) {
    	autoBidRepository.deleteById(id);
    }
    
    public List<AutoBid> getAutoBidsForListing(Auction auction) {
    	return autoBidRepository.findByAuction(auction);
    }
    
    public Set<User> findUniqueBiddersForListing(MarketListing marketListing) {
        List<Bid> bids = bidRepository.findByAuction(marketListing.getAuction());
        
        Set<User> uniqueBidders = new HashSet<>();
        for (Bid bid : bids) {
            uniqueBidders.add(bid.getBidder());
        }
        return uniqueBidders;
    }

    public int countUniqueBiddersForListing(MarketListing marketListing) {
        return findUniqueBiddersForListing(marketListing).size();
    }
    
    public int getTotalBidsForListing(MarketListing marketListing) {
        return bidRepository.findByAuction(marketListing.getAuction()).size();
    }
    
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    
    }
    
    public Bid findHighestBidForAuction(Auction auction) {
        List<Bid> bids = bidRepository.findByAuctionOrderByIdDesc(auction);     

        if (bids.isEmpty()) {
            return null;
        } else {
            Bid mostRecentBid = bids.get(0); // this is now the most recent bid based on ID
            return mostRecentBid;
        }
    }
    
    public List<Bid> getAllBidsForListing(MarketListing marketListing) {
        return bidRepository.findByAuction(marketListing.getAuction());
    }
    
}
