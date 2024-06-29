package edu.sru.cpsc.webshopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import edu.sru.cpsc.webshopping.controller.StatisticsDomainController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class WatchlistService {
	UserController userController;
	UserService userService;
	UserRepository userRepository;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	StatisticsDomainController statControl;
	
	WatchlistService(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}
	
	/**
	 *  Adds an item to the user's watchlist
	 *  @param marketListing   the listing that the user wants to add to their watchlist
	 *  @param user   the currently logged in user
	 */
	public void watchlistAdd(@Validated MarketListing marketListing, User user) {
		MarketListing addedWidget = entityManager.find(MarketListing.class, marketListing.getId());
		Widget widget = addedWidget.getWidgetSold();
		
		// check if the widget is null
		if (widget == null) {
			throw new IllegalArgumentException("Widget pass to addToWishlist not found in database.");
		}
		
		// log event
	    StatsCategory cat = StatsCategory.WATCHLIST;
	    Statistics stat = new Statistics(cat, 1);
	    stat.setDescription(user.getUsername() + " added " + widget.getName() + " to their watchlist");
	    statControl.addStatistics(stat);
		
		user.getWishlistedWidgets().add(addedWidget);
		
	}
	
	/**
	 * Removes an item from the user's watchlist
	 * @param marketListing   the listing that the user wants to remove from their watchlist
	 * @param user   the currently logged in user
	 */
	public void watchlistRemove(@Validated MarketListing marketListing, User user) {
		MarketListing delWidget = entityManager.find(MarketListing.class, marketListing.getId());
		Widget widget = delWidget.getWidgetSold();
		
		// check if the widget is null
		if (widget == null) {
			throw new IllegalArgumentException("Widget pass to removeFromWishlist not found in database.");
		}
		
		// log event
	    StatsCategory cat = StatsCategory.WATCHLIST;
	    Statistics stat = new Statistics(cat, 1);
	    stat.setDescription(user.getUsername() + " removed " + widget.getName() + " from their watchlist");
	    statControl.addStatistics(stat);
		
		user.getWishlistedWidgets().remove(delWidget);
	}
	
	/**
	 * Checks how many users have the market listing saved in their watchlist
	 * @param marketListing   the listing to be checked
	 */
	public Long countUsersWithMarketListingInWatchlist(Long marketListingId) {
		
		Long count = userRepository.countUsersWithMarketListingInWatchlist(marketListingId);
		
		return count;
	}
	
}