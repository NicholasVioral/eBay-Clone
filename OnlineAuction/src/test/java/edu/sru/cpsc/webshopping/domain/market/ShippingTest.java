package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;

@SpringBootTest(classes = {ShippingTest.class})
class ShippingTest {
	
	private Shipping shipping;
	
	@BeforeEach
	void setUp() {
		shipping = new Shipping();
	}

	@Test
	void testGetId() {
		long id = 1L;
		shipping.setId(id);
		assertEquals(id, shipping.getId());
	}

	@Test
	void testGetCarrier() {
		String carrier = "UPS";
		shipping.setCarrier(carrier);
		assertEquals(carrier, shipping.getCarrier());
	}

	@Test
	void testGetAddress() {
		ShippingAddress address = new ShippingAddress();
		shipping.setAddress(address);
		assertNotNull(shipping.getAddress());
	}

	@Test
	void testGetTransaction() {
		Transaction transaction = new Transaction();
		shipping.setTransaction(transaction);
		assertEquals(transaction, shipping.getTransaction());
	}

	@Test
	void testGetTrackingNumber() {
		String trackingNumber = "1234567890";
		shipping.setTrackingNumber(trackingNumber);
		assertEquals(trackingNumber, shipping.getTrackingNumber());
	}

	@Test
	void testSetTrackingNumber() {
		String trackingNumber = "1234567890";
		shipping.setTrackingNumber(trackingNumber);
		assertEquals(trackingNumber, shipping.getTrackingNumber());
	}

	@Test
	void testSetId() {
		long id = 1L;
		shipping.setId(id);
		assertEquals(id, shipping.getId());
	}

	@Test
	void testSetCarrier() {
		String carrier = "UPS";
		shipping.setCarrier(carrier);
		assertEquals(carrier, shipping.getCarrier());
	}

	@Test
	void testSetAddress() {
		ShippingAddress address = new ShippingAddress();
		shipping.setAddress(address);
		assertNotNull(shipping.getAddress());
	}

	@Test
	void testSetTransaction() {
		Transaction transaction = new Transaction();
		shipping.setTransaction(transaction);
		assertEquals(transaction, shipping.getTransaction());
	}

}