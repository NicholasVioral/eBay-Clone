package edu.sru.cpsc.webshopping.controller.billing;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import edu.sru.cpsc.webshopping.domain.billing.CardType;
public class CardTypeControllerTest {
	private Logger log= Logger.getLogger(getClass());

@BeforeAll
static void initAll() {

}
@BeforeEach
void init() {
	
}
@Test
@DisplayName("get All Card Types")
public void getAllCardTypes() {
	try {
		log.info("Starting execution");
		
		Iterable<CardType>expectedValue= null;
		
		CardTypeController cardtypecontroller= new CardTypeController(null);
		Iterable<CardType>actualValue= cardtypecontroller.getAllCardTypes();
		log.info("actualValue");
		System.out.println("actualValue");
		
	}
	catch(Exception exception) {
		log.error("exception");
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
