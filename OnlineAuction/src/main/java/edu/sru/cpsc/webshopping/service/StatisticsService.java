package edu.sru.cpsc.webshopping.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.stats.StatsRepository;

@Service
public class StatisticsService {
	@Autowired
	private StatsRepository statsRepository;
	
	@Autowired
	private MarketListingRepository marketListingRepository;
	
	public StatisticsService() {
		
	}
	
	public void incrementPageView(long pageId) {
		Optional<MarketListing> listing = marketListingRepository.findById(pageId);
		listing.get().setPageViews(listing.get().getPageViews()+1);
		marketListingRepository.save(listing.get());
	}
}
