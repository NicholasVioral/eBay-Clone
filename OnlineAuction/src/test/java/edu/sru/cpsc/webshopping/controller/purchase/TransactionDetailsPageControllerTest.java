package edu.sru.cpsc.webshopping.controller.purchase;

import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.security.Principal;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.billing.StateDetails;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Shipping;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.repository.billing.StateDetailsRepository;
import edu.sru.cpsc.webshopping.service.UserService;

@SpringBootTest(classes = {TransactionDetailsPageControllerTest.class})
@AutoConfigureMockMvc
public class TransactionDetailsPageControllerTest {
	@Autowired
	private TransactionDetailsPageController transPageController;
	@Autowired
	private TransactionController transController;
	@Autowired
	private MarketListingDomainController mlDomainController;
	@Autowired
	private StateDetailsRepository stateRepository;
	@Autowired
	private UserController userController;
	@Autowired
	private WidgetController widgetController;
	private MarketListing newListing;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Mock
	private Model model;
	@Mock
	private BindingResult result;
	@Autowired
	private ObjectMapper mapper;
	@Mock
	private Principal principal;
	@Mock
	private UserService userService;
	
	private User buyer;
	private User seller;
	private Transaction trans;
	
	@BeforeEach
	public void initializeTest() {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		seller = new User();
		seller.setPassword("");
		seller = userController.addUser(seller, mock(BindingResult.class));
		buyer = new User();
		buyer.setPassword("");
		buyer = userController.addUser(buyer, mock(BindingResult.class));
		MarketListing newListing = new MarketListing();
		newListing.setDeleted(false);
		newListing.setPricePerItem(new BigDecimal(50.05));
		newListing.setQtyAvailable(50);
		newListing.setSeller(seller);
		Widget widget = new Widget();
		Category category = new Category("appliance");
		widget.setCategory(category);
		widgetController.addWidget(widget, result);
		newListing.setWidgetSold(widgetController.addWidget(widget, null));
		newListing = mlDomainController.addMarketListing(newListing);
		// Initializes Shipping/State Information
		StateDetails state = new StateDetails();
		state.setStateName("PA");
		state.setSalesTaxRate(new BigDecimal(7));
		state = stateRepository.save(state);
		ShippingAddress address = new ShippingAddress();
		address = new ShippingAddress();
		address.setPostalCode("12345");
		address.setRecipient("Tester");
		address.setState(state);
		address.setStreetAddress("Street");
		// Initializes Transaction Information
		Shipping shippingEntry = new Shipping();
		shippingEntry.setAddress(address);
		trans = new Transaction();
		trans.setMarketListing(newListing);
		trans.setQtyBought(10);
		trans.setSeller(seller);
		trans.setBuyer(buyer);
		trans.setTotalPriceBeforeTaxes(newListing.getPricePerItem().multiply(new BigDecimal(10)));
		trans.setShippingEntry(shippingEntry);
		trans = transController.addTransaction(trans);
	}
	
	/**
	 * Tests the the page opens successfully assuming the required data is set
	 * @throws Exception
	 */
	@Test
	void openPage() throws Exception {
		String result = transPageController.purchaseDetails(trans.getId(), model, principal);
		Assertions.assertEquals("transactionDetails", result);
		result = transPageController.purchaseDetails(trans.getId(), model, principal);
		Assertions.assertEquals("transactionDetails", result);
	}
	
	/**
	 * Tests that the shipping information is updated successfully when valid information is provided
	 * Tests from the perspective of the seller
	 * @throws Exception
	 */
	@Test
	void submitShippingUpdateSuccess() throws Exception {
		transPageController.purchaseDetails(trans.getId(), model, principal);
		Shipping form = trans.getShippingEntry();
		form.setCarrier("USPS");
		RequestBuilder request = MockMvcRequestBuilders.post("/viewTransactionDetails/submitShippingUpdate")
				.flashAttr("shipping", form);
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("redirect:/homePage"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		// Test that the database updated successfully
		Transaction updatedTrans = transController.getTransaction(trans.getId());
		Assertions.assertEquals(form.getCarrier(), updatedTrans.getShippingEntry().getCarrier());
	}
	
	/**
	 * Tests that all of the failure conditions for updating shipping information prevent updates
	 * from being made
	 * Tests from the perspective of the seller
	 * @throws Exception
	 */
	@Test
	void submitShippingUpdateFailure() throws Exception {
		transPageController.purchaseDetails(trans.getId(), model, principal);
		Shipping form = trans.getShippingEntry();
		RequestBuilder request = MockMvcRequestBuilders.post("/viewTransactionDetails/submitShippingUpdate")
				.flashAttr("shipping", form);
		
		// Tests null input restrictions
		form.setCarrier("");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("transactionDetails"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		// Tests date restrictions
		// Ensures that arrival date is always on or after shipping date
		form.setCarrier("USPS");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("transactionDetails"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	/**
	 * Tests that the Transaction can be deleted under valid conditions, and that the
	 * deletion is applied
	 * @throws Exception
	 */
	@Test
	void deleteTransactionSuccessful() throws Exception {
		transPageController.purchaseDetails(trans.getId(), model, principal);
		RequestBuilder request = MockMvcRequestBuilders.post("/viewTransactionDetails/deleteTransaction");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("redirect:/homePage"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		Transaction updatedTrans = transController.getTransaction(trans.getId());
		// Tests updates to database
		Assertions.assertEquals(null, updatedTrans);
		Assertions.assertEquals(trans.getMarketListing().getQtyAvailable() + trans.getQtyBought(),
				mlDomainController.getMarketListing(trans.getMarketListing().getId()).getQtyAvailable());
	}
	
	/**
	 * Tests that failure conditions for deleting a Transaction are successful,
	 * and that the deletion is not applied
	 * @throws Exception
	 */
	@Test
	void deleteTransactionFailure() throws Exception {
		// Update with shipping information
		transPageController.purchaseDetails(trans.getId(), model, principal);
		Shipping form = trans.getShippingEntry();
		form.setCarrier("USPS");
		RequestBuilder request = MockMvcRequestBuilders.post("/viewTransactionDetails/submitShippingUpdate")
				.flashAttr("shipping", form);
		mvc.perform(request);
		// Run delete request
		transPageController.purchaseDetails(trans.getId(), model, principal);
		request = MockMvcRequestBuilders.post("/viewTransactionDetails/deleteTransaction");
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.view().name("transactionDetails"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		Transaction updatedTrans = transController.getTransaction(trans.getId());
		Assertions.assertNotEquals(null, updatedTrans);
		Assertions.assertEquals(trans.getMarketListing().getQtyAvailable(),
				mlDomainController.getMarketListing(trans.getMarketListing().getId()).getQtyAvailable());
	}
	
}
