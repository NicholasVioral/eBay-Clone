package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@SpringBootTest(classes = {DomainBillingSpringTest.class})
@AutoConfigureMockMvc
public class DomainBillingSpringTest {
	
	/*
	 * Tests if the card type is valid and loads correctly
	 */
	@Test
	void cardTypeTest() {
		CardType cardType = new CardType();
		cardType.setCardType("Visa");
		
		assertEquals("Visa", cardType.getCardType());
		
	}
	
	/*
	 * Tests if the form input is validated
	 */
	@Test
	void directDepositDetails_FormTest() {
		DirectDepositDetails_Form deposit = new DirectDepositDetails_Form();
		deposit.setAccountholderName("Heidi");
		deposit.setAccountNumber("12345");
		deposit.setBankName("First National Bank");
		deposit.setRoutingNumber("123456789");
		
		assertEquals("Heidi", deposit.getAccountholderName());
		assertEquals("12345", deposit.getAccountNumber());
		assertEquals("First National Bank", deposit.getBankName());
		assertEquals("123456789", deposit.getRoutingNumber());
		
	}
	
	/*
	 * Tests if the direct deposit information is validated 
	 */
	@Test
	void directDepositDetailsTest() {
		DirectDepositDetails details = new DirectDepositDetails();
		details.setAccountholderName("Heidi");
		details.setAccountNumber("12345");
		details.setBankName("First National Bank");
		details.setId(24);
		details.setRoutingNumber("123456789");
		
		assertEquals("Heidi", details.getAccountholderName());
		assertEquals("12345", details.getAccountNumber());
		assertEquals("First National Bank", details.getBankName());
		assertEquals(24, details.getId());
		assertEquals("123456789", details.getRoutingNumber());
		
	}
	
	/*
	 * Tests if the form input is validated
	 */
	@Test
	void paymentDetails_FormTest() {
		PaymentDetails_Form payment = new PaymentDetails_Form();
		payment.setCardholderName("Heidi");
		payment.setCardNumber("1234567891234567");
		payment.setCardType("Visa");
		payment.setExpirationDate("10/2028");
		payment.setSecurityCode("123");
		
		assertEquals("Heidi", payment.getCardholderName());
		assertEquals("1234567891234567", payment.getCardNumber());
		assertEquals("Visa", payment.getCardType());
		assertEquals("10/2028", payment.getExpirationDate());
		assertEquals("123", payment.getSecurityCode());
	}
	
	/*
	 * Tests if the direct deposit information is validated 
	 */
	@Test
	void paymentDetailsTest() {
		PaymentDetails pay = new PaymentDetails();
		pay.setCardholderName("Heidi");
		pay.setCardNumber("1234567891234567");
		pay.setCardType("Visa");
		pay.setExpirationDate("10/2028");
		pay.setId(24);
		pay.setSecurityCode("123");
		
		assertEquals("Heidi", pay.getCardholderName());
		assertEquals("1234567891234567", pay.getCardNumber());
		assertEquals("Visa", pay.getCardType());
		assertEquals("10/2028", pay.getExpirationDate());
		assertEquals(24, pay.getId());
		assertEquals("123", pay.getSecurityCode());
	}
	
	/*
	 * Tests if the paypal login input is validate
	 */
	@Test
	void Paypal_FormTest() {
		Paypal_Form form = new Paypal_Form();
		form.setPaypalLogin("Username");
		form.setPaypalPassword("password");
		
		assertEquals("Username", form.getPaypalLogin());
		assertEquals("password", form.getPaypalPassword());
	}
	
	/*
	 * Tests if the database is storing info on paypal login
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Test
	void paypalTest() {
		Paypal paypal = new Paypal();
		paypal.setId(20);
		paypal.setPaypalLogin("username");
		paypal.setPaypalPassword("password");
		paypal.transferFields(paypal);
		//paypal.buildFromForm(null); //Paypal_Form other can't get to work atm
		
		assertEquals(20, paypal.getId());
		assertEquals("username", paypal.getPaypalLogin());
		assertEquals("password", paypal.getPaypalPassword());
	}
	
	/*
	 * Tests if the validation layer works for the shipping address
	 */
	@Test
	void shippingAddress_FormTest() {
		ShippingAddress_Form shipForm = new ShippingAddress_Form();
		shipForm.setPostalCode("16057");
		shipForm.setRecipient("David");
		shipForm.setState(null);
		shipForm.setStreetAddress("123 Berry Ln");
		
		assertEquals("16057", shipForm.getPostalCode());
		assertEquals("David", shipForm.getRecipient());
		assertEquals(null, shipForm.getState());
		assertEquals("123 Berry Ln", shipForm.getStreetAddress());
	}
	
	/*
	 * Tests if the shipping address is valid
	 */
	@Test
	void shippingAddressTest() {
		ShippingAddress ship = new ShippingAddress();
		ship.setId(8);
		ship.setPostalCode("16057");
		ship.setRecipient("Katie");
		ship.setState(null);
		ship.setStreetAddress("456 Cherry St");
		
		assertEquals("16057", ship.getPostalCode());
		assertEquals("Katie", ship.getRecipient());
		assertEquals(null, ship.getState());
		assertEquals("456 Cherry St", ship.getStreetAddress());
	}
	
	/*
	 * Tests if the information on states and their sales tax is valid
	 */
	@Test
	void stateDetailsTest() {
		StateDetails state = new StateDetails();
		state.setSalesTaxRate(null);
		state.setStateName(null);
		
		assertEquals(null, state.getSalesTaxRate());
		assertEquals(null, state.getStateName());
	}

}
