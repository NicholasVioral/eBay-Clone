package edu.sru.cpsc.webshopping.repository.market;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sru.cpsc.webshopping.domain.market.OfferNotification;

@Repository
public interface OfferNotificationRepository extends CrudRepository<OfferNotification, Long> {
	
	public List<OfferNotification> findAllByMarketListingId(Long marketListingId);

}
