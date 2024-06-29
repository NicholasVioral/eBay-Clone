package edu.sru.cpsc.webshopping.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.Paypal;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;

@SpringBootTest(classes = {UserTest.class})
public class UserTest {

	@Test
	void testConstructor() {
		User user = new User();
		assertNotNull(user.getSellerRating());
	}

	@Test
	void testGettersAndSetters() {
		User user = new User();
		user.setId(1L);
		assertEquals(1L, user.getId());
		
		user.setDisplayName("John Doe");
		assertEquals("John Doe", user.getDisplayName());
		
		user.setUsername("johndoe");
		assertEquals("johndoe", user.getUsername());
		
		user.setPassword("password");
		assertEquals("password", user.getPassword());
		
		user.setPasswordconf("password");
		assertEquals("password", user.getPasswordconf());
		
		user.setEmail("johndoe@example.com");
		assertEquals("johndoe@example.com", user.getEmail());
		
		user.setUserDescription("A user description");
		assertEquals("A user description", user.getUserDescription());
		
		user.setCreationDate("2022-01-01");
		assertEquals("2022-01-01", user.getCreationDate());
		
		Set<MarketListing> marketListings = new HashSet<>();
		marketListings.add(new MarketListing());
		user.setMarketListings(marketListings);
		assertEquals(marketListings, user.getMarketListings());
		
		Set<Message> inbox = new HashSet<>();
		inbox.add(new Message());
		user.setInbox(inbox);
		assertEquals(inbox, user.getInbox());
		
		user.setRole("ROLE_ADMIN");
		assertEquals("ROLE_ADMIN", user.getRole());
		
		user.setEnabled(true);
		assertTrue(user.isEnabled());
		
		PaymentDetails paymentDetails = new PaymentDetails();
		user.setDefaultPaymentDetails(paymentDetails);
		assertEquals(paymentDetails, user.getDefaultPaymentDetails());
		
		Set<PaymentDetails> paymentDetailsSet = new HashSet<>();
		paymentDetailsSet.add(paymentDetails);
		user.setPaymentDetails(paymentDetailsSet);
		assertEquals(paymentDetailsSet, user.getPaymentDetails());
		
		Paypal paypal = new Paypal();
		user.setPaypal(paypal);
		assertEquals(paypal, user.getPaypal());
		
		DirectDepositDetails directDepositDetails = new DirectDepositDetails();
		user.setDirectDepositDetails(directDepositDetails);
		assertEquals(directDepositDetails, user.getDirectDepositDetails());
		
		Set<ShippingAddress> shippingDetails = new HashSet<>();
		shippingDetails.add(new ShippingAddress());
		user.setShippingDetails(shippingDetails);
		assertEquals(shippingDetails, user.getShippingDetails());
		
		SellerRating sellerRating = new SellerRating();
		user.setSellerRating(sellerRating);
		assertEquals(sellerRating, user.getSellerRating());
	}

	@Test
	void testGettersAndSettersForSecrets() {
		User user = new User();
		user.setUserSecret1("secret1");
		assertEquals("secret1", user.getUserSecret1());
		
		user.setUserSecret2("secret2");
		assertEquals("secret2", user.getUserSecret2());
		
		user.setUserSecret3("secret3");
		assertEquals("secret3", user.getUserSecret3());
		
		user.setSecret1("What is your favorite color?");
		assertEquals("What is your favorite color?", user.getSecret1());
		
		user.setSecret2("What is your favorite food?");
		assertEquals("What is your favorite food?", user.getSecret2());
		
		user.setSecret3("What is your favorite animal?");
		assertEquals("What is your favorite animal?", user.getSecret3());
	}

	@Test
	void testGettersAndSettersForCaptcha() {
		User user = new User();
		user.setCaptcha("captcha");
		assertEquals("captcha", user.getCaptcha());
		
		user.setHiddenCaptcha("hiddenCaptcha");
		assertEquals("hiddenCaptcha", user.getHiddenCaptcha());
		
		user.setRealCaptcha("realCaptcha");
		assertEquals("realCaptcha", user.getRealCaptcha());
	}

	@Test
	void testGettersAndSettersForMarketListings() {
		User user = new User();
		Set<MarketListing> marketListings = new HashSet<>();
		marketListings.add(new MarketListing());
		user.setMarketListings(marketListings);
		assertEquals(marketListings, user.getMarketListings());
		
		Set<MarketListing> wishlistedWidgets = new HashSet<>();
		wishlistedWidgets.add(new MarketListing());
		user.setWishlistedWidgets(wishlistedWidgets);
		assertEquals(wishlistedWidgets, user.getWishlistedWidgets());
	}

	@Test
	void testGettersAndSettersForPaymentDetails() {
		User user = new User();
		PaymentDetails paymentDetails = new PaymentDetails();
		user.setDefaultPaymentDetails(paymentDetails);
		assertEquals(paymentDetails, user.getDefaultPaymentDetails());
		
		Set<PaymentDetails> paymentDetailsSet = new HashSet<>();
		paymentDetailsSet.add(paymentDetails);
		user.setPaymentDetails(paymentDetailsSet);
		assertEquals(paymentDetailsSet, user.getPaymentDetails());
	}

	@Test
	void testGettersAndSettersForShippingDetails() {
		User user = new User();
		Set<ShippingAddress> shippingDetails = new HashSet<>();
		shippingDetails.add(new ShippingAddress());
		user.setShippingDetails(shippingDetails);
		assertEquals(shippingDetails, user.getShippingDetails());
	}

	@Test
	void testGettersAndSettersForSellerRating() {
		User user = new User();
		SellerRating sellerRating = new SellerRating();
		user.setSellerRating(sellerRating);
		assertEquals(sellerRating, user.getSellerRating());
	}
}