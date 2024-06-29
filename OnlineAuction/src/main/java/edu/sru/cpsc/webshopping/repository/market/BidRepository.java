package edu.sru.cpsc.webshopping.repository.market;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.Bid;

public interface BidRepository extends CrudRepository<Bid, Long> {
	List<Bid> findByAuction(Auction auction);
	
	List<Bid> findByAuctionOrderByIdDesc(Auction auction);
    // You can add custom query methods here if needed in the future
}
