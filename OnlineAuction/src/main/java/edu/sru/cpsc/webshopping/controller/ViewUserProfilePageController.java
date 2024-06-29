package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.service.CategoryService;
import edu.sru.cpsc.webshopping.service.UserService;

/**
 * Page for viewing the details of other users
 */
@Controller
public class ViewUserProfilePageController {
	// Database controllers
	private MarketListingDomainController listingController;
	private EmailController emailController;

	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	// Page data
	private User selectedUser;
	private MarketListing[] soldItems;
	private Vector<Vector<MarketListing>> itemsEachPage;
	private SellerRating rating;
	private int pageNumber;
	private boolean messagePaneOpen;
	// Configuration constant
	private final int NUM_LISTINGS_PER_PAGE = 4;
	
	ViewUserProfilePageController(MarketListingDomainController listingController, EmailController emailController) {
		this.listingController = listingController;
		this.emailController = emailController;
	}
	
	/**
	 * Loads model data based on the current state of the controller
	 * @param model the page model
	 */
	private void reloadPageModel(Model model, User user) {
		model.addAttribute("selectedUser", selectedUser);
		model.addAttribute("currUser", user);
		model.addAttribute("currPageItems", itemsEachPage.get(pageNumber - 1));
		model.addAttribute("selectedPageNum", pageNumber);
		model.addAttribute("soldItems", soldItems);
		model.addAttribute("numPages", itemsEachPage.size());
		model.addAttribute("rating", rating);
		model.addAttribute("messagePaneOpen", messagePaneOpen);
	}
	
	/**
	 * Opens a user's user profile
	 * @param userId the id of the user to load
	 * @param model the page model
	 * @return viewUserProfile
	 */
	@RequestMapping("/viewUserProfile/{userId}")
	public String openUserProfile(@PathVariable("userId") long userId, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("user", user);
		
		this.pageNumber = 1;
		this.selectedUser = userService.getUserById(userId);
		this.soldItems = listingController.getListingbyUser(selectedUser);
		this.soldItems = Arrays.stream(this.soldItems).filter(item -> !item.isDeleted()).toArray(MarketListing[]::new);
		this.rating = selectedUser.getSellerRating();
		this.messagePaneOpen = false;
		model.addAttribute("selectedUser", selectedUser);
		
		itemsEachPage = new Vector<Vector<MarketListing>>();
		if (soldItems.length == 0) {
			// If no sold items, add a blank page
			itemsEachPage.add(new Vector<MarketListing>());
		}
		else {
			int currItemOnPage = 0;
			for (MarketListing listing : soldItems) {
				// If sold items, add them to each page
				if (currItemOnPage == 0) 
					itemsEachPage.add(new Vector<MarketListing>());
				itemsEachPage.lastElement().add(listing);
				currItemOnPage = (currItemOnPage + 1) % NUM_LISTINGS_PER_PAGE;
				
				String category = categoryService.generateCategoryStack(listing.getWidgetSold().getCategory()).toString();
			    category = category.replaceAll("\\[","");
			    category = category.replaceAll("\\]","");
			    model.addAttribute("widgetCategory", category);
			    System.out.println("widget category test: " + category);
			}
		}
		
		if(this.rating == null) {
			this.rating = new SellerRating();
		}
		
		reloadPageModel(model,user);
		model.addAttribute("user", user);
		return "viewUserProfile";
	}
	
	/**
	 * Changes the current page of displayed market listings
	 * @param pageNumber a page number - indexed starting from 1
	 * @param model the page model
	 * @return viewUserProfile
	 */
	@RequestMapping("/viewUserProfile/changePage/{pageNumber}")
	public String changePageNumber(@PathVariable("pageNumber") int pageNumber, Model model, Principal principal) {
		// pageNumber is not read from page as zero indexed for user readability reasons
		User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("user", user);
		if (pageNumber <= 0 || pageNumber > itemsEachPage.size()) {
			throw new IllegalArgumentException("Invalid page number for viewUserProfilePage");
		}
		// Load updated model
		this.pageNumber = pageNumber;
		reloadPageModel(model, user);
		return "viewUserProfile";
	}
	
	/**
	 * Opens a MarketListing
	 * @param listingId the id of the listing to open
	 * @param model the page model
	 * @return /viewMarketListing/{listingId}
	 */
	@RequestMapping("/viewUserProfile/openListing/{listingId}")
	public String openListing(@PathVariable("listingId") long listingId, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("user", user);
		return "redirect:/viewMarketListing/" + listingId;
	}
	
	/**
	 * Opens the pane for sending messages
	 * @param model the page model
	 * @return viewUserProfile
	 */
	@RequestMapping("/viewUserProfile/openMessagePane")
	public String openMessagePane(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("user", user);
		System.out.println("open message pane");
		this.messagePaneOpen = true;
		reloadPageModel(model, user);
		return "viewUserProfile";
	}
	
	/**
	 * Closes the pane for sending messages
	 * @param model the page model
	 * @return viewUserProfile
	 */
	@RequestMapping("/viewUserProfile/closeMessagePane")
	public String closeMessagePane(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("user", user);
		this.messagePaneOpen = false;
		reloadPageModel(model, user);
		return "viewUserProfile";
	}
	
	
}
