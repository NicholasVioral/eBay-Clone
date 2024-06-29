package edu.sru.cpsc.webshopping.repository.market;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.AutoBid;

public interface AutoBidRepository extends CrudRepository<AutoBid, Long> {
	List<AutoBid> findByAuction(Auction auction);
//	
//	List<AutoBid> findByAuctionOrderByIdDesc(Auction auction);
//	
//	List<AutoBid> findByAuctionAndUser(Auction auction, User user);
    // You can add custom query methods here if needed in the future
}
