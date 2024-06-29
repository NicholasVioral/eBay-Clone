package edu.sru.cpsc.webshopping.controller.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.billing.CardTypeRepository;
import edu.sru.cpsc.webshopping.repository.billing.DirectDepositDetailsRepository;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;
import edu.sru.cpsc.webshopping.repository.billing.PaypalRepository;
import edu.sru.cpsc.webshopping.repository.billing.StateDetailsRepository;

@SpringBootTest(classes = {ControllerBillingSpringTest.class})
@AutoConfigureMockMvc
//@ContextConfiguration(classes = ControllerBillingSpringTest.class, loader = AnnotationConfigContextLoader.class)

public class ControllerBillingSpringTest {
	@Autowired
	private CardTypeRepository cardRepository;
	
	@Autowired
	private DirectDepositDetailsRepository directDepositDetailsRepository;
	
	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;
	
	@Autowired
	private PaypalRepository paypalRepository;
	
	
	@Autowired
	private StateDetailsRepository stateDetailsRepository;
	

	@Test
	/*
	 * Tests to see if the cardTypes repository is empty 
	 */
	public void cardTypeTest() throws Exception {
		
		if(cardRepository.findAll() == null) {
			System.out.println("true");
		}
			
		}
	
	@Test
	/*
	 * Tests to see if the directDeposit repository is empty 
	 */
	public void directDepositTest() {
		if(directDepositDetailsRepository == null) {
			System.out.println("true");
			
		}
	}
	
	@Test
	/*
	 * Tests to see if the paymentDetails repository is empty 
	 */
	public void paymentDetailsTest() {
		if(paymentDetailsRepository == null) {
			System.out.println("true");
		}
	}
	

	@Test
	/*
	 * Tests to see if the paypal repository deletes/is empty
	 */
	public void paypalTest() {
		if(paypalRepository == null) {
			System.out.println("true");
		}
	}
	
	@Test
	/*
	 * Tests to see if the paypal repository has matching information
	 */
	public void sellerRatingTest() {
		User user = new User();
		SellerRating rating = new SellerRating(user);
		rating.setRating(5);
		
		assertEquals(5, rating.getRating());
		assertEquals(1, rating.getNumRatings());
		assertEquals("Excellent", rating.getRatingName());	
	}
	
	@Test
	/*
	 * Tests to see if the paypal repository has information stored in DB
	 */
	public void stateDetailsTest() {
		
		if(stateDetailsRepository != null) {
			System.out.print(true);
			
		}
	}
	
	
}
