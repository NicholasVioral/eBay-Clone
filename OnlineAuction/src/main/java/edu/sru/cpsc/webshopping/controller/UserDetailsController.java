package edu.sru.cpsc.webshopping.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.cpsc.webshopping.controller.billing.CardTypeController;
import edu.sru.cpsc.webshopping.controller.billing.PaymentDetailsController;
import edu.sru.cpsc.webshopping.controller.billing.StateDetailsController;
import edu.sru.cpsc.webshopping.domain.billing.BankAddress;
import edu.sru.cpsc.webshopping.domain.billing.BankAddress_Form;

/**
import edu.sru.cpsc.webshopping.controller.purchase.ApiException;
import edu.sru.cpsc.webshopping.controller.purchase.AutocompletePrediction;
import edu.sru.cpsc.webshopping.controller.purchase.GeoApiContext;
*/

import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails;
import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails_Form;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails_Form;
import edu.sru.cpsc.webshopping.domain.billing.Paypal;
import edu.sru.cpsc.webshopping.domain.billing.Paypal_Form;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress_Form;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.secure.TwoFactorAuthentication;
import edu.sru.cpsc.webshopping.service.AddressService;
import edu.sru.cpsc.webshopping.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


/**
 * Controller for the user account page. Allows users to edit their account information.
 * Interacts with user database.
 * @author NICK
 *
 */

// Enum for describing the currently displayed submenu
enum SUB_MENU {
	USER_DETAILS,
	PAYMENT_DETAILS,
	PAYPAL_DETAILS,
	DEPOSIT_DETAILS,
	SHIPPING_DETAILS
}

@Controller
public class UserDetailsController {
	@PersistenceContext
	private EntityManager entityManager;
	private UserRepository userRepository;
	private UserController userController;
	private TransactionController transController;
	private CardTypeController cardController;
	private String userName;
	private String displayName;
	private String creationDate;
	private String userDescription;
	private String email;
	private boolean addNewPD = false;
	private boolean updatePD = false;
	private boolean addNewSA = false;
	private boolean updateSA = false;
	private boolean reloginSA = false;
	private boolean loginErrorSA = false;
	private boolean reloginPD = false;
	private boolean loginErrorPD = false;
	private boolean delSA = false;
	private boolean delPD = false;
	private long id2PD = -1;
	private long updateIdPD = -1;
	private long id2SA;
	private long updateIdSA = -1;
	private long shippingDelId = -1;
	private long paymentDelId = -1;
	private PaymentDetailsController payDetCont;
	private SUB_MENU selectedMenu;
	private ShippingAddressDomainController shippingController;
	private StateDetailsController stateDetailsController;

	@Autowired
	private TwoFactorAuthentication twoFactorAuthentication;
	@Autowired
	private UserService userService;
	@Autowired
	private AddressService addressService;
	
	public UserDetailsController(UserController userController, UserRepository userRepository, 
			TransactionController transController, CardTypeController cardController,
			PaymentDetailsRepository payDetRepo,
			PaymentDetailsController payDetCont, ShippingAddressDomainController shippingController,
			StateDetailsController stateDetailsController)
	{
		this.userController = userController;
		this.userRepository = userRepository;
		this.cardController = cardController;
		this.payDetCont = payDetCont;
		this.transController = transController;
		this.shippingController = shippingController;
		this.stateDetailsController = stateDetailsController;
	}
	
	/**
	 * add the required attributes for displaying the userdetails page
	 * @param model
	 * @return
	 */
	@RequestMapping("/userDetails")
	public String userDetails(Model model, Principal principal)
	{
		User user = userService.getUserByUsername(principal.getName());
		loadUserData(model, user);
		// Model for updating Paypal details
		model.addAttribute("paypalDetails", new Paypal_Form());
		// Model for updating Payment Details
		model.addAttribute("paymentDetails", new PaymentDetails_Form());
		model.addAttribute("cardTypes", cardController.getAllCardTypes());
		// Model for updating Direct Deposit Details
		DirectDepositDetails_Form details = new DirectDepositDetails_Form();
		model.addAttribute("directDepositDetails", details);
		model.addAttribute("user", user);
		selectedMenu = SUB_MENU.USER_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		return "userDetails";
	}
	
	/**
	 * initialize the payment details page (used when opening payment details page from another page)
	 * resets login information so the user must do it again
	 * @param model
	 * @return
	 */
	@RequestMapping("/userDetails/initializePaymentDetails")
	public String initializePaymentDetails(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		loadUserData(model, user);
		addNewPD = false;
		updatePD = false;
		updateIdPD = -1;
		reloginPD = false;
		delPD = false;
		// Model for updating Paypal details
		model.addAttribute("paypalDetails", new Paypal_Form());
		// Model for updating Payment Details
		model.addAttribute("paymentDetails", new PaymentDetails_Form());
		model.addAttribute("cardTypes", cardController.getAllCardTypes());
		// Model for updating Direct Deposit Details
		DirectDepositDetails_Form details = new DirectDepositDetails_Form();
		ShippingAddress_Form billingAddress = new ShippingAddress_Form();
		BankAddress_Form bankAddress = new BankAddress_Form();
		model.addAttribute("directDepositDetails", details);
		model.addAttribute("billingAdress", billingAddress);
		model.addAttribute("bankAddress", bankAddress);
		model.addAttribute("user", user);
		if(user.getDefaultPaymentDetails() != null)
			model.addAttribute("defaultPaymentDetails", payDetCont.getPaymentDetail(user.getDefaultPaymentDetails().getId(), null));
		else
			model.addAttribute("defaultPaymentDetails", null);
		if(user.getPaymentDetails() != null && user.getPaymentDetails().isEmpty())
			model.addAttribute("savedDetails", null);
		else
			model.addAttribute("savedDetails", payDetCont.getPaymentDetailsByUser(user));
		model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));
		model.addAttribute("savedDirectDepositDetails", user.getDirectDepositDetails());
		model.addAttribute("states", stateDetailsController.getAllStates());
		model.addAttribute("addNew", addNewPD);
		model.addAttribute("updateId", updateIdPD);
		model.addAttribute("update", updatePD);
		model.addAttribute("relogin", reloginPD);
		model.addAttribute("delete", delPD);
		model.addAttribute("loginError", loginErrorPD);
		selectedMenu = SUB_MENU.PAYMENT_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		return "userDetails";
	}
	
	/**
	 * sets up the userdetails payment details page
	 * @param model
	 * @return
	 */
	@RequestMapping("/userDetails/paymentDetails")
	public String openPaymentDetails(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		loadUserData(model, user);
		// Model for updating Paypal details
		model.addAttribute("paypalDetails", new Paypal_Form());
		// Model for updating Payment Details
		model.addAttribute("paymentDetails", new PaymentDetails_Form());
		model.addAttribute("cardTypes", cardController.getAllCardTypes());
		// Model for updating Direct Deposit Details
		DirectDepositDetails_Form details = new DirectDepositDetails_Form();
		ShippingAddress_Form billingAddress = new ShippingAddress_Form();
		model.addAttribute("directDepositDetails", details);
		model.addAttribute("billingAdress", billingAddress);
		model.addAttribute("user", user);
		if(user.getDefaultPaymentDetails() != null)
			model.addAttribute("defaultPaymentDetails", payDetCont.getPaymentDetail(user.getDefaultPaymentDetails().getId(), null));
		else
			model.addAttribute("defaultPaymentDetails", null);
		if(user.getPaymentDetails() != null && user.getPaymentDetails().isEmpty())
			model.addAttribute("savedDetails", null);
		else
			model.addAttribute("savedDetails", payDetCont.getPaymentDetailsByUser(user));
		if(updateIdPD != -1){
			PaymentDetails detailsToUpdate = payDetCont.getPaymentDetail(updateIdPD, null);
			System.out.println("details to update: " + detailsToUpdate.getExpirationDate());
			model.addAttribute("detailsToUpdate", detailsToUpdate);
		}
		model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));
		model.addAttribute("savedDirectDepositDetails", user.getDirectDepositDetails());
		model.addAttribute("states", stateDetailsController.getAllStates());
		model.addAttribute("addNew", addNewPD);
		model.addAttribute("updateId", updateIdPD);
		model.addAttribute("update", updatePD);
		model.addAttribute("relogin", reloginPD);
		model.addAttribute("delete", delPD);
		model.addAttribute("loginError", loginErrorPD);
		selectedMenu = SUB_MENU.PAYMENT_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		return "userDetails";
	}
	
	/**
	 * initializes necessary information for the shipping details page as well as
	 * making it necessary for a user to relogin when modifying or adding details
	 * @param model
	 * @return
	 */
	@RequestMapping("/userDetails/initializeShippingDetails")
	public String initializeShippingDetails(Model model, Principal principal) {
		addNewSA = false;
		updateSA = false;
		updateIdSA = -1;
		delSA = false;
		reloginSA = false;
		User user = userService.getUserByUsername(principal.getName());
		
		loadUserData(model, user);
		model.addAttribute("loginError", loginErrorSA);
		model.addAttribute("shippingDetails", new ShippingAddress_Form());
		model.addAttribute("user", user);
		selectedMenu = SUB_MENU.SHIPPING_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		model.addAttribute("states", stateDetailsController.getAllStates());
		if(user.getDefaultShipping() != null)
			model.addAttribute("defaultShippingDetails", user.getDefaultShipping());
		else
			model.addAttribute("defaultShippingDetails", null);
		model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));
		model.addAttribute("savedDirectDepositDetails", user.getDirectDepositDetails());
		model.addAttribute("addNew", addNewSA);
		model.addAttribute("updateId", updateIdSA);
		model.addAttribute("update", updateSA);
		model.addAttribute("relogin", reloginSA);
		model.addAttribute("delete", delSA);
		return "userDetails";
	}
	
	/**
	 * Set up the necessary attributes for the shipping details page
	 * @param model
	 * @return
	 */
	@RequestMapping("/userDetails/shippingDetails")
	public String openShippingDetails(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		loadUserData(model, user);
		model.addAttribute("loginError", loginErrorSA);
		model.addAttribute("shippingDetails", new ShippingAddress_Form());
		model.addAttribute("user", user);
		selectedMenu = SUB_MENU.SHIPPING_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		model.addAttribute("states", stateDetailsController.getAllStates());
		if(user.getDefaultShipping() != null)
			model.addAttribute("defaultShippingDetails", user.getDefaultShipping());
		else
			model.addAttribute("defaultShippingDetails", null);
			
		model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));
		model.addAttribute("savedDirectDepositDetails", user.getDirectDepositDetails());
		model.addAttribute("addNew", addNewSA);
		model.addAttribute("updateId", updateIdSA);
		model.addAttribute("update", updateSA);
		model.addAttribute("relogin", reloginSA);
		model.addAttribute("delete", delSA);
		return "userDetails";
	}

	@PostMapping(value = "/addBillingAddress", params="submit")
	public String addShippingDetails(@Validated @ModelAttribute("billingAddress") ShippingAddress_Form details, @ModelAttribute("selectedMenu") SUB_MENU menu, BindingResult result, @RequestParam("stateId") String stateId, Model model, Principal principal) {
	
		details.setState(stateDetailsController.getState(stateId));
		User user = userService.getUserByUsername(principal.getName());

		if (result.hasErrors()) {
			// Add error messages
			if(!result.hasErrors() && shippingAddressConstraintsFailed(details))
				model.addAttribute("shippingError", "Address does not exist");
			model.addAttribute("shippingDetails", new ShippingAddress_Form());
			model.addAttribute("user", user);
			model.addAttribute("states", stateDetailsController.getAllStates());
			for (FieldError error : result.getFieldErrors()) {
				model.addAttribute(error.getField() + "Err", error.getDefaultMessage());
			}
			if(menu == SUB_MENU.PAYMENT_DETAILS)
				return "redirect:/userDetails/paymentDetails";
			else if(menu == SUB_MENU.DEPOSIT_DETAILS)
				return "redirect:/userDetails/depositDetails";
			else
				return "redirect:/userDetails";
		}

		ShippingAddress shipping = new ShippingAddress();
		details.setState(stateDetailsController.getState(stateId));
		shipping.buildFromForm(details);
		shipping.setUser(user);
		Set<ShippingAddress> SA = user.getShippingDetails();
		if(SA == null)
			SA = new HashSet<ShippingAddress>();
		SA.add(shipping);
		user.setShippingDetails(SA);
		shippingController.addShippingAddress(shipping, user);
		userService.updateUserProfile(user.getId(), user);
		if(menu == SUB_MENU.PAYMENT_DETAILS)
			return "redirect:/userDetails/paymentDetails";
		else if(menu == SUB_MENU.DEPOSIT_DETAILS)
				return "redirect:/userDetails/depositDetails";
		else
			return "redirect:/userDetails";
	}

	@PostMapping(value = "/addBankAddress", params="submit")
	public String addBankAddress(@Validated @ModelAttribute("bankAddress") BankAddress_Form details, @ModelAttribute("selectedMenu") SUB_MENU menu, BindingResult result, @RequestParam("stateId") String stateId, Model model, Principal principal) {
	
		details.setState(stateDetailsController.getState(stateId));
		User user = userService.getUserByUsername(principal.getName());

		if (result.hasErrors()) {
			// Add error messages
			if(!result.hasErrors() && shippingAddressConstraintsFailed(details))
				model.addAttribute("shippingError", "Address does not exist");
			model.addAttribute("bankAddress", new BankAddress_Form());
			model.addAttribute("user", user);
			model.addAttribute("states", stateDetailsController.getAllStates());
			for (FieldError error : result.getFieldErrors()) {
				model.addAttribute(error.getField() + "Err", error.getDefaultMessage());
			}
			if(menu == SUB_MENU.PAYMENT_DETAILS)
				return "redirect:/userDetails/paymentDetails";
			else if(menu == SUB_MENU.DEPOSIT_DETAILS)
				return "redirect:/userDetails/depositDetails";
			else
				return "redirect:/userDetails";
		}

		BankAddress bankAddress = new BankAddress();
		bankAddress.setState(stateDetailsController.getState(stateId));
		bankAddress.buildFromForm(details);

		addressService.addBankAddress(bankAddress);
		userService.updateUserProfile(user.getId(), user);
		if(menu == SUB_MENU.PAYMENT_DETAILS)
			return "redirect:/userDetails/paymentDetails";
		else if(menu == SUB_MENU.DEPOSIT_DETAILS)
				return "redirect:/userDetails/depositDetails";
		else
			return "redirect:/userDetails";
	}
	
	/**
	 * sets the necessary variables for adding shipping details
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/addShippingDetails")
	public String addShippingDetails(Model model)
	{
		System.out.println("add ship det");
		reloginSA = true;
		updateSA = false;
		addNewSA = true;
		updateIdSA = -1;
		return "redirect:/userDetails/shippingDetails";
	}
	
	/**
	 * sets the necessary variables for adding new CC details
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/addNewCard")
	public String addNewCard(Model model)
	{
		reloginPD = true;
		updatePD = false;
		addNewPD = true;
		updateIdPD = -1;
		return "redirect:/userDetails/paymentDetails";
	}
	
	/**
	 * resets necessary variables when returning to the payment details page
	 * @param model
	 * @return
	 */
	@RequestMapping("/goBackToMainPD")
	public String backToMainPD(Model model)
	{
		updatePD = false;
		addNewPD = false;
		reloginPD = false;
		loginErrorPD = false;
		delPD = false;
		paymentDelId = -1;
		updateIdPD = -1;
		return "redirect:/userDetails/paymentDetails";
	}
	
	/**
	 * resets the necessary variables when returning to the shipping details page
	 * @param model
	 * @return
	 */
	@RequestMapping("/goBackToMainSD")
	public String backToMainSD(Model model)
	{
		loginErrorSA = false;
		updateSA = false;
		addNewSA = false;
		updateIdSA = -1;
		reloginSA = false;
		delSA = false;
		shippingDelId = -1;
		return "redirect:/userDetails/shippingDetails";
	}
	
	/**
	 * collects the necessary information for updating payment details
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePaymentDetails/{id}")
	public String updateCard(@PathVariable("id") long id, Model model)
	{
		updatePD = true;
		//reloginPD = true;
		this.id2PD = id;
		addNewPD = false;
		updateIdPD = id;
		return "redirect:/userDetails/paymentDetails";
	}
	
	@RequestMapping("/userDetails/paypalDetails")
	public String openPaypalDetails(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		loadUserData(model, user);
		// Model for updating Paypal details
		model.addAttribute("paypalDetails", new Paypal_Form());
		// Model for updating Payment Details
		model.addAttribute("paymentDetails", new PaymentDetails_Form());
		model.addAttribute("cardTypes", cardController.getAllCardTypes());
		// Model for updating Direct Deposit Details
		DirectDepositDetails_Form details = new DirectDepositDetails_Form();
		model.addAttribute("directDepositDetails", details);
		model.addAttribute("user", user);
		selectedMenu = SUB_MENU.PAYPAL_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		return "userDetails";
	}
	
	/**
	 * sets up the needed attributes for displaying the deposit details page
	 * @param model
	 * @return
	 */
	@RequestMapping("/userDetails/depositDetails")
	public String openDepositDetails(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		loadUserData(model, user);
		// Model for updating Paypal details
		model.addAttribute("paypalDetails", new Paypal_Form());
		// Model for updating Payment Details
		model.addAttribute("paymentDetails", new PaymentDetails_Form());
		model.addAttribute("cardTypes", cardController.getAllCardTypes());
		// Model for updating Direct Deposit Details
		DirectDepositDetails_Form details = new DirectDepositDetails_Form();
		BankAddress_Form bankAddress = new BankAddress_Form();
		DirectDepositDetails savedDetails = user.getDirectDepositDetails();
		model.addAttribute("savedDirectDepositDetails", savedDetails);
		model.addAttribute("directDepositDetails", details);
		model.addAttribute("bankAddress", bankAddress);
		model.addAttribute("user", user);
		model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));
		model.addAttribute("states", stateDetailsController.getAllStates());
		selectedMenu = SUB_MENU.DEPOSIT_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		return "userDetails";
	}
	
	/**
	 * Collects and updates the current user's user details based on passed information
	 * @param model
	 * @param file
	 * @param attributes
	 * @param username
	 * @param description
	 * @param displayName
	 * @param email
	 * @return
	 */
	@RequestMapping("/updateUser")
	public String updateUser(Model model, @RequestParam("imageName") MultipartFile file, RedirectAttributes attributes, @RequestParam("username") String username,
		@RequestParam("description") String description, @RequestParam("displayName") String displayName, 
		@RequestParam("email") String email, @RequestParam(value = "2FA", required = false) String is2FAEnabled, @RequestParam(value = "currentPassword", required = false) String currentPassword, Principal principal)
	{

		User user = userService.getUserByUsername(principal.getName());
		userName = username;
		this.userDescription = description;
		this.displayName = displayName;
		this.email = email;
		
		user.setUsername(userName);
		user.setUserDescription(this.userDescription);
		user.setDisplayName(this.displayName);
		
		if (!currentPassword.isEmpty() && !passwordEncoder.matches(currentPassword, user.getPassword())) {
		    // Password verification failed
		    // Add an error message and return to the form
		    attributes.addFlashAttribute("error", "Incorrect current password");
			System.out.println("Incorrect current password");
		    return "redirect:userDetails"; // Or the appropriate error page
		}
		else {
		user.setEmail(email);
		}
		
		boolean twoFactorEnabled = "on".equals(is2FAEnabled);
		if(twoFactorEnabled) {
			twoFactorAuthentication.set2FAEnabled(user, true);
		} else {
			twoFactorAuthentication.set2FAEnabled(user, false);
		}	
		
		model.addAttribute("user", user);
		model.addAttribute("userName", userName);
		model.addAttribute("userDescription", userDescription);
		model.addAttribute("displayName", displayName);
		model.addAttribute("email", email);
		
		if(!file.isEmpty()){
			String tempImageName;
			tempImageName = user.getId() + StringUtils.cleanPath(file.getOriginalFilename());
			System.out.println(file.getOriginalFilename());
			System.out.println(tempImageName);
			try {
				String fileLocationTemp = new ClassPathResource("static/images/userImages").getFile().getAbsolutePath() + "/" + tempImageName;
				
				try (FileOutputStream output = new FileOutputStream(fileLocationTemp)) {
					output.write(file.getBytes());
				}
			
				System.out.println("Upload successful, file saved at: " + fileLocationTemp);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Upload failed");
			}
			model.addAttribute("userImage", tempImageName);
			user.setUserImage(tempImageName);
		}
		
		userRepository.save(user);
		
		return "redirect:userDetails";
	}
	
	/**
	 * Function for adding data on the logged in User to the model and controller
	 * @param model The page Model
	 * @return true if successful
	 */
	private boolean loadUserData(Model model, User user) {
		System.out.println(user.getUserImage());
		userName = user.getUsername();
		userDescription = user.getUserDescription();
		creationDate = user.getCreationDate();
		displayName = user.getDisplayName();
		email = user.getEmail();
		model.addAttribute("user", user);
		model.addAttribute("userName", userName);
		model.addAttribute("userDescription", userDescription);
		model.addAttribute("creationDate", creationDate);
		model.addAttribute("displayName", displayName);
		model.addAttribute("currUser", user);
		model.addAttribute("sellerRating", user.getSellerRating());
		model.addAttribute("email", email);
		model.addAttribute("displayUserID", user.getId());
		model.addAttribute("twoFactorAuth", user.isTwoFactorEnabled());
		return true;
	}
	
	/**
	 * Creates or updates the DirectDepositDetails associated with the user
	 * @param details the filled out DirectDepositDetails from the page's form
	 * @return 	a redirection string pointing to the userDetails page
	 */
	@Transactional
	@RequestMapping(value = "/submitDepositDetailsAction", 
			method = RequestMethod.POST, params="submit")
	public String sendUpdateDD(
			@Validated @ModelAttribute("directDepositDetails") DirectDepositDetails_Form details,
			@RequestParam("stateId") String stateId,
			BindingResult result, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		selectedMenu = SUB_MENU.DEPOSIT_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		if (result.hasErrors()) {
			System.out.println("deposit error");
			model.addAttribute("errMessage", "Your updated direct deposit details has errors.");
			model.addAttribute("paymentDetails", new PaymentDetails_Form());
			model.addAttribute("states", stateDetailsController.getAllStates());
			loadUserData(model, user);
			return "userDetails";
		}
		details.setState(stateDetailsController.getState(stateId));
		DirectDepositDetails deposit = new DirectDepositDetails(user);
		model.addAttribute("states", stateDetailsController.getAllStates());
		deposit.buildFromForm(details);
		this.userController.updateDirectDepositDetails(deposit, principal);
		return "redirect:/userDetails/initializePaymentDetails";
	}
	
	/**
	 * deletes the users deposit details
	 * @param details
	 * @param result
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/submitDepositDetailsAction", 
			method = RequestMethod.POST, params="delete")
	public String deleteExisting(
			@Validated @ModelAttribute("directDepositDetails") DirectDepositDetails_Form details,
			BindingResult result, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		selectedMenu = SUB_MENU.DEPOSIT_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		if (user.getDirectDepositDetails() == null) {
			model.addAttribute("errMessage", "Your direct deposit details have already been deleted.");
			// Add back page data
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("paymentDetails", new PaymentDetails_Form());
			loadUserData(model, user);
			return "userDetails";
		}
		model.addAttribute("states", stateDetailsController.getAllStates());
		this.userController.deleteDirectDepositDetails(user);
		return "redirect:/userDetails/initializePaymentDetails";
	}
	
	/**
	 * Creates or updates the PaymentDetails associated with the logged in user
	 * @param details the filled out PaymentDetails from the page's form
	 * @return 	a redirection string pointing to the userDetails page
	 */
	@Transactional
	@PostMapping(value = "/submitPaymentDetailsAction", params="submit")
	public String createDetails(@Validated @ModelAttribute("paymentDetails") PaymentDetails_Form details, BindingResult result, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		selectedMenu = SUB_MENU.PAYMENT_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		System.out.println(details.getExpirationDate());
		if (result.hasErrors() || paymentDetailsConstraintsFailed(details)) {
			// Add error messages
			if (cardExpired(details))
				model.addAttribute("cardError", "The credit card is expired.");
			if(cardFarFuture(details))
				model.addAttribute("cardError", "The expiration date is an impossible number of years in the future");
			model.addAttribute("errMessage", "Your updated payment details has errors.");
			// Add back page data
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("directDepositDetails", new DirectDepositDetails_Form());
			if(user.getDefaultPaymentDetails() != null)
				model.addAttribute("defaultPaymentDetails", payDetCont.getPaymentDetail(user.getDefaultPaymentDetails().getId(), null));
			else
				model.addAttribute("defaultPaymentDetails", null);
			if(user.getPaymentDetails() != null && user.getPaymentDetails().isEmpty())
				model.addAttribute("savedDetails", null);
			else
				model.addAttribute("savedDetails", payDetCont.getPaymentDetailsByUser(user));
			model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));

			model.addAttribute("addNew", addNewPD);
			model.addAttribute("updateId", updateIdPD);
			model.addAttribute("update", updatePD);
			model.addAttribute("relogin", reloginPD);
			model.addAttribute("delete", delPD);
			loadUserData(model, user);
			return "/userDetails";
		}
		PaymentDetails payment = new PaymentDetails();
		//get billing adderss by id in form
		ShippingAddress billingAddress = shippingController.getShippingAddressEntry(details.getBillingAddress());
		payment.buildFromForm(details, billingAddress);
		payment.setUser(user);
		Set<PaymentDetails> PD = user.getPaymentDetails();
		if(PD == null)
			PD = new HashSet<PaymentDetails>();
		PD.add(payment);
		user.setPaymentDetails(PD);
		if(user.getDefaultPaymentDetails() == null)
			user.setDefaultPaymentDetails(payment);
		payDetCont.addPaymentDetails(payment);
		userRepository.save(user);
		addNewPD = false;
		return "redirect:/userDetails/paymentDetails";
	}
	
	
	/**
	 * Creates or updates the PaymentDetails associated with the logged in user
	 * @param details the filled out PaymentDetails from the page's form
	 * @return 	a redirection string pointing to the userDetails page
	 */
	@Transactional
	@PostMapping(value = "/submitPaymentDetailsAction", params="update")
	public String sendUpdatePD(@Validated @ModelAttribute("paymentDetails") PaymentDetails_Form details, BindingResult result, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		selectedMenu = SUB_MENU.PAYMENT_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		PaymentDetails currDetails = payDetCont.getPaymentDetail(id2PD, model);
		System.out.println(details.getExpirationDate());
		if (result.hasErrors() || paymentDetailsConstraintsFailed(details)) {
			// Add error messages
			if (cardExpired(details))
				model.addAttribute("cardError", "The credit card is expired.");
			if(cardFarFuture(details))
				model.addAttribute("cardError", "The expiration date is an impossible number of years in the future");
			model.addAttribute("errMessage", "Your updated payment details has errors.");
			// Add back page data
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("directDepositDetails", new DirectDepositDetails_Form());
			if(user.getDefaultPaymentDetails() != null)
				model.addAttribute("defaultPaymentDetails", payDetCont.getPaymentDetail(user.getDefaultPaymentDetails().getId(), null));
			else
				model.addAttribute("defaultPaymentDetails", null);
			if(user.getPaymentDetails() != null && user.getPaymentDetails().isEmpty())
				model.addAttribute("savedDetails", null);
			else
				model.addAttribute("savedDetails", payDetCont.getPaymentDetailsByUser(user));
			model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));
			model.addAttribute("addNew", addNewPD);
			model.addAttribute("updateId", updateIdPD);
			model.addAttribute("update", updatePD);
			model.addAttribute("relogin", reloginPD);
			model.addAttribute("delete", delPD);
			loadUserData(model, user);
			return "/userDetails";
		}
		PaymentDetails payment = new PaymentDetails();
		ShippingAddress billingAddress = shippingController.getShippingAddressEntry(details.getBillingAddress());
		payment.buildFromForm(details, billingAddress);
		payDetCont.updatePaymentDetails(payment, currDetails);	
		Set<PaymentDetails> pDetails = user.getPaymentDetails();
		List<PaymentDetails> PD = new ArrayList<>(pDetails);
		for(PaymentDetails payDet : PD)
			if(payDet.getId() == id2PD)
				currDetails = payDet;
		PD.remove(PD.indexOf(currDetails));
		PD.add(payDetCont.getPaymentDetail(id2PD, model));
		Set<PaymentDetails> PD2 = new HashSet<>(PD);
		user.setPaymentDetails(PD2);
		model.addAttribute("user", user);
		userRepository.save(user);
		addNewPD = false;
		updatePD = false;
		updateIdPD = -1;
		return "redirect:/userDetails/paymentDetails";
	}
	
	/**
	 * sets up the necessary variable states for deleting payment details
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteExistingPaymentDetails/{id}")
	public String setUpDeletePaymentDetails(@PathVariable("id") long id)
	{
		paymentDelId = id;
		reloginPD = true;
		delPD = true;
		return "redirect:/userDetails/paymentDetails";
	}

	/**
	 * deletes the existing payment details of a user
	 * @param details The paymentDetails_Form to be deleted
	 * @param result
	 * @param model
	 * @return
	 */
	@Transactional
	@PostMapping(value = "/submitPaymentDetailsAction", params="delete")
	public String deleteExisting(@RequestParam("usernamePD") String username, @RequestParam("passwordPD") String password, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		System.out.println("entered udcont");
		if(reloginPD(username, password, model, principal).equals("false"))
			return "redirect:/userDetails/paymentDetails";
		selectedMenu = SUB_MENU.PAYMENT_DETAILS;
		int index = -1;
		System.out.println(paymentDelId);
		PaymentDetails currDetails = payDetCont.getPaymentDetail(paymentDelId, null);
		if(user.getDefaultPaymentDetails() != null && currDetails.getId() == user.getDefaultPaymentDetails().getId())
		{
			System.out.println("detached");
			entityManager.detach(user.getDefaultPaymentDetails());
			List<PaymentDetails> PD = new ArrayList<>(user.getPaymentDetails());
			PD.remove(currDetails);
			if(PD.isEmpty())
				user.setDefaultPaymentDetails(null);
			else
				user.setDefaultPaymentDetails(PD.get(0));
			userRepository.save(user);
		}
		List<PaymentDetails> PD = new ArrayList<>(user.getPaymentDetails());
		System.out.println(PD.size());
		if(PD.size()==1)
			PD.remove(0);
		else
			for(PaymentDetails details : PD)
			{
				if(details.getId() == currDetails.getId())
					index = PD.indexOf(details);
			}
		if(index != -1)
			PD.remove(index);
		if(PD.isEmpty())
			user.setPaymentDetails(null);
		else
			user.setPaymentDetails(new HashSet<>(PD));
		if(transController.findByPaymentDetails(currDetails).isEmpty())
		{
			payDetCont.deletePaymentDetails(currDetails);
		}
		currDetails.setUser(null);
		addNewPD = false;
		updatePD = false;
		updateIdPD = -1;
		paymentDelId = -1;
		delPD = false;
		return "redirect:/userDetails/paymentDetails";
	}
	
	/**
	 * Changes the current default payment details to another set of payment details
	 * @param id
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/makeDefaultPaymentDetails/{id}")
	public String makeDefaultPaymentDetails(@PathVariable("id") long id, Principal principal) {
		selectedMenu = SUB_MENU.PAYMENT_DETAILS;
		User user = userService.getUserByUsername(principal.getName());
		PaymentDetails currDetails = payDetCont.getPaymentDetail(id, null);
		user.setDefaultPaymentDetails(currDetails);
		System.out.println(user.getDefaultPaymentDetails().getCardType());
		userRepository.save(user);
		return "redirect:/userDetails/paymentDetails";
	}
	
	/**
	 * Updates the users details and sends the update to the paymentdetails Database location of that users paymentDetails
	 * @param details The updated Payment Details
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/submitPaypalDetailsAction", method = RequestMethod.POST, params="submit")
	public String sendUpdatePPD(@Validated @ModelAttribute("paypalDetails") Paypal_Form details, BindingResult result, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		selectedMenu = SUB_MENU.PAYPAL_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		if (result.hasErrors()) {
			model.addAttribute("errMessage", "Your updated payment details has errors.");
			// Add back page data
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("directDepositDetails", new DirectDepositDetails_Form());
			loadUserData(model, user);
			return "userDetails";
		}
		Paypal payment = new Paypal();
		payment.buildFromForm(details);
		System.out.println("after printing errors");
		this.userController.updatePaypalDetails(payment, principal);
		return "redirect:/userDetails";
	}
	
	/**
	 * send an error if the users paypal Details had already been deleted
	 * @param details
	 * @param result
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value ="/submitPaypalDetailsAction", method = RequestMethod.POST, params="delete")
	public String deleteExisting(@Validated @ModelAttribute("paypalDetails") Paypal_Form details, BindingResult result, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		selectedMenu = SUB_MENU.PAYPAL_DETAILS;
		model.addAttribute("selectedMenu", selectedMenu);
		if (user.getPaypal() == null) {
			model.addAttribute("errMessage", "Your paypal details have already been deleted.");
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("directDepositDetails", new DirectDepositDetails_Form());
			loadUserData(model, user);
			return "userDetails";
		}
		this.userController.deletePaypal(user);
		return "redirect:/userDetails";
	}
	
	/**
	 * sets up necessary variables for updating shipping information
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateShippingDetails/{id}")
	public String updateShipping(@PathVariable("id") long id, Model model)
	{
		updateSA = true;
		this.id2SA = id;
		addNewSA = false;
		updateIdSA = id;
		reloginSA = true;
		return "redirect:/userDetails/shippingDetails";
	}
	
	/**
	 * sets up necessary variables for deleting shippping information
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteExistingShippingDetails/{id}")
	public String setUpDeleteShipping(@PathVariable("id") long id)
	{
		shippingDelId = id;
		reloginSA = true;
		delSA = true;
		return "redirect:/userDetails/shippingDetails";
	}
	
	/**
	 * takes the passed shipping address details and creates a new shipping address to be associated
	 * with the user
	 * @param details
	 * @param result
	 * @param stateId
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/submitShippingAddressAction", params="submit")
	public String createShippingDetails(@Validated @ModelAttribute("shippingDetails") ShippingAddress_Form details, BindingResult result, @RequestParam("stateId") String stateId, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		selectedMenu = SUB_MENU.SHIPPING_DETAILS;
		details.setState(stateDetailsController.getState(stateId));
		model.addAttribute("selectedMenu", selectedMenu);
		if (result.hasErrors() || shippingAddressConstraintsFailed(details)) {
			// Add error messages
			if(!result.hasErrors() && shippingAddressConstraintsFailed(details))
				model.addAttribute("shippingError", "Address does not exist");
			model.addAttribute("shippingDetails", new ShippingAddress_Form());
			model.addAttribute("user", user);
			model.addAttribute("states", stateDetailsController.getAllStates());
			if(user.getDefaultShipping() != null)
				model.addAttribute("defaultShippingDetails", user.getDefaultShipping());
			else
				model.addAttribute("defaultShippingDetails", null);
			if(user.getShippingDetails() != null && user.getShippingDetails().isEmpty())
				model.addAttribute("savedShippingDetails", null);
			else
				model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));
			for (FieldError error : result.getFieldErrors()) {
				model.addAttribute(error.getField() + "Err", error.getDefaultMessage());
			}
			model.addAttribute("addNew", addNewSA);
			model.addAttribute("updateId", updateIdSA);
			model.addAttribute("update", updateSA);
			model.addAttribute("relogin", reloginSA);
			model.addAttribute("delete", delSA);
			loadUserData(model, user);
			return "/userDetails";
		}
		ShippingAddress shipping = new ShippingAddress();
		details.setState(stateDetailsController.getState(stateId));
		shipping.buildFromForm(details);
		shipping.setUser(user);
		Set<ShippingAddress> SA = user.getShippingDetails();
		if(SA == null)
			SA = new HashSet<ShippingAddress>();
		SA.add(shipping);
		user.setShippingDetails(SA);
		shippingController.addShippingAddress(shipping, user);
		userRepository.save(user);
		addNewSA = false;
		return "redirect:/userDetails/shippingDetails";
	}
	
	
	/**
	 * updates the users existing shipping address information for a single address
	 * @param details the filled out PaymentDetails from the page's form
	 * @return 	a redirection string pointing to the userDetails page
	 */
	@PostMapping(value = "/submitShippingAddressAction", params="update")
	public String sendUpdateSA(@Validated @ModelAttribute("shippingDetail") ShippingAddress_Form details, BindingResult result, @RequestParam("stateId") String stateId, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		selectedMenu = SUB_MENU.SHIPPING_DETAILS;
		details.setState(stateDetailsController.getState(stateId));
		model.addAttribute("selectedMenu", selectedMenu);
		ShippingAddress currDetails = shippingController.getShippingAddressEntry(id2SA);
		
		if (result.hasErrors() || shippingAddressConstraintsFailed(details)) {
			// Add error messages
			if(!result.hasErrors() && shippingAddressConstraintsFailed(details))
				model.addAttribute("shippingError", "Address does not exist");
			model.addAttribute("shippingDetails", new ShippingAddress_Form());
			model.addAttribute("user", user);
			model.addAttribute("states", stateDetailsController.getAllStates());
			if(user.getDefaultShipping() != null)
				model.addAttribute("defaultShippingDetails", user.getDefaultShipping());
			else
				model.addAttribute("defaultShippingDetails", null);
			
			if(user.getShippingDetails() != null && user.getShippingDetails().isEmpty())
				model.addAttribute("savedShippingDetails", null);
			else
				model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));
			
			for (FieldError error : result.getFieldErrors()) {
				model.addAttribute(error.getField() + "Err", error.getDefaultMessage());
			}
			
			model.addAttribute("addNew", addNewSA);
			model.addAttribute("updateId", updateIdSA);
			model.addAttribute("update", updateSA);
			model.addAttribute("relogin", reloginSA);
			model.addAttribute("delete", delSA);
			loadUserData(model, user);
			
			return "/userDetails";
		}
		
		ShippingAddress shipping = new ShippingAddress();
		details.setState(stateDetailsController.getState(stateId));
		shipping.buildFromForm(details);
		shippingController.updateShippingAddress(shipping, currDetails);	
		Set<ShippingAddress> sAddress = user.getShippingDetails();
		List<ShippingAddress> SA = new ArrayList<>(sAddress);
		
		for(ShippingAddress address : SA)
			if(address.getId() == id2SA)
				currDetails = address;
		
		SA.remove(SA.indexOf(currDetails));
		SA.add(shippingController.getShippingAddressEntry(id2SA));
		Set<ShippingAddress> SA2 = new HashSet<>(SA);
		user.setShippingDetails(SA2);
		model.addAttribute("user", user);
		userRepository.save(user);
		addNewSA = false;
		updateSA = false;
		updateIdSA = -1;
		
		return "redirect:/userDetails/shippingDetails";
	}
	
	/**
	 * deletes an existing shipping address associated with the user
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@Transactional
	@PostMapping(value = "/submitShippingAddressAction", params="delete")
	public String deleteExistingShipping(@RequestParam("usernameSA") String username, @RequestParam("passwordSA") String password, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		System.out.println("entered udcont");
		if(this.reloginSA(username, password, model, principal).equals("false"))
		{
			return "redirect:/userDetails/shippingDetails";
		}
		selectedMenu = SUB_MENU.SHIPPING_DETAILS;
		int index = -1;
		System.out.println(shippingDelId);
		ShippingAddress currDetails = shippingController.getShippingAddressEntry(shippingDelId);
		if(user.getDefaultShipping() != null && currDetails.getId() == user.getDefaultShipping().getId())
		{
			System.out.println("detached");
			entityManager.detach(user.getDefaultShipping());
			user.setDefaultShipping(null);
			userRepository.save(user);
		}
		List<ShippingAddress> SD = new ArrayList<>(user.getShippingDetails());
		System.out.println(SD.size());
		if(SD.size()==1)
			SD.remove(0);
		else
			for(ShippingAddress details : SD)
			{
				if(details.getId() == currDetails.getId())
					index = SD.indexOf(details);
			}
		if(index != -1)
			SD.remove(index);
		if(SD.isEmpty())
			user.setShippingDetails(null);
		else
			user.setShippingDetails(new HashSet<>(SD));
		if(transController.findByShippingDetails(currDetails).isEmpty())
		{
			shippingController.deleteShippingAddress(currDetails);
		}
		currDetails.setUser(null);
		addNewSA = false;
		updateSA = false;
		updateIdSA = -1;
		delSA = false;
		shippingDelId = -1;
		return "redirect:/userDetails/shippingDetails";
	}
	
	/**
	 * sets the details of the passed id to be the users default shipping details
	 * @param id
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/makeDefaultShippingDetails/{id}")
	public String makeDefaultShippingDetails(@PathVariable("id") long id, Principal principal) {
		selectedMenu = SUB_MENU.SHIPPING_DETAILS;
		User user = userService.getUserByUsername(principal.getName());
		ShippingAddress currDetails = shippingController.getShippingAddressEntry(id);
		user.setDefaultShipping(currDetails);
		System.out.println(user.getDefaultShipping().getStreetAddress());
		userRepository.save(user);
		return "redirect:/userDetails/shippingDetails";
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * takes the users login info to verify if the user is the one trying to add the details
	 * prevent outsiders from trying to use someone else's account / card
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/submitShippingAddressAction", params="loginInfo")
	public String reloginSA(@RequestParam("usernameSA") String username, @RequestParam("passwordSA") String password, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		if(!validateLoginInfo(username, password, user))
		{
			if(delSA == true)
			{
				loginErrorSA = true;
				return "false";
			}
			loginErrorSA = true;
			return "redirect:/userDetails/shippingDetails";
		}
		loginErrorSA = false;
		reloginSA = false;
		return "redirect:/userDetails/shippingDetails";
	}
	
	/**
	 * use login info to verify that the one attempting to add or modify CC information is the accounts owner
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/submitPaymentDetailsAction", params="loginInfo")
	public String reloginPD(@RequestParam("usernamePD") String username, @RequestParam("passwordPD") String password, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		if(!validateLoginInfo(username, password, user))
		{
			if(delPD == true)
			{
				loginErrorPD = true;
				return "false";
			}
			loginErrorPD = true;
			return "redirect:/userDetails/paymentDetails";
		}
		loginErrorPD = false;
		reloginPD = false;
		return "redirect:/userDetails/paymentDetails";
	}
	
	/**
	 * Checks if the non Spring Validation constraints are met by the passed for
	 * @param form The PaymentDetails_Form to check
	 * @return true if the constraints fail, false otherwise
	 */
	public boolean paymentDetailsConstraintsFailed(PaymentDetails_Form form) {
		return cardExpired(form);
	}
	
	/**
	 * send the address details to the validation method
	 * @param form
	 * @return
	 */
	public boolean shippingAddressConstraintsFailed(ShippingAddress_Form form) {
		return !addressExists(form);
	}

	public boolean shippingAddressConstraintsFailed(BankAddress_Form form) {
		return !addressExists(form);
	}
	
	
	/**
	 * use the smartstreets api (expired) to check if the address passed exists
	 * @param shipping
	 * @return
   **/
  /*
	 public boolean addressExists(ShippingAddress_Form shipping){
		Client client = new ClientBuilder("15c052fe-6a81-8841-3359-59658192ff8e", "9d48LSyfCFhlZolc0gi6").build();
		Lookup lookup = new Lookup();
		lookup.setAddressee(shipping.getRecipient());
		lookup.setStreet(shipping.getStreetAddress());
		lookup.setCity(shipping.getCity());
		lookup.setState(shipping.getState().getStateName());
		lookup.setZipCode(shipping.getPostalCode());
		System.out.println(lookup.getAddressee());
		System.out.println(lookup.getStreet());
		System.out.println(lookup.getCity());
		System.out.println(lookup.getState());
		System.out.println(lookup.getZipCode());
		try {
			client.send(lookup);
		}
		catch(SmartyException | IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		List<Candidate> results = lookup.getResult();
		if(results.isEmpty()){
			System.out.println("cannot find address");
			return true;
		}
		return false;
	} */
	
	
	
	public boolean addressExists(ShippingAddress_Form shipping) {
	    return addressService.addressExists(shipping);
	}

	public boolean addressExists(BankAddress_Form shipping) {
	    return addressService.addressExists(shipping);
	}
	
	
	/**
	 * validates that the passed login information is the users login information
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean validateLoginInfo(String username, String password, User user)
	{
		System.out.println(username.equals(user.getUsername()));
		System.out.println(passwordEncoder.matches(password, user.getPassword()));
		if(username.equals(user.getUsername()) && passwordEncoder.matches(password, user.getPassword()))
			return true;
		
		return false;
	}
	
	/**
	 * Checks if the expirationDate of the passed PaymentDetails_Form is expired
	 * @param details the form to validate
	 * @return true if the card is expired, false if the card is not expired, or the expirationDate is invalid
	 */
	public boolean cardExpired(PaymentDetails_Form details) {
		LocalDate expirDate;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy dd");
		try {
			expirDate = LocalDate.parse(details.getExpirationDate()+" 01", formatter);
			System.out.println(expirDate.toString() + " to s exp date");
			return expirDate.compareTo(LocalDate.now()) < 0;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Checks to see if the card's expiration date is more than 8 years in the future
	 * @param details the PaymentDetails_Form to check
	 * @return true if the card is not in the far future
	 */
	public boolean cardFarFuture(PaymentDetails_Form details) {
		int thisYear = LocalDate.now().getYear();
		try {
			int year = Integer.parseInt(details.getExpirationDate().split("/")[1]);
			System.out.println(year + " compared to " + (thisYear + 8));
			if(year <= (thisYear + 8)) {
				System.out.println("year is less than or equal to 8 years in the future");
				return false;
			}
			return true;
		}
			catch (Exception e) {
			System.out.println(e);
			return true;
		}
				
	}
}