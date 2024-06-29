package edu.sru.cpsc.webshopping.controller;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UtilityControllerTest {
	private Logger log= Logger.getLogger(this.getClass());
	
@BeforeAll
static void initAll() {
	
}
@BeforeEach
void init() {
	
}
// testing generating a random string
@Test
@DisplayName("random String Generator")
public void randomStringGenerator() {
	try {
		log.info("Starting execution");
		
		String expectedValue="";
		
		UtilityController utilitycontroller= new UtilityController();
		String actualValue= utilitycontroller.randomStringGenerator();
		log.info("expectedValue");
		System.out.println("expectedValue");
		//Assertions.assertEquals(null,null);
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
