package edu.sru.cpsc.webshopping.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import edu.sru.cpsc.webshopping.controller.billing.CardTypeController;
import edu.sru.cpsc.webshopping.controller.billing.PaymentDetailsController;
import edu.sru.cpsc.webshopping.controller.billing.StateDetailsController;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails_Form;
import edu.sru.cpsc.webshopping.domain.billing.Paypal_Form;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress_Form;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.util.PreLoad;
import jakarta.transaction.Transactional;

@Controller
// @RequestMapping(value = "/user")
public class SignUpController {
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserService userService;
  private UserController userController;
  private UserRepository userRepository;
  private WidgetController widgetController;
  private EmailController email;
  private UtilityController util;
  private PaymentDetailsController payDetController;
  private ShippingAddress address;
  private PaymentDetails validatedDetails;
  private PaymentDetails details;
  private UserDetailsController userDetController;
  private CardTypeController cardController;
  
  private Transaction purchase;
  private MarketListing prevListing;
  private PaymentDetailsRepository payDetRepository;
  private Paypal_Form paypal;
  
  private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
  private boolean showedSuccess = false;
  User findUser = new User();
  private boolean allSelected = false;
  private boolean modifyPayment = false;
  private boolean relogin = true;
  private boolean depositPicked = false;
  private boolean loginEr = false;
  private boolean toShipping = false;
  private String answer;
  private String question;
  private int theId;
  private String page;
  
  /* shipping */
  private StateDetailsController stateDetailsController;
  private ShippingAddressDomainController shippingController;
  private boolean addNewSA = false;
  private boolean updateSA = false;
  private boolean reloginSA = false;
  private boolean delSA = false;
  private long updateIdSA = -1;

  public SignUpController(
      UserController userController,
      WidgetController widgetController,
      EmailController email,
      UtilityController util,
      PaymentDetailsController payDetController,
      UserRepository userRepository,
      CardTypeController cardController,
      UserDetailsController userDetController,
      StateDetailsController stateDetailsController,
      ShippingAddressDomainController shippingController) {
    this.userController = userController;
    this.widgetController = widgetController;
    this.email = email;
    this.util = util;
    this.userRepository = userRepository;
    this.userDetController = userDetController;
    this.cardController = cardController;
    this.payDetController = payDetController;
    this.stateDetailsController = stateDetailsController;
    this.shippingController = shippingController;
  }

  Map<String, String> countryCodesMap;

  @ModelAttribute
  public void preLoad(Model model) {

    countryCodesMap = new LinkedHashMap<>();
    Set<String> regions = phoneNumberUtil.getSupportedRegions();
    countryCodesMap =
        regions.stream()
            .sorted(Comparator.comparing(region -> new Locale("", region).getDisplayCountry()))
            .collect(
                Collectors.toMap(
                    region -> region,
                    region ->
                        String.format(
                            "+%s %s",
                            phoneNumberUtil.getCountryCodeForRegion(region),
                            new Locale("", region).getDisplayCountry()),
                    (x, y) -> y,
                    LinkedHashMap::new));
  }

  List<String> secretQuestion1;

  @ModelAttribute
  public void preLoad2(Model model) {
    secretQuestion1 = new ArrayList<>();
    secretQuestion1.add("What is your mother's maiden name?");
    secretQuestion1.add("What is the name of your first pet?");
    secretQuestion1.add("What is the name of the street you grew up on?");
    secretQuestion1.add("What is the make of the first car you owned?");
    secretQuestion1.add("What city were you born in?");
  }

  List<String> secretQuestion2;

  @ModelAttribute
  public void preLoad3(Model model) {
    secretQuestion2 = new ArrayList<>();
    secretQuestion2.add("What is your favorite band?");
    secretQuestion2.add("What is your father's middle name?");
    secretQuestion2.add("What is your favorite school subject?");
    secretQuestion2.add("What was the name of your first boyfriend/girlfriend?");
    secretQuestion2.add("Where is somewhere you've always wanted to travel to?");
  }

  List<String> secretQuestion3;

  @ModelAttribute
  public void preLoad4(Model model) {
    secretQuestion3 = new ArrayList<>();
    secretQuestion3.add("What is your favorite beverage?");
    secretQuestion3.add("What is your favorite video game?");
    secretQuestion3.add("What is your favorite tv show?");
    secretQuestion3.add("What is your favorite outdoor activity?");
    secretQuestion3.add("What was your highschool mascot?");
  }

  @RequestMapping({"/newUser"})
  public String newUser(Model model) {
    if (getPage() == null) {
      setPage("signup");
      setShowedSuccess(false);
    }
    if (getPage() != null && !(getPage().contains("signup")) && !(getPage().contains("success"))) {
      setPage("signup");
      setShowedSuccess(false);
    }
    if (isShowedSuccess()) {
      setPage("signup");
      setShowedSuccess(false);
    }
    if (getPage().contains("success")) {
      setShowedSuccess(true);
    }

    String defaultCountryCode = PreLoad.getDefaultCountryCode();

    User user = new User();
    userController.getCaptcha(user);
    model.addAttribute("page", getPage());
    model.addAttribute("user", user);
    model.addAttribute("secretQuestion1", secretQuestion1);
    model.addAttribute("secretQuestion2", secretQuestion2);
    model.addAttribute("secretQuestion3", secretQuestion3);
    model.addAttribute("countryCodes", countryCodesMap);
    model.addAttribute("users", userController.getAllUsers());
    model.addAttribute("widget", new Widget());
    model.addAttribute("widgets", widgetController.getAllWidgets());
    model.addAttribute("selectedWidget", null);
    model.addAttribute("defaultCountryCode", defaultCountryCode);
    return "newUser";
  }
  
  /**
   * creates a new user when someone signs up
   * called by newUser.html
   * @param user
   * @param result
   * @param file
   * @param attributes
   * @param model
   * @return
   */

  @PostMapping("/add-user-signup")
  public String addUser(
      @Valid User user,
      BindingResult result,
      @RequestParam("imageName") MultipartFile file,
      RedirectAttributes attributes,
      Model model) {
    User usertemp = user;
    String countryCode = user.getCountryCode();
    String phoneNumberString = user.getPhoneNumber();
    PhoneNumber phoneNumber = new PhoneNumber();
    try {
      phoneNumber
          .setCountryCode(phoneNumberUtil.getCountryCodeForRegion(countryCode))
          .setNationalNumber(Long.parseLong(phoneNumberString));
      if (!phoneNumberUtil.isValidNumber(phoneNumber)) {
        result.addError(
            new FieldError(
                "phoneNumber",
                "phoneNumber",
                String.format(
                    "Please enter valid phone number. Example format for the selected country - %s",
                    phoneNumberUtil
                        .getExampleNumberForType(countryCode, PhoneNumberType.MOBILE)
                        .getNationalNumber())));
      }
    } catch (NumberFormatException e) {
      result.addError(
          new FieldError(
              "phoneNumber",
              "phoneNumber",
              String.format(
                  "Please enter valid phone number. Example format for the selected country - %s",
                  phoneNumberUtil
                      .getExampleNumberForType(countryCode, PhoneNumberType.MOBILE)
                      .getNationalNumber())));
    }
    if (!EmailValidator.getInstance().isValid(user.getEmail())) {
      result.addError(
          new FieldError("email", "email", "Invalid email format. Example - username@domain.com"));
    }
    if (userController.getUserByUsername(usertemp.getUsername()) != null) {
      result.addError(new FieldError("username", "username", "Username is already taken."));
    }
    if (userController.getUserByEmail(usertemp.getEmail()) != null) {
      result.addError(
          new FieldError("email", "email", "An account is already associated with this email."));
    }

    if (!(usertemp.getPasswordconf().equals(usertemp.getPassword()))) {
      result.addError(new FieldError("passwordconf", "passwordconf", "Passwords don't match."));
    }

    String defaultCountryCode = PreLoad.getDefaultCountryCode();

    if (result.hasErrors()) {
      model.addAttribute("user", user);
      model.addAttribute("countryCodes", countryCodesMap);
      model.addAttribute("defaultCountryCode", defaultCountryCode);
      model.addAttribute("secretQuestion1", secretQuestion1);
      model.addAttribute("secretQuestion2", secretQuestion2);
      model.addAttribute("secretQuestion3", secretQuestion3);
      userController.getCaptcha(user);
      model.addAttribute("page", getPage());
      return "newUser";
    }
    /* captcha */
    if (user.getCaptcha().equals(user.getHiddenCaptcha())) {
//    	if (user.getUsername().contains("admin" + "widget")) { // check for admin
//    		user.setRole("ROLE_ADMIN");
//    		user.setEnabled(true);
//    	} else if (user.getUsername().contains("admin" + "super")) { // check for super admin
//    		user.setRole("ROLE_SUPERADMIN");
//    		user.setEnabled(true);
//    	}
    	model.addAttribute("message", "User Registered successfully!");
    	setPage("success");
    	//userController.setCurrently_Logged_In(user);
    	model.addAttribute("page", getPage());
    	if (user.getRole() != "ROLE_ADMIN") {
    		email.verificationEmail(user, util.randomStringGenerator());
    	}
    	userController.addUser(user, result);
      
    	user = userController.getUserByEmail(user.getEmail());
      
    	System.out.println(user.getId() + " user Id");
    	
    	/* profile picture */
    	System.out.println(file.isEmpty() + " is the file empty");
    	if (!file.isEmpty()) {
    		String tempImageName;
    		tempImageName = user.getId() + StringUtils.cleanPath(file.getOriginalFilename());
    		System.out.println(file.getOriginalFilename());
    		System.out.println(tempImageName);
    		user.setUserImage(tempImageName);
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
    	}
    	
    	userRepository.save(user);
    	model.addAttribute("user", user);
    	model.addAttribute("cardTypes", cardController.getAllCardTypes());
    	model.addAttribute("states", stateDetailsController.getAllStates());
    	
    	System.out.println("line 341 " + user);
    	User userLogin = userController.getUserByUsername(user.getUsername());
    	System.out.println("line 343 " + userLogin);
    	//userController.setCurrently_Logged_In(userLogin);
    	//System.out.println(userController.getCurrently_Logged_In());
    	
    	return "newUserShipping";
    } else {
    	result.addError(new FieldError("captcha", "captcha", "Incorrect Captcha."));
    	userController.getCaptcha(user);
    	
    	model.addAttribute("secretQuestion1", secretQuestion1);
    	model.addAttribute("secretQuestion2", secretQuestion2);
    	model.addAttribute("secretQuestion3", secretQuestion3);
    	model.addAttribute("user", user);
    	model.addAttribute("page", getPage());
    	model.addAttribute("countryCodes", countryCodesMap);
    	model.addAttribute("defaultCountryCode", defaultCountryCode);
    	model.addAttribute("cardTypes", cardController.getAllCardTypes());
    	model.addAttribute("states", stateDetailsController.getAllStates());
    	
    	return "newUser";
    }
  }
  
  	/* used by newUserPayment.html */
  	@Transactional
	@PostMapping("/submitPurchaseSignup")
  	public String submitPurchaseSignup(@Validated @ModelAttribute("paymentDetails") PaymentDetails_Form paymentDetails, @RequestParam("userId") Long userId,
			BindingResult result, Model model) {
	  	System.out.println("got to start of payment");
		PaymentDetails currDetails = new PaymentDetails();
		allSelected = false;
    ShippingAddress billingAddress = shippingController.getShippingAddressEntry(paymentDetails.getBillingAddress());
		currDetails.buildFromForm(paymentDetails, billingAddress);
		model.addAttribute("cardTypes", cardController.getAllCardTypes());
    System.out.println("userId: " + userId);
		// Test that payment details are valid
		if (!paymentDetailsInvalid(paymentDetails) && !result.hasErrors()) {
			// add the card to the database if it's new
			if (!payDetController.checkDuplicateCard(currDetails)) {
				payDetController.addPaymentDetails(currDetails);
				System.out.println("option 1");
			} else {
				currDetails = payDetController.getPaymentDetailsByCardNumberAndExpirationDate(currDetails);
				System.out.println(currDetails.getId());
			}
			if (address != null) {
				allSelected = true;
			}
			User user = userRepository.findById(userId).orElse(null);
			Set<PaymentDetails> PD = user.getPaymentDetails();
			if(PD == null)
				PD = new HashSet<PaymentDetails>();
			PD.add(currDetails);
			user.setPaymentDetails(PD);
			if(user.getDefaultPaymentDetails() == null)
				user.setDefaultPaymentDetails(currDetails);
			currDetails.setUser(user);
			payDetController.addPaymentDetails(currDetails);
			modifyPayment = false;
			relogin = true;
			validatedDetails = currDetails;
      setPage("success");
      model.addAttribute("page", getPage());

			return "redirect:/newUser";
		}
		// Transaction failed - post error
		else {
			if (address == null)
				model.addAttribute("selectedAddress", null);
			else
				model.addAttribute("selectedAddress", address);
			details = new PaymentDetails();
			// Build credit card error message
			for (FieldError item : result.getFieldErrors()) {
				model.addAttribute(item.getField() + "Err", item.getDefaultMessage());
			}
			if (paymentDetails.getExpirationDate() != null && paymentDetailsExpired(paymentDetails)) {
				model.addAttribute("cardError", "The Credit Card has expired.");
			}
			if (userDetController.cardFarFuture(paymentDetails) && paymentDetails.getExpirationDate() != "") {
				model.addAttribute("cardError", "The expiration date is an impossible number of years in the future");
			}
			User user = userRepository.findById(userId).orElse(null);
			System.out.println(user);
			if (address == null) {
				model.addAttribute("noAddress", "Please enter a shipping address");
			}
			model.addAttribute("expirationDate", paymentDetails.getExpirationDate());
			model.addAttribute("purchase", purchase);
			model.addAttribute("paymentDetails", details);
			model.addAttribute("errMessage", "Payment Details Invalid");
			model.addAttribute("paypal", paypal);
			model.addAttribute("modifyPayment", modifyPayment);
			model.addAttribute("selectedPayment", validatedDetails);
			model.addAttribute("toShipping", toShipping);
			model.addAttribute("useThis", true);
			model.addAttribute("depositPicked", depositPicked);
			model.addAttribute("relogin", relogin);
			model.addAttribute("loginEr", loginEr);
			model.addAttribute("allSelected", allSelected);
			model.addAttribute("cardTypes", cardController.getAllCardTypes());
			model.addAttribute("user", user);
			model.addAttribute("existingSecurityCode", new String());
			model.addAttribute("states", stateDetailsController.getAllStates());
			
			return "newUserPayment";
		}
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
	@PostMapping(value = "/submitShippingAddressSignUp", params="submit")
	public String createShippingDetails(@Validated @ModelAttribute("shippingDetails") ShippingAddress_Form details, BindingResult result, @RequestParam("stateId") String stateId, @RequestParam("userId") Long userId, Model model) {
		/* https://www.geeksforgeeks.org/how-to-call-private-method-from-another-class-in-java-with-help-of-reflection-api/ */
		/* trying to get loadUser from UserDetailsController */
//		Method m = null;
//		try {
//			m = UserDetailsController.class.getDeclaredMethod("loadUserData", Model.class);
//			m.setAccessible(true);
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//selectedMenu = SUB_MENU.SHIPPING_DETAILS;
		details.setState(stateDetailsController.getState(stateId));
		//model.addAttribute("selectedMenu", selectedMenu);
		if (result.hasErrors()) { // || userDetController.shippingAddressConstraintsFailed(details)) {
			// Add error messages
			User user = userRepository.findById(userId).orElse(null);
			if(!result.hasErrors() && userDetController.shippingAddressConstraintsFailed(details))
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
			
			// https://www.geeksforgeeks.org/how-to-call-private-method-from-another-class-in-java-with-help-of-reflection-api/
			/* trying to invoke loadUser from UserDetailsController */
//			try {
//				m.invoke(userDetController, model);
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}			
			return "newUserShipping";
		}
		ShippingAddress shipping = new ShippingAddress();
		User user = userRepository.findById(userId).orElse(null);
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
    
    model.addAttribute("user", user);
    model.addAttribute("cardTypes", cardController.getAllCardTypes());
    model.addAttribute("states", stateDetailsController.getAllStates());
    model.addAttribute("savedShippingDetails", shippingController.getShippingDetailsByUser(user));


    	return "redirect:/emailverification";
	}

  @GetMapping({"/forgotUser/{id}"})
  public String forgotUser(@PathVariable("id") int id, Model model) {
    setPage("email");
    model.addAttribute("page", page);
    if (id == 0) {
      setTheId(0);
      setPage("user");
    } else {
      setTheId(1);
      setPage("pass");
    }
    return "forgotUser";
  }

  @PostMapping({"/findUser"})
  public String findUser(Model model, @RequestParam("email") String email2) {
	  try {
		  if (userController.getUserByEmail(email2) == null) {
	          setPage("findUserFail");
	          model.addAttribute("page", page);
	          return "forgotUser";
	      }
	  }
	  catch (Exception e){
		  setPage("findUserFail");
          model.addAttribute("page", page);
          return "forgotUser";
	  }
      findUser = userController.getUserByEmail(email2);

      // Mask the email and add it to the model
      String maskedEmail = maskEmail(email2);
      model.addAttribute("maskedEmail", maskedEmail);

      if (getTheId() == 0) {
          email.usernameRecovery(findUser);
      }
      Random rand = new Random();
      int rand_int1 = rand.nextInt(3);

      if (rand_int1 == 0) {
          setQuestion(findUser.getSecret1());
          setAnswer(findUser.getUserSecret1());
      }
      if (rand_int1 == 1) {
          setQuestion(findUser.getSecret2());
          setAnswer(findUser.getUserSecret2());
      } else {
          setQuestion(findUser.getSecret3());
          setAnswer(findUser.getUserSecret3());
      }
      model.addAttribute("question", question);
      model.addAttribute("page", page);
      return "forgotUser";
  }
  
  private String maskEmail(String email) {
	    int index = email.indexOf('@');
	    if (index <= 3) return "****"; // if the email has 3 or fewer characters before '@', return "****"

	    String firstPart = email.substring(0, 1);
	    String middlePart = email.substring(1, index - 2);
	    String lastPart = email.substring(index - 2);

	    StringBuilder maskedMiddle = new StringBuilder();
	    for (int i = 0; i < middlePart.length(); i++) {
	        maskedMiddle.append('*');
	    }

	    return firstPart + maskedMiddle.toString() + lastPart;
	}
	

  @PostMapping({"/answerQuestion"})
  public String answerQuestion(@RequestParam("answer") String answer2, Model model) {
    if (answer2.equals(getAnswer())) {
      setPage("reset");
      model.addAttribute("page", page);
    } else {
      setPage("wrong");
      model.addAttribute("question", getQuestion());
      model.addAttribute("page", page);
    }

    return "forgotUser";
  }

  @PostMapping("/resetPassword")
  public String resetPassword(
      @RequestParam("pass") String pass, @RequestParam("passC") String pass2, Model model) {
    if (pass.length() < 6 || !(pass.equals(pass2))) {
      setPage("resetfail");
      model.addAttribute("page", page);
      return "forgotUser";
    } else {

      findUser.setPassword(passwordEncoder.encode(pass));
      userRepository.save(findUser);
      setPage("resetSuccess");
      model.addAttribute("page", page);
      return "forgotUser";
    }
  }

  @RequestMapping({"/emailverification"})
  public String showverify(Model model) {

    setPage("startverify");

    model.addAttribute("page", getPage());
    model.addAttribute("users", userRepository.findAll());
    return "emailverification";
  }

  @RequestMapping("/verify")
  public String verifyUser(
      @RequestParam("username") String name,
      @RequestParam("verification") String verification,
      Model model) {
    if (userController.getUserByUsername(name) == null) {
      setPage("wronguser");
      model.addAttribute("username", name);
      model.addAttribute("verification", verification);
      model.addAttribute("page", getPage());
      model.addAttribute("message", "Wrong Username!");
      return "emailverification";
    }

    User user = userController.getUserByUsername(name);
    if (user.getEnabled()) {
      setPage("alreadyverified");
      model.addAttribute("page", getPage());
      model.addAttribute("message", "User already Verified!");
      return "emailverification";
    }
    if (verification.equals(user.getEmailVerification())) {
      setPage("verifysuccess");
      model.addAttribute("page", getPage());
      model.addAttribute("message", "User Verified successfully!");
      user.setEnabled(true);
      userRepository.save(user);
      return "emailverification";
    } else {
      setPage("wrongcode");
      model.addAttribute("username", name);
      model.addAttribute("verification", verification);
      model.addAttribute("page", getPage());
      model.addAttribute("message", "Wrong verification code!");
      return "emailverification";
    }
  }
  
  public boolean paymentDetailsInvalid(PaymentDetails_Form form) {
		return paymentDetailsExpired(form);
  }
  
  public boolean paymentDetailsExpired(PaymentDetails_Form form) {
		if (form.getExpirationDate() == null || form.getExpirationDate().length() == 0)
			return false;
		// Check if current date is on or past the expiration date
		LocalDate expirDate;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy dd");
		try {
			expirDate = LocalDate.parse(form.getExpirationDate()+" 01", formatter);
			return expirDate.compareTo(LocalDate.now()) < 0;
		}
		catch (Exception e) {
			return false;
		}
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public int getTheId() {
    return theId;
  }

  public void setTheId(int theId) {
    this.theId = theId;
  }

  public boolean isShowedSuccess() {
    return showedSuccess;
  }

  public void setShowedSuccess(boolean showedSuccess) {
    this.showedSuccess = showedSuccess;
  }
  
  
  
}
