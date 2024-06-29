package edu.sru.cpsc.webshopping.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails_Form;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails_Form;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.service.UserService;
import kotlin.jvm.Throws;

@SpringBootTest(classes = {UserDetailsControllerTest.class})
@AutoConfigureMockMvc
public class UserDetailsControllerTest {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@MockBean
	private UserController userController;
	@MockBean
	private UserService userService;
	
	private User currUser;
	
	
	/**
	 * Setups data to use for tests
	 */
	@BeforeEach
	public void initializeTest() {
		//this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); //sets up the build
		// Initializes User
		currUser = new User();
		currUser.setPassword("");
		currUser = userController.addUser(currUser, mock(BindingResult.class));
		Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
        User user = new User();
        user.setUsername("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(user);
	}
	
	/**
	 * Tests that updatePaymentDetails 
	 * @throws Throws Exception if test fails
	 */
	@Test
	public void updatePaymentDetailsSuccess() throws Exception {
		PaymentDetails_Form validDetails = new PaymentDetails_Form();
		validDetails.setCardholderName("Test Name");
		validDetails.setCardNumber("1111111111111111");
		validDetails.setCardType("Discover");
		validDetails.setExpirationDate("2022/12/01");
		validDetails.setSecurityCode("1234");
		
		RequestBuilder request = MockMvcRequestBuilders.post("/submitPaymentDetailsAction")
				.flashAttr("paymentDetails", validDetails)
				.param("submit", "true");
				
		mvc.perform(request).andDo(MockMvcResultHandlers.print());
		
		// Check that a PaymentDetails has been added to the user
		Assertions.assertNotEquals(null, currUser.getPaymentDetails());
	}
	
	/**
	 * Tests that invalid PaymentDetails submissions are rejected
	 * @throws Throws Exception if test fails
	 */
	@Test
	public void updatePaymentDetailsFailure() throws Exception {
		PaymentDetails_Form invalidDetails = new PaymentDetails_Form();
		// Create valid details - test each results of each invalid field one by one
		// in order to ensure that each field has working validation
		invalidDetails.setCardholderName("Test Name");
		invalidDetails.setCardNumber("1111111111111111");
		invalidDetails.setCardType("Discover");
		invalidDetails.setExpirationDate("2022/12/01");
		invalidDetails.setSecurityCode("1234");
		invalidDetails.setCardholderName("");
		
		RequestBuilder request = MockMvcRequestBuilders.post("/submitPaymentDetailsAction")
				.flashAttr("paymentDetails", invalidDetails)
				.param("submit", "true");
		
		mvc.perform(request).andDo(MockMvcResultHandlers.print());
		
		// Check that a PaymentDetails has been added to the user
		Assertions.assertEquals(null, currUser.getPaymentDetails());
		
	}
	
	/**
	 * Tests that updateDepositDetails can add new DirectDepositDetails
	 * @throws Throws Exception if the test fails
	 */
	@Test
	@WithMockUser(username = "testuser", password = "testpass")
	public void updateDirectDepositDetailsSuccess() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/userDetails"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		mvc.perform(MockMvcRequestBuilders.get("/userDetails/depositDetails"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		DirectDepositDetails_Form validForm = new DirectDepositDetails_Form();
		validForm.setAccountholderName("Test User");
		validForm.setAccountNumber("4531531");
		validForm.setRoutingNumber("123456789");
		validForm.setBankName("Bank Name");
		RequestBuilder request = MockMvcRequestBuilders.post("/submitDepositDetailsAction")
				.flashAttr("directDepositDetails", validForm)
				.param("submit", "true");
		mvc.perform(request);
		
		Assertions.assertNotEquals(null, currUser.getDirectDepositDetails());
	}
	
	/**
	 * Tests that invalid PaymentDetails submissions are rejected
	 * @throws Throws Exception if test fails
	 */
	@Test
	public void updateDirectDepositDetailsFailure() throws Exception {
		// Test each input's validation separately to ensure each is functioning
		DirectDepositDetails_Form invalidForm = new DirectDepositDetails_Form();
		invalidForm.setAccountholderName("Test User");
		invalidForm.setAccountNumber("4531531");
		invalidForm.setRoutingNumber("123456789");
		invalidForm.setBankName("Bank Name");
		
		invalidForm.setAccountholderName("");
		RequestBuilder request = MockMvcRequestBuilders.post("/submitDepositDetailsAction")
				.flashAttr("directDepositDetails", invalidForm)
				.param("submit", "true");
		mvc.perform(request);
		Assertions.assertEquals(null, currUser.getDirectDepositDetails());
		invalidForm.setAccountholderName("Test User");
		
		invalidForm.setAccountNumber("");
		mvc.perform(request);
		Assertions.assertEquals(null, currUser.getDirectDepositDetails());
		invalidForm.setAccountNumber("4531531");
		
		invalidForm.setRoutingNumber("123");
		mvc.perform(request);
		Assertions.assertEquals(null, currUser.getDirectDepositDetails());
		invalidForm.setRoutingNumber("123456789");
		
		invalidForm.setBankName("");
		mvc.perform(request);
		Assertions.assertEquals(null, currUser.getDirectDepositDetails());
		invalidForm.setBankName("Bank Name");
		
	}
}
