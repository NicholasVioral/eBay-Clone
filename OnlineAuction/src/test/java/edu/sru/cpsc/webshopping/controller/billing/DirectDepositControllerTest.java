package edu.sru.cpsc.webshopping.controller.billing;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails;
import edu.sru.cpsc.webshopping.repository.billing.DirectDepositDetailsRepository;

public class DirectDepositControllerTest {
	private Logger log= Logger.getLogger(getClass());

@BeforeAll
static void initAll() {
	
}
@BeforeEach
void init() {
	
}
@Test
@DisplayName("delete Direct Deposit Details")
public void deleteDirectDepositDetails() {
	try {
		log.info("Starting Execution");
		
		DirectDepositDetails details= null;
		DirectDepositDetailsRepository directdepositdetailsrepository= null;
		
		DirectDepositController directdepositcontroller= new DirectDepositController(directdepositdetailsrepository);
		directdepositcontroller.deleteDirectDepositDetails(details);
		Assertions.assertTrue(true);
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
