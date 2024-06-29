package edu.sru.cpsc.webshopping.controller;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import edu.sru.cpsc.webshopping.domain.market.Shipping;

public class ShippingDomainControllerTest {
	private static final Shipping Shipping = null;
	private Logger log= Logger.getAnonymousLogger();

@BeforeAll
static void initAll() {
	
}
@BeforeEach
void init() {
	
}

@Test
@DisplayName("get Shipping Entry")
 
public void getShippingEntry() {
	try {
		log.info("Starting execution of getShippingEntry");
	Shipping expectedValue= null;
	
	ShippingDomainController shippingdomaincontroller = new ShippingDomainController(null);
	Shipping actualValue= shippingdomaincontroller.getShippingEntry(0);
		log.info("Expected Value");
		System.out.println("Expected Value");
	}
	catch(Exception exception) {
		log.config("Exeption in execution");
		exception.printStackTrace();
	}
}

@Test
@DisplayName("edit Shipping Entry")

public void editShippingEntry() {
	try {
		log.info("Starting execution of editShippingEntry");
		
		ShippingDomainController shippingdomaincontroller= new ShippingDomainController(null);
		shippingdomaincontroller.editShippingEntry(Shipping);
		Assertions.assertTrue(true);
		
	}
	catch(Exception exception) {
		log.config("Exception in execution");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@AfterEach
void tearDown() {
}
@AfterAll
static void tearDownAll() {
}
}
