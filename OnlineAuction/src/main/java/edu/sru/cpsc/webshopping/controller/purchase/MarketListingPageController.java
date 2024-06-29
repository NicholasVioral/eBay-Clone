package edu.sru.cpsc.webshopping.controller.purchase;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sru.cpsc.webshopping.controller.EmailController;
import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.MessagingController;
import edu.sru.cpsc.webshopping.controller.StatisticsDomainController;
import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.UserListDomainController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.controller.WidgetImageController;
import edu.sru.cpsc.webshopping.controller.billing.SellerRatingController;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.MarketListingStatus;
import edu.sru.cpsc.webshopping.domain.market.OfferNotification;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.misc.Notification;
import edu.sru.cpsc.webshopping.domain.misc.Revenue;
import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.user.UserList;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetAttribute;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.market.OfferNotificationRepository;
import edu.sru.cpsc.webshopping.repository.misc.NotificationRepository;
import edu.sru.cpsc.webshopping.service.CategoryService;
import edu.sru.cpsc.webshopping.service.NotificationService;
import edu.sru.cpsc.webshopping.service.StatisticsService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.service.WatchlistService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Manages the functionality of the MarketListing page This page is used for ing
 * and interacting with market listings
 */
@Controller
public class MarketListingPageController {
	MarketListingDomainController marketListingController;
	PurchaseShippingAddressPageController shippingPage;
	MarketListing heldListing;
	UserController userController;
	WidgetImageController widgetImageController;
	EmailController emailController;
	ConfirmPurchasePageController purchaseController;
	SellerRatingController ratingController;
	UserListDomainController userListController;
	WatchlistService watchlistService;
	// Repositories passed to ConfirmPurchasePage
	TransactionController transController;
	WidgetController widgetController;
	private String page;
	private Widget tempWidget;

	Logger log = LoggerFactory.getLogger(MarketListingPageController.class);

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	StatisticsDomainController statControl;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private StatisticsDomainController statController;

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private MarketListingRepository marketListingRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private OfferNotificationRepository offerNotificationRepository;

	@Autowired
	private MessagingController messagingController;

	public MarketListingPageController(MarketListingDomainController marketListingController,
			TransactionController transController, UserController userController,
			PurchaseShippingAddressPageController shippingPage, EmailController emailController,
			WidgetController widgetController, WidgetImageController widgetImageController,
			SellerRatingController ratingController, UserListDomainController userListController,
			ConfirmPurchasePageController purchaseController, WatchlistService watchlistService) {
		this.marketListingController = marketListingController;
		this.transController = transController;
		this.userController = userController;
		this.purchaseController = purchaseController;
		this.shippingPage = shippingPage;
		this.widgetImageController = widgetImageController;
		this.emailController = emailController;
		this.widgetController = widgetController;
		this.ratingController = ratingController;
		this.userListController = userListController;
		this.watchlistService = watchlistService;
	}

	/** Reloads the page model data */
	public void reloadModel(Model model, User user) {
		model.addAttribute("currListing", heldListing);
		model.addAttribute("widget", heldListing.getWidgetSold());
		model.addAttribute("category", heldListing.getWidgetSold().getCategory());

		// Check if User is the owner of the market listing
		model.addAttribute("user", user);

		// If user is the seller or an admin, give them access to modify the listing
		if (user.getId() == heldListing.getSeller().getId() || user.getRole().equals("ROLE_ADMIN")) {
			model.addAttribute("isBuyer", false);
		} else {
			model.addAttribute("isBuyer", true);
			Transaction newTrans = new Transaction();
			model.addAttribute("newTransaction", newTrans);
		}

		// Check if the user is able to purchase widgets
		if (user.getPaymentDetails() == null) {
			model.addAttribute("canBuy", false);
		} else {
			model.addAttribute("canBuy", true);
		}

		model.addAttribute("sellerRating", heldListing.getSeller().getSellerRating());
		model.addAttribute("seller", heldListing.getSeller());
	}

	/**
	 * Opens the viewMarketListingPage
	 *
	 * @param marketListingId the id of the marketListing to view
	 * @param model           The page model - passed by dependency injection
	 * @return the viewMarketListing page string
	 */
	@RequestMapping({ "/viewMarketListing/{marketListingId}" })
	public String viewMarketListingPage(@PathVariable("marketListingId") long marketListingId, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		heldListing = marketListingController.getMarketListing(marketListingId);

		WidgetImage[] widgetImages = widgetImageController.getwidgetImageByMarketListing(heldListing);

		// TODO: Open an error page
		// TODO: Set user status by reading from a User server
		if (heldListing == null || heldListing.isDeleted() || heldListing.getQtyAvailable() == 0) {
			return "redirect:/homePage";
		}

		if (heldListing.isDeleted()) {
			throw new IllegalArgumentException("Attempted to access an invalid Market Listing!");
		}

		if (user.getId() != heldListing.getSeller().getId()) {
			statisticsService.incrementPageView(heldListing.getId());
		}

		UserList myList = new UserList();
		myList.setOwner(user);

		String[] widgetImgData = new String[widgetImages.length];

		// add the widget attributes to the model, such as name, description, color etc.
		Widget widget = heldListing.getWidgetSold();
		Set<WidgetAttribute> widgetAttributes = widget.getAttributes();

		for (int x = 0; x < widgetImages.length; x++) {
			Blob imageData = widgetImages[x].getImageData();
			int blobLength;

			try {
				blobLength = (int) imageData.length();
				widgetImgData[x] = Base64.getEncoder().encodeToString(imageData.getBytes(1, blobLength));
			} catch (SQLException e) {
				widgetImgData[x] = null;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Generate the category stack for the widget
		// TODO UNCOMMENT WHENEVER MORE CATEGORIES ARE ADDED
//    List<String> categoryStack = categoryService.generateCategoryStack(widget.getCategory());
		List<String> categoryStack = new ArrayList<>();
		categoryStack.add("Vehicle Parts & Accessories");
		// check if the product is in the user's watchlist
		boolean foundInWatchlist = false;
		Set<MarketListing> wishlist = user.getWishlistedWidgets();
		for (MarketListing element : wishlist) {
			if (marketListingId == element.getId()) {
				foundInWatchlist = true;
			}
		}

		// get how many people are watching the item
		Long userCount = watchlistService.countUsersWithMarketListingInWatchlist(marketListingId);

		if (heldListing.hasAcceptedOffer()) {
			model.addAttribute("acceptedOffer",
					offerNotificationRepository.findById(heldListing.getAcceptedOfferId()).get());
		} else {
			List<OfferNotification> offers = offerNotificationRepository.findAllByMarketListingId(marketListingId)
					.stream().filter(x -> !x.isRejected()).toList();

			if (!offers.isEmpty() && heldListing.isSeller(user.getId())) {
				model.addAttribute("offerNotifications", offers);
				
			}
			
			model.addAttribute("acceptedOffer", new OfferNotification());
		}

		model.addAttribute("hasAcceptedOffer", heldListing.hasAcceptedOffer());
		model.addAttribute("categories", categoryStack);
		model.addAttribute("images", widgetImgData);
		model.addAttribute("attributes", widgetAttributes);
		model.addAttribute("foundInWatchlist", foundInWatchlist);
		model.addAttribute("currentUser", user);
		model.addAttribute("userCount", userCount);
		model.addAttribute("qtyBought", 1);
		model.addAttribute("pageViews", heldListing.getPageViews());

		reloadModel(model, user);
		return "viewMarketListing";
	}

	/**
	 * Attempts to purchase from a MarketListing Opens the next page in the
	 * purchasing pipeline - submitting the shipping address
	 *
	 * @param newTransaction a partially filled out Transaction holding the number
	 *                       of items to buy
	 * @param model          the page model - passed by dependency injection
	 * @return the confirmShippingPage
	 */
	@PostMapping({ "/viewMarketListing/attemptPurchase" })
	public String attemptPurchase(@Validated Transaction newTransaction, BindingResult result, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		
		// Initial validation - validation must also be done when updating the database
		if (newTransaction.getQtyBought() > heldListing.getQtyAvailable()) {
			System.out.println("failure 1");
			result.addError(new FieldError("newTransaction", "qtyBought",
					"Cannot buy more items than the Market Listing quantity."));
		}
		
		// Errors found - refresh page
		if (result.hasErrors()) {
			reloadModel(model, user);
			return "viewMarketListing";
		}
		
		BigDecimal pricePerItem = heldListing.getPricePerItem();
		
		OfferNotification acceptedOffer = null;
		
		// load accepted offer
		if (heldListing.hasAcceptedOffer()) {
			log.info("Detected accepted offer");
			acceptedOffer = offerNotificationRepository.findById(heldListing.getAcceptedOfferId()).get();
			
			if (user.getId() == acceptedOffer.getPotentialBuyerUserId()) {
				log.info("Adjusting Price based on offer");
				pricePerItem = new BigDecimal(acceptedOffer.getOfferAmount());
			}
		}
		
		ShippingAddress address;
		Transaction purchaseAttempt = new Transaction();
		
		purchaseAttempt.setBuyer(user);
		purchaseAttempt.setSeller(heldListing.getSeller());
		purchaseAttempt.setMarketListing(heldListing);
		purchaseAttempt.setQtyBought(newTransaction.getQtyBought());
		purchaseAttempt.setTotalPriceBeforeTaxes(pricePerItem.multiply(BigDecimal.valueOf(newTransaction.getQtyBought())));
		
		// Add shipping entry if user confirms purchase on next page
		purchaseAttempt.setShippingEntry(null);
		purchaseAttempt.setPaymentDetails(null);

		model.addAttribute("user", user);
		
		return this.purchaseController.initializePurchasePage(heldListing, purchaseAttempt, model, principal);
	}

	/**
	 * Adds the current item to the User's wishlist
	 *
	 * @param the market listing id of the current product
	 * @return the current viewMarketListing page
	 */
	@PostMapping({ "/viewMarketListing/wishlistItem/{marketListingId}" })
	public String wishlistItem(@PathVariable("marketListingId") long marketListingId, Model model,
			Principal principal) {
		// define held listing as the targeted listing bby passing in the market listing
		// ID
		heldListing = marketListingController.getMarketListing(marketListingId);
		// call addToWishlist in UserController.java and pass in the widget assigned to
		// heldListing

		userController.addToWishlist(heldListing, principal);

		// redirect the user to the listing for the widget
		return "redirect:/viewMarketListing/" + heldListing.getId();
	}

	/**
	 * Removes the current item from the User's wishlist
	 *
	 * @param the market listing id of the current product
	 * @return the current viewMarketListing page
	 */
	@PostMapping({ "/viewMarketListing/delWishlistItem/{marketListingId}" })
	public String delWishlistItem(@PathVariable("marketListingId") long marketListingId, Model model,
			Principal principal) {
		// define held listing as the targeted listing bby passing in the market listing
		// ID
		heldListing = marketListingController.getMarketListing(marketListingId);
		// call removeFromWishlist in UserController.java and pass in the widget
		// assigned to heldListing
		userController.removeFromWishlist(heldListing, principal);
		// redirect the user to the listing
		return "redirect:/viewMarketListing/" + heldListing.getId();
	}

	/**
	 * Gets all of the User's wishlisted items
	 *
	 * @param the market listing id of the current product
	 * @return the current viewMarketListing page
	 */
	@RequestMapping({ "/viewWishlist" })
	public String getWishlist(Principal principal) {
		// get the currently logged in user
		User user = userService.getUserByUsername(principal.getName());

		user.getWishlistedWidgets();

		// redirect the user to the watchlist.html page
		return "watchlist";
	}

	/**
	 * Return to market listings
	 *
	 * @return the homePage
	 */
	@PostMapping({ "/viewMarketListing/returnToListings" })
	public String returnToListings() {
		return "redirect:/homePage";
	}

	/**
	 * Administrative/seller functionality Edits the number of items, or its price
	 *
	 * @param marketListing holding the new MarketListing values
	 * @return returns to the index
	 */
	@PostMapping({ "/viewMarketListing/editListing" })
	public String editListing(@Validated MarketListing marketListing, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());

		marketListingController.editMarketListing(marketListing, user);

		return "redirect:/viewMarketListing/" + marketListing.getId();
	}

	/**
	 * Deletes a market listing for listing owners
	 *
	 * @return returns to the index
	 */
	@RequestMapping({ "/viewMarketListing/deleteListing/{id}" })
	public String deleteListing(@PathVariable long id, Model model, Principal principal) {
		// go to marketListingController and delete the listing (it has the repository
		// there)
		marketListingController.deleteMarketListing(id);
		// redirect - if the user is an admin send to their search page
		User user = userService.getUserByUsername(principal.getName());
		if (user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_CUSTOMERSERVICE")
				|| user.getRole().equals("ROLE_TECHNICALSERVICE") || user.getRole().equals("ROLE_SECURITY")
				|| user.getRole().equals("ROLE_SALES") || user.getRole().equals("ROLE_ADMIN_SHADOW")
				|| user.getRole().equals("ROLE_HELPDESK_ADMIN") || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
			return "redirect:/searchWidgetButton";
		}
		// otherwise return user home
		return "redirect:/homePage";
		// this code could potentially be improved to track where the user deleted it
		// from and return
		// to that page specifically. look into doing that after overall site is more
		// functional?
	}

	/**
	 * Deletes a market listing for admins
	 *
	 * @return returns to the index
	 */
	@RequestMapping({ "/viewMarketListing/deleteListingAdmin/{id}" })
	public String deleteListingAdmin(@PathVariable long id, @RequestParam String reason, Model model,
			Principal principal) {
		// set up email notification information
		User sender = userService.getUserByUsername(principal.getName());
		MarketListing listing = marketListingController.getMarketListing(id);
		Widget widget = listing.getWidgetSold();
		User recipient = listing.getSeller();
		String content = // message format
				"\n" + "Hello " + recipient.getUsername() + ",\n\n" + "This message is informing you that your listing "
						+ widget.getName() + " has been removed.\n\n" + "The reason for removal is as follows:\n"
						+ reason + "\n\n" + "Sincerely,\nOfferly Team";
		Message message = new Message();
		message.setOwner(sender);
		message.setSender(sender.getUsername());
		message.setContent(content);
		message.setSubject("Market Listing Removal");
		message.setMsgDate();
		message.setReceiverName(recipient.getDisplayName());
		message.setReceiver(recipient);
		// send message
		emailController.messageEmail(recipient, sender, message);

		// log event
		StatsCategory cat = StatsCategory.LISTINGDELETED;
		Statistics stat = new Statistics(cat, 1);
		stat.setDescription(
				sender.getUsername() + " deleted the listing with ID: " + listing.getId() + " for reason: " + reason);
		statController.addStatistics(stat);

		// go to marketListingController and delete the listing (it has the repository
		// there)
		marketListingController.deleteMarketListing(id);

		// redirect - if the user is an admin send to their search page
		User user = userService.getUserByUsername(principal.getName());
		if (user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_CUSTOMERSERVICE")
				|| user.getRole().equals("ROLE_TECHNICALSERVICE") || user.getRole().equals("ROLE_SECURITY")
				|| user.getRole().equals("ROLE_SALES") || user.getRole().equals("ROLE_ADMIN_SHADOW")
				|| user.getRole().equals("ROLE_HELPDESK_ADMIN") || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
			return "redirect:/searchWidgetButton";
		}
		// otherwise return user home
		return "redirect:/homePage";
	}

	@RequestMapping("/acceptOffer/{id}")
	public String acceptOffer(@PathVariable long id) {
		OfferNotification offer = offerNotificationRepository.findById(id).get();
		offer.setAccepted(true);
		offer.setAcceptedOn(new Date());
		offerNotificationRepository.save(offer);

		MarketListing listing = marketListingRepository.findById(offer.getMarketListingId()).get();
		listing.acceptOffer(offer);
		listing.setListingStatus(MarketListingStatus.PENDING);
		marketListingRepository.save(listing);

		messagingController.notifyOfferAccepted(offer, listing.getWidgetSold().getName());

		return "redirect:/viewMarketListing/" + offer.getMarketListingId();
	}

	@RequestMapping("/rejectOffer/{id}")
	public String rejectOffer(@PathVariable long id) {
		OfferNotification offer = offerNotificationRepository.findById(id).get();
		offer.setRejected(true);
		offerNotificationRepository.save(offer);

		MarketListing listing = marketListingRepository.findById(offer.getMarketListingId()).get();
		messagingController.notifyOfferRejection(offer, listing.getWidgetSold().getName());

		return "redirect:/viewMarketListing/" + offer.getMarketListingId();
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
}
