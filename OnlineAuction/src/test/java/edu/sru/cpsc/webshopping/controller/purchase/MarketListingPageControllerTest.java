package edu.sru.cpsc.webshopping.controller.purchase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.security.Principal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.controller.WidgetController;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.service.UserService;

/**
 * jUnit code for testing the ViewMarketListing page
 */
@SpringBootTest(classes = {MarketListingPageControllerTest.class})
@AutoConfigureMockMvc
public class MarketListingPageControllerTest {
	@Autowired
	private MarketListingPageController pageController;
	@Autowired
	private MarketListingDomainController mlDomainController;
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
	private UserService userService;
	@Mock
	private Principal principal;
	
	/**
	 * Setups data to use for tests
	 */
	@BeforeEach
	public void InitializeTest() {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.newListing = new MarketListing();
		// Initializes User
		User user = new User();
		user.setPassword("");
		
        when(principal.getName()).thenReturn("testuser");
        user.setUsername("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(user);

		// Initializes Widget and MarketListing
		newListing.setDeleted(false);
		newListing.setPricePerItem(new BigDecimal(50.05));
		newListing.setQtyAvailable(50);
		newListing.setSeller(userController.addUser(user, mock(BindingResult.class)));
		Widget widget = new Widget();
		Category category = new Category("appliance");
		widget.setCategory(category);
		widgetController.addWidget(widget, result);
		newListing.setWidgetSold(widgetController.addWidget(widget, null));
		newListing = mlDomainController.addMarketListing(newListing);
	}
	
	/**
	 * Tests that loading the page is successful with a valid market listing
	 * @throws Exception
	 */
	@Test
	public void LoadPageSuccess() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/viewMarketListing/" + newListing.getId()))
								.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/**
	 * Tests that attempting to access a deleted listing fails
	 * @throws Exception
	 */
	@Test
	public void LoadPageListingDeleted() throws Exception {
		try {
			User user = new User();
			newListing.setDeleted(true);
			newListing = mlDomainController.editMarketListing(newListing, user);
			mvc.perform(MockMvcRequestBuilders.get("/viewMarketListing/" + newListing.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
			throw new Exception("LoadPageListingDeleted test failed.");
		}
		catch (NestedServletException e) {
			if (e.getCause().getClass() == IllegalArgumentException.class)
				return;
			else 
				throw new Exception("LoadPageListingDeleted test failed.");
		}
	}
	
	/**
	 * Tests that attempting to view a MarketListing when not logged in fails
	 * @throws Exception
	 */
	@Test
	public void LoadPageNotLoggedIn() throws Exception {
		try {
			mvc.perform(MockMvcRequestBuilders.get("/viewMarketListing/" + newListing.getId()));
			throw new Error("LoadPageNotLoggedIn test failed.");
		}
		catch (NestedServletException e) {
			if (e.getCause().getClass() == NullPointerException.class) 
				return;
			throw new Error("LoadPageNotLoggedIn test failed.");
		}
	}
	
	/**
	 * Tests that, when the user attempts to begin a purchase under valid conditions
	 * that the confirmShippingAddressPage is opened
	 * @throws Exception
	 */
	@Test
	public void AttemptPurchaseSuccess() throws Exception {
		pageController.viewMarketListingPage(newListing.getId(), model, principal);
		Transaction currTrans = new Transaction();
		currTrans.setQtyBought(20);
		String redirectRes = pageController.attemptPurchase(currTrans, result, model, principal);
		Assertions.assertEquals("confirmShippingAddressPage", redirectRes);
	}
	
	/**
	 * Tests that attempting to purchase too many items returns the user to the viewMarketListing page
	 * @throws Exception
	 */
	@Test
	public void AttemptPurchaseFailureTooManyBought() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/viewMarketListing/" + newListing.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk());
		Transaction currTrans = new Transaction();
		currTrans.setQtyBought(60);
		String json = mapper.writeValueAsString(currTrans);
		MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/viewMarketListing/attemptPurchase")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("viewMarketListing"))
					.andReturn();
	}
	
	/**
	 * Tests that an invalid form submission returns the user to the viewMarketListing page
	 * @throws Exception
	 */
	@Test
	public void AttemptPurchaseFailureFormErrors() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/viewMarketListing/" + newListing.getId()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		Transaction currTrans = new Transaction();
		currTrans.setQtyBought(60);
		String json = mapper.writeValueAsString(currTrans);
		MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/viewMarketListing/attemptPurchase")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding("utf-8"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("viewMarketListing"))
				.andReturn();
	}
}
