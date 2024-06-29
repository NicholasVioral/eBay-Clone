package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.apiclub.captcha.Captcha;
import edu.sru.cpsc.webshopping.controller.billing.DirectDepositController;
import edu.sru.cpsc.webshopping.controller.billing.PaymentDetailsController;
import edu.sru.cpsc.webshopping.controller.billing.PaypalController;
import edu.sru.cpsc.webshopping.controller.billing.SellerRatingController;
import edu.sru.cpsc.webshopping.domain.billing.BankAddress;
import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.Paypal;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.secure.CaptchaUtil;
import edu.sru.cpsc.webshopping.service.GroupService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.service.WatchlistService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@RestController
public class UserController {
	//private User Currently_Logged_In;
	private UserRepository userRepository;
	private StatisticsDomainController statControl;
	private PaypalController paypalController;
	private PaymentDetailsController paymentDetailsController;
	private DirectDepositController directDepositDetailsController;
	private SellerRatingController sellerRatingController;
	private UtilityController util;
	private WatchlistService watchlistService;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UserService userService;
	
	@Autowired
    private GroupService groupService;
		
	UserController(UserRepository userRepository,StatisticsDomainController statControl,
			PaymentDetailsController paymentDetailsController, DirectDepositController directDepositDetailsController,
			SellerRatingController sellerRatingController,UtilityController util, PaypalController paypalController,
			WatchlistService watchlistService) {
		this.userRepository = userRepository;
		this.statControl = statControl;
		this.paymentDetailsController = paymentDetailsController;
		this.directDepositDetailsController = directDepositDetailsController;
		this.sellerRatingController = sellerRatingController;
		this.util = util;
		this.paypalController = paypalController;
		this.watchlistService = watchlistService;
	}
	
	

	/**
	 * User of Currently_Logged_In_User in database has MarketListing added to their list of wishlisted items
	 * @param marketListing MarketListing to add to wishlist
	 * @exception IllegalStateException if no user is logged in
	 * @exception IllegalArgumentException if the wishlisted Widget is not found in the database
	 */
	
	
	@PostMapping("/add-to-wishlist")
	@Transactional
	public void addToWishlist(@Validated MarketListing marketListing, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		if (user == null) {
			throw new IllegalStateException("User not logged in when attempting to add new Widget to wishlist.");
		}
		MarketListing addedWidget = entityManager.find(MarketListing.class, marketListing.getId());

		// add product to user's watchlist
		watchlistService.watchlistAdd(addedWidget, user);
		
		//update the user
		userRepository.save(user);
	}
	
	/**
	 * User of Currently_Logged_In_User in database has MarketListing removed from their list of wishlisted items
	 * @param marketListing MarketListing to remove from wishlist
	 * @exception IllegalStateException if no user is logged in
	 * @exception IllegalArgumentException if the wishlisted Widget is not found in the database
	 */
	@PostMapping("/remove-from-wishlist")
	@Transactional

	public void removeFromWishlist(@Validated MarketListing marketListing, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		if (user == null) {
			throw new IllegalStateException("User not logged in when attempting to remove Widget from wishlist.");
		}
		MarketListing delWidget = entityManager.find(MarketListing.class, marketListing.getId());
		
		// remove product from user's watchlist
		watchlistService.watchlistRemove(delWidget, user);
		
		// update the user
		userRepository.save(user);
	}
	
	/**
	 * Saves or updates the current Paypal form of the user
	 * Paypal is encoded using BCryptPasswordEncoder and then stored in the database
	 * @param paypal new Paypal to save
	 * @exception IllegalStateException is thrown if the user is not logged in
	 */
	@PostMapping("/update-paypal-details")
	@Transactional
	public void updatePaypalDetails(@Validated Paypal paypal, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		if (user == null) {
			throw new IllegalStateException("Error updating payment details: User not logged in.");
		}
		paypal.setPaypalLogin(passwordEncoder.encode(paypal.getPaypalLogin()));
		paypal.setPaypalPassword(passwordEncoder.encode(paypal.getPaypalPassword()));
		if (user.getPaypal() == null) {
			entityManager.persist(paypal);
			user.setPaypal(paypal);
			entityManager.merge(user);
		}
		else {
			Paypal curr = entityManager.find(Paypal.class, user.getPaypal().getId());
			curr.transferFields(paypal);
			user.setPaypal(curr);
			entityManager.merge(paypal);
		}
		user.setPaypal(paypal);
	}
	
	/**
	 * Saves or updates the current PaymentDetails of the user
	 * PaymentDetails is encoded using BCryptPasswordEncoder and then stored in the database
	 * @param details new PaymentDetails to save
	 * @exception IllegalStateException is thrown if the user is not logged in
	 */
	@PostMapping("/update-default-payment-details") 
	@Transactional
	public void updateDefaultPaymentDetails(@Validated PaymentDetails details, Principal principal) {
		System.out.println("update payment details database function called");
		User user = userService.getUserByUsername(principal.getName());
		if (user == null) {
			throw new IllegalStateException("Error updating payment details: User not logged in.");
		}
		// Encode fields
		details.setCardholderName(details.getCardholderName());
		details.setCardNumber(passwordEncoder.encode(details.getCardNumber()));
		details.setLast4Digits(details.getLast4Digits());
		details.setCardType(details.getCardType());
		details.setExpirationDate(details.getExpirationDate());
		details.setSecurityCode(passwordEncoder.encode(details.getSecurityCode()));
		details.setBillingAddress(details.getBillingAddress());
		// No assigned details - add to user
		if (user.getPaymentDetails() == null) {
			entityManager.persist(details);
			user.setDefaultPaymentDetails(details);
			entityManager.merge(user);
		}
		else {
			PaymentDetails curr = entityManager.find(PaymentDetails.class, user.getDefaultPaymentDetails().getId());
			curr.transferFields(details);
			user.setDefaultPaymentDetails(curr);
			entityManager.merge(user);
		}
		user.setDefaultPaymentDetails(details);
	}
	
	/**
	 * Deletes the Paypal associated with the user
	 */
	@Transactional
	public void deletePaypal(User user) {
		Paypal details = user.getPaypal();
		user.setPaypal(null);
		userRepository.save(user);
		paypalController.deletePaypalDetails(details);
	}
	
	/**
	 * Deletes the DirectDepositDetails associated with the user
	 */
	@Transactional
	public void deleteDirectDepositDetails(User user) {
		DirectDepositDetails details = user.getDirectDepositDetails();
		user.setDirectDepositDetails(null);
		userRepository.save(user);
		directDepositDetailsController.deleteDirectDepositDetails(details);
	}
	
	/**
	 * Returns true if the passed Paypal information matches the currently logged in User's Paypal
	 * Returns false if they do not match
	 * The comparison is done using the BCryptPasswordEncoder matches functionality
	 * @return boolean
	 * @exception IllegalStateException is thrown if the user is not logged in
	 * @exception IllegalStateException is thrown if the user has no saved Paypal
	 */
	@GetMapping("/verify-paypal-details")
	@Transactional
	public boolean verifyPaypalDetails(@Validated Paypal details, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		if (user == null) {
			throw new IllegalStateException("No logged in user when attempting to verify payment details.");
		}
		else if (user.getPaypal() == null) {
			throw new IllegalStateException("User does not have an added Paypal for verifying.");
		}
		Paypal encodedDetails = user.getPaypal();
		boolean isValid = true;
		isValid = isValid && passwordEncoder.matches(details.getPaypalLogin(), encodedDetails.getPaypalLogin());
		isValid = isValid && passwordEncoder.matches(details.getPaypalPassword(), encodedDetails.getPaypalPassword());
		return isValid;
	}
	
	/**
	 * Saves or updates the current DirectDepositDetails of the user
	 * DirectDepositDetails is encoded using BCryptPasswordEncoder and then stored in the database
	 * @param details new DirectDepositDetails to save
	 * @exception IllegalStateException is thrown if the user is not logged in
	 */
	@PostMapping("/update-direct-deposit-details") 
	@Transactional
	public void updateDirectDepositDetails(@Validated DirectDepositDetails details, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		System.out.println("update direct deposit details database function called");
		if (user == null) {
			throw new IllegalStateException("Error updating payment details: User not logged in.");
		}
		// Encode fields
		// details.setAccountholderName(passwordEncoder.encode(details.getAccountholderName()));
		details.setAccountNumber(passwordEncoder.encode(details.getAccountNumber()));
		details.setRoutingNumber(passwordEncoder.encode(details.getRoutingNumber()));
		//details.setBankName(passwordEncoder.encode(details.getBankName()));

		//save bank address
		//entityManager.persist(details.getBankAddress());

		// No assigned details - add to user
		if (user.getDirectDepositDetails() == null) {
			entityManager.persist(details);
			user.setDirectDepositDetails(details);
			entityManager.merge(user);
		}
		else {
			DirectDepositDetails curr = entityManager.find(DirectDepositDetails.class, user.getDirectDepositDetails().getId());
			if(curr == null) {
				return;
			}
			curr.transferFields(details);
			entityManager.merge(curr);
			user.setDirectDepositDetails(curr);
			entityManager.merge(user);
		}
		//user.setDirectDepositDetails(details);
	}
	
	/**
	 * Returns true if the passed details matches the currently logged in User's DirectDepositDetails
	 * Returns false if they do not match
	 * The comparison is done using the BCryptPasswordEncoder matches functionality
	 * @param details the raw PaymentDetails to compare with the stored PaymentDetails
	 * @return boolean
	 * @exception IllegalStateException is thrown if the user is not logged in
	 * @exception IllegalStateException is thrown if the user has no saved PaymentDetails
	 */
	@GetMapping("/verify-direct-deposit-details")
	@Transactional
	public boolean verifyDirectDepositDetails(@Validated DirectDepositDetails details, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		if (user == null) {
			throw new IllegalStateException("No logged in user when attempting to verify payment details.");
		}
		else if (user.getDirectDepositDetails() == null) {
			throw new IllegalStateException("User does not have an added PaymentDetails for verifying.");
		}
		DirectDepositDetails encodedDetails = user.getDirectDepositDetails();
		boolean isValid = true;
		isValid = isValid && passwordEncoder.matches(details.getAccountholderName(), encodedDetails.getAccountholderName());
		isValid = isValid && passwordEncoder.matches(details.getAccountNumber(), encodedDetails.getAccountNumber());
		isValid = isValid && passwordEncoder.matches(details.getRoutingNumber(), encodedDetails.getRoutingNumber());
		return isValid;
	}
	
	// Basic CRUD functions
	@RequestMapping("/get-user") 
	public User getUser(@PathVariable("id") long id, Model model) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID passed to find a user"));
		return user;
	}
	
	@RequestMapping("/get-username") 
	public User getUserByUsername(@PathVariable("username") String name) {
		User user = userRepository.findByUsername(name);
		return user;
	}
	
	@RequestMapping("/get-email") 
	public User getUserByEmail(@PathVariable("email") String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	@RequestMapping("/get-all-users") 
	public Iterable<User> getAllUsers() {
		Iterable<User> users = userRepository.findAll();
		return users;
	}
	@Autowired
	private PasswordEncoder passwordEncoder;
	@PostMapping("/add-user")
	public User addUser(@Valid User user, BindingResult result) {
		// Find specific errors
		// Verifies that the giver user.creationDate is in a valid format
		//LocalDate.parse(user.getCreationDate());
		// Find other errors determined by the result
		if (result.hasErrors()) {
			// Temporary - just returns to the website index
			throw new IllegalArgumentException();
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setPasswordconf(util.randomStringGenerator());
		
		Statistics stats = new Statistics(StatsCategory.ACCOUNTCREATION,1);
		stats.setDescription("Account with username: " + user.getUsername() + " was created");
		statControl.addStatistics(stats);
		
		return userRepository.save(user);
	}
	@PostMapping("/edit-user")
	public User editUser(@Valid User user, String oldPass) {
		// Find specific errors
		// Verifies that the giver user.creationDate is in a valid format
		//LocalDate.parse(user.getCreationDate());
		// Find other errors determined by the result
		
		// only change the password if the new one if different from the old
		if (user.getPassword() != oldPass) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		user.setPasswordconf(util.randomStringGenerator());
		return userRepository.save(user);
	}
	
	@PostMapping("/edit-user/{id}/{returnPath}")
	public void updateUser(@PathVariable("id") long id, @PathVariable("returnPath") String returnPath,
							 @Validated User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			// Temporary - just returns to the website index
			throw new IllegalArgumentException();
		}
		
		userRepository.save(user);
	}
	
	@PostMapping("/delete-user/{id}")
	public void deleteUser(@PathVariable("id") long id, Model model) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID passed to delete a User"));
		Statistics stats = new Statistics(StatsCategory.ACCOUNTDELETED,1);
		stats.setDescription("Account with username: " + user.getUsername() + " was deleted");
		statControl.addStatistics(stats);
		userRepository.delete(user);
	}
	// End of basic CRUD functions

	/* public User getCurrently_Logged_In() {
		// https://stackoverflow.com/questions/31159075/how-to-find-out-the-currently-logged-in-user-in-spring-boot
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		// added this check to not allow anonymous authentication
		if (!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			return Currently_Logged_In;
		}
		return null;
		
		return Currently_Logged_In;
	}

	public void setCurrently_Logged_In(User currently_Logged_In) {
		Currently_Logged_In = currently_Logged_In;
	} */
	
	public SellerRating getSellerRating(User user) {
		if (user == null) {
			return null;
		}
		else {
			return user.getSellerRating();
		}
	}
	
	void getCaptcha(User user) {
		Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
		user.setHiddenCaptcha(captcha.getAnswer());
		user.setCaptcha(""); // value entered by the User
		user.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
		
	}
	

}
