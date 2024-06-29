package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.user.User;

@SpringBootTest(classes = {ShippingAddressTest.class})
class ShippingAddressTest {
	
	private ShippingAddress shippingAddress;
	
	@BeforeEach
	void setUp() {
		shippingAddress = new ShippingAddress();
	}
	
	@Test
	void testSetAndGetRecipient() {
		String testRecipient = "John Doe";
		shippingAddress.setRecipient(testRecipient);
		assertEquals(testRecipient, shippingAddress.getRecipient());
	}
	
	@Test
	void testSetAndGetStreetAddress() {
		String testStreetAddress = "123 Main St";
		shippingAddress.setStreetAddress(testStreetAddress);
		assertEquals(testStreetAddress, shippingAddress.getStreetAddress());
	}
	
	@Test
	void testSetAndGetExtraLocationInfo() {
		String testExtraLocationInfo = "Apt 4B";
		shippingAddress.setExtraLocationInfo(testExtraLocationInfo);
		assertEquals(testExtraLocationInfo, shippingAddress.getExtraLocationInfo());
	}
	
	@Test
	void testSetAndGetPostalCode() {
		String testPostalCode = "12345";
		shippingAddress.setPostalCode(testPostalCode);
		assertEquals(testPostalCode, shippingAddress.getPostalCode());
	}
	
	@Test
	void testSetAndGetState() {
		StateDetails testState = new StateDetails();
		shippingAddress.setState(testState);
		assertEquals(testState, shippingAddress.getState());
	}
	
	@Test
	void testSetAndGetUser() {
		User testUser = new User();
		shippingAddress.setUser(testUser);
		assertEquals(testUser, shippingAddress.getUser());
	}
	
	@Test
	void testSetAndGetCity() {
		String testCity = "New York";
		shippingAddress.setCity(testCity);
		assertEquals(testCity, shippingAddress.getCity());
	}
}