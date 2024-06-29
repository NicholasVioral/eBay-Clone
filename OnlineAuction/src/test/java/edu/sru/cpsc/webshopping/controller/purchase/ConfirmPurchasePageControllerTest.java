package edu.sru.cpsc.webshopping.controller.purchase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails_Form;
import edu.sru.cpsc.webshopping.domain.billing.Paypal;
import edu.sru.cpsc.webshopping.domain.billing.Paypal_Form;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.billing.StateDetails;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.repository.billing.StateDetailsRepository;
import edu.sru.cpsc.webshopping.service.UserService;

/**
 * jUnit code for testing the ConfirmPurchasePage
 */
@SpringBootTest(classes = {ConfirmPurchasePageControllerTest.class})
@AutoConfigureMockMvc
public class ConfirmPurchasePageControllerTest {
	@Autowired
	private ConfirmPurchasePageController pageController;
	@Autowired
	private MarketListingDomainController mlDomainController;
	@Autowired
	private UserController userController;
	@Autowired
	private TransactionController transController;
	@Autowired
	private WidgetController widgetController;
	private MarketListing newListing;
	private ShippingAddress address;
	private Transaction trans;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Mock
	private Model model;
	@Mock
	private BindingResult result;
	@Autowired
	private StateDetailsRepository stateRepository;
	private PaymentDetails validDetailsForm;
	@Autowired
	private UserService userService;
	
	private User buyer;
	private User seller;
	private Principal principal;
	
	/**
	 * Setups data to use for tests
	 */
	@BeforeEach
	public void InitializeTest() {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.newListing = new MarketListing();
		// Initializes User
		seller = new User();
		seller.setPassword("");
		seller = userController.addUser(seller, mock(BindingResult.class));
		buyer = new User();
		buyer.setPassword("");
		buyer = userController.addUser(buyer, mock(BindingResult.class));

		principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
        User user = new User();
        user.setUsername("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(user);

		// Add payment details to user
		validDetailsForm = new PaymentDetails();
		validDetailsForm.setCardholderName("TestName");
		validDetailsForm.setCardNumber("1111444455557777");
		validDetailsForm.setCardType("Discover");
		validDetailsForm.setExpirationDate(LocalDate.now().toString());
		validDetailsForm.setSecurityCode("1234");
		// Add Paypal details to user
		Paypal validPaypal = new Paypal();
		validPaypal.setPaypalLogin("testemail@test.com");
		validPaypal.setPaypalPassword("passwd");
		userController.updatePaypalDetails(validPaypal, principal);
		// Initializes Widget and MarketListing
		newListing.setDeleted(false);
		newListing.setPricePerItem(new BigDecimal(50.05));
		newListing.setQtyAvailable(50);
		newListing.setSeller(seller);
		newListing.setWidgetSold(widgetController.addWidget(new Widget(), null));
		newListing = mlDomainController.addMarketListing(newListing);
		// Initializes Shipping/State Information
		StateDetails state = new StateDetails();
		state.setStateName("PA");
		state.setSalesTaxRate(new BigDecimal(7));
		state = stateRepository.save(state);
		address = new ShippingAddress();
		address.setPostalCode("12345");
		address.setRecipient("Tester");
		address.setState(state);
		address.setStreetAddress("Street");
		// Initializes Transaction Information
		trans = new Transaction();
		trans.setMarketListing(newListing);
		trans.setQtyBought(10);
		trans.setSeller(seller);
		trans.setBuyer(buyer);
		trans.setTotalPriceBeforeTaxes(newListing.getPricePerItem().multiply(new BigDecimal(10)));
	}
	
	/**
	 * Tests that the page opens properly
	 * @throws Exception
	 */
	@Test
	void OpenPage() throws Exception {
		String result = pageController.openConfirmPurchasePage(address, newListing, trans, model, principal);
		Assertions.assertEquals("confirmPurchase", result);
	}
	
	/**
	 * Tests that a purchase is successful when using valid payment details
	 * @throws Exception is thrown if the test fails
	 */
	@Test
	void CCPurchaseSuccess() throws Exception {
		PaymentDetails_Form validDetails = new PaymentDetails_Form();
		validDetails.setCardholderName("TestName");
		validDetails.setCardNumber("1111444455557777");
		validDetails.setCardType("Discover");
		validDetails.setExpirationDate(LocalDate.now().toString());
		validDetails.setSecurityCode("1234");
		
		pageController.openConfirmPurchasePage(address, newListing, trans, model, principal);
		
		RequestBuilder request = MockMvcRequestBuilders.post("/confirmPurchase/submitPurchase")
				.flashAttr("paymentDetails", validDetails)
				.param("submit", "true");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("redirect:/index"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		// Check that both buyer and seller have a new transaction from purchase
		Iterable<Transaction> transactions = transController.getUserPurchases(buyer);
		Assertions.assertEquals(1, transactions.spliterator().getExactSizeIfKnown());
		transactions = transController.getUserSoldItems(seller);
		Assertions.assertEquals(1, transactions.spliterator().getExactSizeIfKnown());
	}
	
	/**
	 * Tests that a purchase fails for all invalid cases
	 * @throws Exception is thrown if the test fails
	 */
	@Test
	void CCPurchaseFailure() throws Exception {
		PaymentDetails_Form details = new PaymentDetails_Form();
		details.setCardholderName("TestName");
		details.setCardNumber("1111444455557777");
		details.setCardType("Discover");
		details.setExpirationDate(LocalDate.now().toString());
		details.setSecurityCode("1234");
		
		pageController.openConfirmPurchasePage(address, newListing, trans, model, principal);
		
		RequestBuilder request = MockMvcRequestBuilders.post("/confirmPurchase/submitPurchase")
				.flashAttr("paymentDetails", details)
				.param("submit", "true");
		
		// Tests all form validation failures
		details.setCardholderName("");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setCardholderName("TestName");
		details.setCardNumber("242");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setCardNumber("1111444455557777");
		details.setCardType("");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setCardType("Discover");
		details.setExpirationDate("");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setExpirationDate(LocalDate.now().toString());
		details.setExpirationDate("2022-01-01");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setExpirationDate(LocalDate.now().toString());
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setSecurityCode("");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setSecurityCode("1234");
		
		// Tests that passed details must match saved details
		details.setCardholderName("OtherName");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setCardholderName("TestName");
		details.setCardNumber("1111111111111111");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setCardNumber("1111444455557777");
		details.setCardType("Visa");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setCardType("Discover");
		details.setExpirationDate(LocalDate.of(2050, 1, 2).toString());
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setExpirationDate(LocalDate.now().toString());
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setSecurityCode("4457");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		details.setSecurityCode("1234");
		
		// Check that no transactions were added
		Iterable<Transaction> transactions = transController.getUserPurchases(buyer);
		Assertions.assertEquals(0, transactions.spliterator().getExactSizeIfKnown());
		transactions = transController.getUserSoldItems(seller);
		Assertions.assertEquals(0, transactions.spliterator().getExactSizeIfKnown());
	}
	
	/**
	 * Tests that a Paypal purchase is successful if the Paypal details are valid
	 * @throws Exception is thrown if the test fails
	 */
	@Test
	public void PaypalPurchaseSuccess() throws Exception {
		Paypal_Form validDetails = new Paypal_Form();
		validDetails.setPaypalLogin("testemail@test.com");
		validDetails.setPaypalPassword("passwd");
		
		pageController.openConfirmPurchasePage(address, newListing, trans, model, principal);
		
		RequestBuilder request = MockMvcRequestBuilders.post("/confirmPurchase/submitPurchasePaypal")
				.flashAttr("paypal", validDetails)
				.param("submit", "true");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("redirect:/homePage"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		// Check that both buyer and seller have a new transaction from purchase
		Iterable<Transaction> transactions = transController.getUserPurchases(buyer);
		Assertions.assertEquals(1, transactions.spliterator().getExactSizeIfKnown());
		transactions = transController.getUserSoldItems(seller);
		Assertions.assertEquals(1, transactions.spliterator().getExactSizeIfKnown());
	}
	
	/**
	 * Tests that a Paypal purchase fails if the Paypal details are invalid
	 * @throws Exception is thrown if the test fails
	 */
	@Test
	public void PaypalPurchaseFailure() throws Exception {
		Paypal_Form validDetails = new Paypal_Form();
		validDetails.setPaypalLogin("testemail@test.com");
		validDetails.setPaypalPassword("passwd");
		
		pageController.openConfirmPurchasePage(address, newListing, trans, model, principal);
		
		RequestBuilder request = MockMvcRequestBuilders.post("/confirmPurchase/submitPurchasePaypal")
				.flashAttr("paypal", validDetails)
				.param("submit", "true");
		
		// Test all validation fields
		validDetails.setPaypalLogin("");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		validDetails.setPaypalLogin("testemail@test.com");
		validDetails.setPaypalPassword("");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("confirmPurchase"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		validDetails.setPaypalPassword("passwd");
		
		// Check that no transactions were added
		Iterable<Transaction> transactions = transController.getUserPurchases(buyer);
		Assertions.assertEquals(0, transactions.spliterator().getExactSizeIfKnown());
		transactions = transController.getUserSoldItems(seller);
		Assertions.assertEquals(0, transactions.spliterator().getExactSizeIfKnown());
	}
	
	/**
	 * Tests that cancel purchase is functioning
	 * @throws Exception is thrown if the test fails
	 */
	@Test
	public void CancelPurchase() throws Exception {
		pageController.openConfirmPurchasePage(address, newListing, trans, model, principal);
		RequestBuilder request = MockMvcRequestBuilders.post("/confirmPurchase/submitPurchase")
				.param("cancel", "true");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("redirect:/viewMarketListing/" + newListing.getId()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		pageController.openConfirmPurchasePage(address, newListing, trans, model, principal);
		request = MockMvcRequestBuilders.post("/confirmPurchase/submitPurchasePaypal")
				.param("cancel", "true");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("redirect:/viewMarketListing/" + newListing.getId()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
}
