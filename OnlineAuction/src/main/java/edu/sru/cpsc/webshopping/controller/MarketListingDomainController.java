package edu.sru.cpsc.webshopping.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.AutoBid;
import edu.sru.cpsc.webshopping.domain.market.Bid;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.MarketListingStatus;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.market.AutoBidRepository;
import edu.sru.cpsc.webshopping.repository.market.BidRepository;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;
import edu.sru.cpsc.webshopping.service.AuctionService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.service.WatchlistService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;

/** A class for interacting with MarketListing items from the database */
@RestController
public class MarketListingDomainController {
	private User currentlyLoggedIn;
	private MarketListingRepository marketRepository;
	@Autowired
	private WidgetRepository widgetRepository;
	private StatisticsDomainController statControl;
	private WidgetImageController imageController;
	private UserController userController;
	private WatchlistService watchlistService;
	private AutoBidRepository autoBidRepository;
	@Autowired
	private AuctionService auctionService;
	@Autowired
	private UserService userService;
	@Autowired
	private BidRepository bidRepository;
	@PersistenceContext private EntityManager entityManager;
	@Autowired
  	private ServletContext servletContext;
	
	MarketListingDomainController(
			MarketListingRepository marketRepository,
			StatisticsDomainController statControl,
			WidgetImageController imageController,
			WatchlistService watchlistService
			//UserController userController
			) {
			this.marketRepository = marketRepository;
			this.widgetRepository = widgetRepository;
			this.statControl = statControl;
			this.imageController = imageController;
			this.watchlistService = watchlistService;
			//this.userController = userController;
		}


	/**
	 * Gets the MarketListing with an id matching the passed id
	 *
	 * @param id the ID to search for product
	 * @return the MarketListing found, or null if no MarketListing with the passed id is found
	 */
	
	
	@RequestMapping("/get-market-listing/{id}")
	@Transactional
	public MarketListing getMarketListing(@PathVariable("id") long id) {
		// MarketListing marketListing = marketRepository.findById(id).orElseThrow(() -> new
		// IllegalArgumentException("Invalid ID provided for retrieving a MarketListing"));
		MarketListing marketListing = entityManager.find(MarketListing.class, id);
		return marketListing;
	}

	@RequestMapping("/get-widget-by-name/{name}")
	public Widget getWidget(@PathVariable("name") String name) {
	    Widget widget = widgetRepository.findByName(name);
	    System.out.println(widget);
	    return widget;
	}

	@RequestMapping("/get-listing-by-widget")
	public MarketListing getListingByWidget(@PathVariable("widget") Widget widget) {
		MarketListing listing = marketRepository.findByWidgetSold(widget);
		return listing;
	}
	

	/**
	 * Gets all MarketListings from the database
	 *
	 * @return an Iterable of all the database's MarketListings
	 */
	@RequestMapping({"/get-all-listings"})
	public Iterable<MarketListing> getAllListings() {
		Iterable<MarketListing> listings = marketRepository.findAll();
		return listings;/*() ->
		not sure what all this was but it doesnt work
		StreamSupport.stream(listings.spliterator(), false)
		.filter(
				marketListing ->
				PreLoad.subCategoryConfiguration().stream()
				.anyMatch(marketListing.getWidgetSold().getSubCategory()::equalsIgnoreCase))
		.iterator();*/
	}

	/**
	 * Updates the MarketListing associated with the passed marketListing to reflect a purchase This
	 * involves reducing the quantity available by the amount purchased
	 *
	 * @param marketListing the MarketListing to update
	 * @param numPurchased the number of items purchased
	 * @exception IllegalStateException is thrown if the MarketListing does not exist in the database,
	 *     or is marked as deleted
	 * @exception IllegalArgumentException is thrown if the number purchased exceeds the quantity
	 *     available
	 */
	@Transactional
	@PostMapping("/market-listing-purchase-update")
	public void marketListingPurchaseUpdate(
			@Validated MarketListing marketListing, int numPurchased) {
		MarketListing currListing = entityManager.find(MarketListing.class, marketListing.getId());
		if (currListing == null) {
			throw new IllegalStateException(
					"The referenced MarketListing you have purchased from does not exist.");
		} else if (currListing.isDeleted()) {
			throw new IllegalStateException(
					"The MarketListing you have purchased from is no longer available for purchase.");
		} else if (numPurchased > currListing.getQtyAvailable()) {
			throw new IllegalArgumentException("The number of items you wish to buy exceeds the stock.");
		}
		float value = currListing.getPricePerItem().floatValue() * numPurchased;
		Statistics recordSaleValue = new Statistics(StatsCategory.SALEVALUE, value);
		Statistics recordSale = new Statistics(StatsCategory.SALE, 1);
		recordSaleValue.setDescription(
				numPurchased
				+ " "
				+ currListing.getWidgetSold().getName()
				+ "(s)"
				+ " were sold for a total of: $"
				+ value);
		recordSale.setDescription(currListing.getWidgetSold().getName() + ": was sold");
		statControl.addStatistics(recordSaleValue);
		statControl.addStatistics(recordSale);
		currListing.setQtyAvailable(currListing.getQtyAvailable() - numPurchased);
		marketRepository.save(currListing);
	}

	/**
	 * Creates a new MarketListing in the database
	 *
	 * @param marketListing the values of the new MarketListing
	 * @param result form verification
	 * @exception IllegalArgumentException is thrown if result is invalid
	 */
	@Transactional
	@PostMapping("/add-market-listing")
	public MarketListing addMarketListing(@Validated MarketListing marketListing) {
		User seller = entityManager.find(User.class, marketListing.getSeller().getId());
		Widget widgetSold = entityManager.find(Widget.class, marketListing.getWidgetSold().getId());
		marketListing.setSeller(seller);
		marketListing.setWidgetSold(widgetSold);
		marketListing.setListingStatus(MarketListingStatus.ACTIVE);
		return marketRepository.save(marketListing);
	}

	@Transactional
	@PostMapping("/add-market-listing-simple")
	public MarketListing addMarketListingSimple(@Validated MarketListing marketListing) {

		return marketRepository.save(marketListing);
	}

	/**
	 * Updates an existing MarketListing to hold the values of the passed MarketListing The updated
	 * MarketListing is the one whose id matches the id of the updatedMarketListing
	 *
	 * @param updatedMarketListing a MarketListing holding the updated values
	 */
	@Transactional
	@PostMapping("/edit-market-listing")
	public MarketListing editMarketListing(@Validated MarketListing updatedMarketListing, User user) {
		Optional<MarketListing> dbListingOpt = marketRepository.findById(updatedMarketListing.getId());
		
		if (!dbListingOpt.isPresent()) {
	        throw new IllegalArgumentException("MarketListing with ID " + updatedMarketListing.getId() + " not found.");
	    }
		
		MarketListing dbListing = dbListingOpt.get();
		dbListing.setPricePerItem(updatedMarketListing.getPricePerItem());
		dbListing.setQtyAvailable(updatedMarketListing.getQtyAvailable());
		dbListing.getWidgetSold().setDescription(updatedMarketListing.getWidgetSold().getDescription());
		dbListing.setDeleted(updatedMarketListing.isDeleted());
		
		if (updatedMarketListing.getImages() != null) {
			dbListing.setImages(updatedMarketListing.getImages());
		}
		
		Widget widget = dbListing.getWidgetSold();
		
		// log event
	    StatsCategory cat = StatsCategory.WATCHLIST;
	    Statistics stat = new Statistics(cat, 1);
	    stat.setDescription(user.getUsername() + " edited " + widget.getName());
	    statControl.addStatistics(stat);
		
		return marketRepository.save(dbListing);
	}

	/**
	 * Undos the changes caused by a purchase on the associated MarketListing
	 *
	 * @param trans The purchase to undo
	 * @return true if the operation is successful
	 */
	@Transactional
	@PostMapping("undo-market-listing-purchase")
	public void undoPurchase(@Validated Transaction trans) {
		MarketListing currListing = entityManager.find(MarketListing.class, trans.getMarketListing().getId());
		currListing.setQtyAvailable(currListing.getQtyAvailable() + trans.getQtyBought());
		marketRepository.save(currListing);
	}

	@Transactional
	@GetMapping("get-Market-Listing-by-User")
	public MarketListing[] getListingbyUser(@Validated User user) {
		return marketRepository.findBySeller(user).stream()
				.filter(marketListing -> !marketListing.isDeleted())
				.toArray(MarketListing[]::new);
	}
				

	/**
	 * Deletes a MarketListing from the database
	 *
	 * @param id the id of the MarketListing to delete
	 */
	@PostMapping("/delete-market-listing/{id}")
	public void deleteMarketListing(@PathVariable long id) {
		for(WidgetImage image : imageController.getwidgetImageByMarketListing(getMarketListing(id)))
		{
			imageController.deleteWidgetImage(image.getId());
		}
		entityManager.detach(getMarketListing(id));
		entityManager.detach(entityManager.find(Widget.class, getMarketListing(id).getWidgetSold().getId()));
		marketRepository.deleteById(id);
	}

	@Transactional
	@PostMapping("/update-market-listing-auction")
	public MarketListing updateMarketListingAuction(
	        @RequestParam("marketListingId") Long marketListingId, 
	        @RequestParam("startingBid") BigDecimal startingBid, 
	        @RequestParam("endAuctionDate") LocalDateTime endAuctionDate) {   // assuming java.util.Date, adjust as necessary
	
	    // Find the MarketListing entity
	    MarketListing marketListing = entityManager.find(MarketListing.class, marketListingId);
	    if (marketListing == null) {
	        throw new IllegalStateException("No MarketListing found for the provided ID");
	    }
	
	    // Update the auction details
	    Auction auction = marketListing.getAuction();
	    if (auction == null) {
	        auction = new Auction();  // assuming you have a default constructor for Auction
	    }
	    auction.setStartingBid(startingBid);
	    auction.setEndAuctionDate(endAuctionDate);   // Adjust the method names as per your Auction class
	
	    marketListing.setAuction(auction);
	
	    // Save the updated MarketListing
	    return marketRepository.save(marketListing);
	}

	/*
	 * method for placing a manual bid on a market listing
	 * @param   the bid the user is placing on the listing
	 * @param   the market listing id
	 * @param   the ID of the user placing the bid
	 * @param   model
	 * @param   redirect attributes
	 * return   redirect
	 */
	@PostMapping("/updateBid")
	public ResponseEntity<Object> updateBid(@RequestParam BigDecimal bidAmount, @RequestParam Long listingId, @RequestParam Long bidderId, Model model, RedirectAttributes redirectAttributes, Principal principal) {
		User bidder = userService.getUserById(bidderId);
		User user = userService.getUserByUsername(principal.getName());
		
		MarketListing listing = marketRepository.findById(listingId).orElse(null);
		Auction auction = listing.getAuction();
		BigDecimal currentBidPrice = auction.getCurrentBid();
		
		boolean manualBid = true; // variable saying this is a manual bid
		
		// Ensure that listing.getAuctionPrice() also returns a BigDecimal. Bid amount should be less than 20
		if (listing != null && bidAmount.compareTo(new BigDecimal(20)) <= 0 && listing.getAuction().getCurrentBid().add(bidAmount).compareTo(listing.getAuction().getStartingBid()) >= 0){
			BigDecimal newBid = currentBidPrice.add(bidAmount);
			auctionService.bid(auction, bidder, bidAmount);
			listing.getAuction().setCurrentBid(newBid);
			marketRepository.save(listing);
			
			watchlistService.watchlistAdd(listing, user); // add listing to bidder's watchlist
			userService.addUser(user); // save user to the user repository      
		}
		
		// check for any autobids
		placeAutoBids(listing, auction, currentBidPrice, manualBid);
		
		// redirect
		URI redirectUri = URI.create(servletContext.getContextPath() + "/viewMarketListing/" + listingId);
		return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
	}
	
	/*
	 * Method for setting up automatic bidding
	 * Source for incrementing: https://www.ebay.com/help/buying/bidding/automatic-bidding?id=4014#section1
	 * 
	 * @param   the maximum value the user would be willing to spend on the product
	 * @param   the listing ID of the product
	 * @param   the id of the user that is placing the bid
	 * @param   model
	 * @param   redirect attributes
	 * @param   principal
	 * return  redirect to the market listing page
	 */
	@PostMapping("/autoBid")
	public ResponseEntity<Object> autoBid(@RequestParam BigDecimal maxBid, @RequestParam Long listingId, @RequestParam Long bidderId, Model model, RedirectAttributes redirectAttributes, Principal principal) {
		User bidder = userService.getUserById(bidderId);
		User user = userService.getUserByUsername(principal.getName());
		
		MarketListing listing = marketRepository.findById(listingId).orElse(null);
		Auction auction = listing.getAuction();
		
		BigDecimal currentBidPrice = auction.getCurrentBid();	
		BigDecimal increment = calculateIncrement(maxBid); // set up increments for auto bidding
		
		boolean manualBid = false; // variable saying this is an auto bid
		
		if (listing != null && maxBid.compareTo(currentBidPrice) > 0){
			auctionService.autoBid(auction, user, maxBid); // update autobid table with user and max amount for according auction
			BigDecimal newBid = currentBidPrice.add(increment);
			auctionService.bid(auction, bidder, increment);
			listing.getAuction().setCurrentBid(newBid);
			marketRepository.save(listing);
			
			watchlistService.watchlistAdd(listing, user); // add listing to bidder's watchlist
			userService.addUser(user);
		}
		
		// check for any autobids
		placeAutoBids(listing, auction, currentBidPrice, manualBid);
		
		// redirect
		URI redirectUri = URI.create(servletContext.getContextPath() + "/viewMarketListing/" + listingId);
		return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
	}
	
	/*
	 * Method for calculating auto bid increments
	 * @param   the current bid price of the item
	 * @return  the bid increment
	 */
	private BigDecimal calculateIncrement(BigDecimal currentBidPrice) {
		BigDecimal[] priceRanges = { // defines the different price ranges where the bid increment will differ
	        new BigDecimal("0.01"), new BigDecimal("0.99"),
	        new BigDecimal("1.00"), new BigDecimal("4.99"),
	        new BigDecimal("5.00"), new BigDecimal("24.99"),
	        new BigDecimal("25.00"), new BigDecimal("99.99"),
	        new BigDecimal("100.00"), new BigDecimal("249.99"),
	        new BigDecimal("250.00"), new BigDecimal("499.99"),
	        new BigDecimal("500.00"), new BigDecimal("999.99"),
	        new BigDecimal("1000.00"), new BigDecimal("2499.99"),
	        new BigDecimal("2500.00"), new BigDecimal("4999.99"),
	        new BigDecimal("5000.00")
	    };

	    BigDecimal[] increments = { // defines the bid increments for the given price range
	        new BigDecimal("0.05"),
	        new BigDecimal("0.25"),
	        new BigDecimal("0.50"),
	        new BigDecimal("1.00"),
	        new BigDecimal("2.50"),
	        new BigDecimal("5.00"),
	        new BigDecimal("10.00"),
	        new BigDecimal("25.00"),
	        new BigDecimal("50.00"),
	        new BigDecimal("100.00")
	    };

	    for (int i = 0; i < priceRanges.length; i += 2) {
	        if (currentBidPrice.compareTo(priceRanges[i]) >= 0 && currentBidPrice.compareTo(priceRanges[i + 1]) <= 0) {
	            return increments[i / 2];
	        }
	    }

	    return new BigDecimal("100.00");
	}
	
	/*
	 * check the db for anyone that has auto bidding set up for an auction and place the according bids
	 * @param   the listing
	 * @param   the auction
	 * @param   the current bid price on the item
	 * @param   whether the most recent bid placed was manual or automatic
	 */
	private void placeAutoBids(MarketListing listing, Auction auction, BigDecimal currentBidPrice, boolean manualBid) {
		List<AutoBid> autobids = auctionService.getAutoBidsForListing(auction); // collection of every user that has auto bidding setup for the specific product

		// If a manual bid was placed, look for auto bids
	    if (manualBid) {
	    	if(autobids != null) {
		    	for (AutoBid bidEntry : autobids) { // iterate through list of auto bidders
		    		// auction variables
			        BigDecimal userMaxPrice = bidEntry.getMaxBid();
			        Auction autoAuction = listing.getAuction();
			        BigDecimal autoCurrentPrice = autoAuction.getCurrentBid();
			        BigDecimal autoIncrement = calculateIncrement(autoCurrentPrice);
			        BigDecimal potentialBid = autoCurrentPrice.add(autoIncrement);
		
			        if (userMaxPrice.compareTo(currentBidPrice) > 0 && userMaxPrice.compareTo(potentialBid) >= 0) { // make sure the potential bid is under the user's max price
			        	// place a bid
			        	auctionService.bid(auction, bidEntry.getBidder(), autoIncrement);
			            autoAuction.setCurrentBid(potentialBid);
			            marketRepository.save(listing);
			        }
			    }
	    	}
	    }
	    
	    // If an auto bid was placed, make the higher auto bidder raise the bid to the lower auto bid price	    
	    if (!manualBid) {	        
	    	if(autobids.size() >= 2) {
	    		// initialize info on the bidder with the higher price
	    		User highestAutoBidder = null;
		        BigDecimal higherAutoBid = autobids.get(0).getMaxBid();
		        long highBidderId = -1;
		        
		        // initialize info on the bidder with the lower price
		        User lowestAutoBidder = null;
		        BigDecimal lowerAutoBid = BigDecimal.valueOf(Double.MAX_VALUE);
		        long lowBidderId = -1;
		        
		        for (AutoBid bidEntry : autobids) { // loop through the auto bids
		        	if (bidEntry.getMaxBid().compareTo(higherAutoBid) > 0) { // check if the value is higher than the current highest bid
		        		// populate data
		        		higherAutoBid = bidEntry.getMaxBid();
		        		highestAutoBidder = bidEntry.getBidder();
		        		highBidderId = bidEntry.getId();
		        	}
		        	if (bidEntry.getMaxBid().compareTo(lowerAutoBid) < 0 && !bidEntry.getBidder().equals(highestAutoBidder)) { // check if the value is higher than the current lowest bid
		        		// populate data
		        		lowerAutoBid = bidEntry.getMaxBid();
		        		lowestAutoBidder = bidEntry.getBidder();
		        		lowBidderId = bidEntry.getId();
		        	}
		        }
		        
		        BigDecimal autoIncrement = calculateIncrement(lowerAutoBid); // set up bid increment
		        BigDecimal newBid = autoIncrement.add(lowerAutoBid); // add bid increment to the lower bid ceiling
		        
		        auctionService.bid(auction, highestAutoBidder, newBid); // bid on the product
		        auction.setCurrentBid(newBid); // update the bid price
		        marketRepository.save(listing); // save the listing
		        
		        auctionService.removeAutoBid(lowBidderId); // remove the lower bidder from the table
	    	}
	    }   
	}
	
	@GetMapping("/uniqueBiddersCount/{id}")
	public ResponseEntity<String> getUniqueBiddersCount(@PathVariable Long id) {
	    MarketListing marketListing = marketRepository.findById(id).orElse(null);
	    if (marketListing == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Listing not found");
	    }
	    int uniqueBidderCount = auctionService.countUniqueBiddersForListing(marketListing);
	    return ResponseEntity.ok(String.valueOf(uniqueBidderCount));
	}
	
	/* @GetMapping
	public String getAllAuctions(Model model) {
		List<Auction> auctions = auctionService.getAllAuctions();
		model.addAttribute("auctions", auctions);
		return "auctions";
	} */
	 
	 @GetMapping("/totalBidsCount/{id}")
	 public ResponseEntity<String> getTotalBidsCount(@PathVariable Long id) {
	     MarketListing marketListing = marketRepository.findById(id).orElse(null);
	     if (marketListing == null) {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Listing not found");
	     }
	     int totalBidsCount = auctionService.getTotalBidsForListing(marketListing);
	     return ResponseEntity.ok(String.valueOf(totalBidsCount));
	 }
	 
     @GetMapping("/isHighestBidder/{marketListingId}")
     @ResponseBody
     public Map<String, Boolean> isHighestBidder(@PathVariable Long marketListingId, Principal principal) {
         MarketListing marketListing = marketRepository.findById(marketListingId).orElse(null);

         if (marketListing == null) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Market listing not found");
         }

         Auction auction = marketListing.getAuction();
         Bid mostRecentBid = auctionService.findHighestBidForAuction(auction);

         Map<String, Boolean> response = new HashMap<>();
         if (mostRecentBid != null && mostRecentBid.getBidder().getUsername().equals(principal.getName())) {
             response.put("isHighestBidder", true);
         } else {
             response.put("isHighestBidder", false);
         } 
         return response;
     }
     
     @GetMapping("/showUniqueBidders/{id}")
     public ResponseEntity<List<String>> getUniqueBidders(@PathVariable Long id) {
         MarketListing marketListing = marketRepository.findById(id).orElse(null);
         if (marketListing == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
         }
         Set<User> uniqueBidders = auctionService.findUniqueBiddersForListing(marketListing);
         List<String> bidderNames = uniqueBidders.stream().map(User::getUsername).collect(Collectors.toList());
         return ResponseEntity.ok(bidderNames);
     }
     
     @GetMapping("/bidsForListing/{id}")
     public ResponseEntity<List<BidDTO>> getBidsForListing(@PathVariable Long id) {
         try {
             MarketListing marketListing = marketRepository.findById(id).orElse(null);
             if (marketListing == null) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
             }
             List<Bid> bids = bidRepository.findByAuction(marketListing.getAuction());
             
             List<BidDTO> bidDTOs = bids.stream()
                                     .map(bid -> new BidDTO(bid.getBidder().getUsername(), bid.getBidAmount()))
                                     .collect(Collectors.toList());
             
             for (Bid bid : bids) {
                 System.out.println("Bidder: " + bid.getBidder().getUsername() + ", Amount: " + bid.getBidAmount());
             }
             
             return ResponseEntity.ok(bidDTOs);
         
         } catch (Exception e) {
             e.printStackTrace(); // log the error
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
         }
     }
     
     static class BidDTO {
    	    private String username;
    	    private BigDecimal bidAmount;

    	    public BidDTO(String username, BigDecimal bigDecimal) {
    	        this.username = username;
    	        this.bidAmount = bigDecimal;
    	    }

    	    public String getUsername() {
    	        return username;
    	    }

    	    public void setUsername(String username) {
    	        this.username = username;
    	    }

    	    public BigDecimal getBidAmount() {
    	        return bidAmount;
    	    }

    	    public void setBidAmount(BigDecimal bidAmount) {
    	        this.bidAmount = bidAmount;
    	    }
    	}
}


