package edu.sru.cpsc.webshopping.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.springframework.validation.BindingResult;

import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;

public class StatisticsDomainControllerTest {
	@Mock
	private static final Statistics Statistics = null;
	@Mock
	private static final StatsCategory Category = null;
	@Mock
	private Logger log= Logger.getLogger(null);
	
@BeforeAll
static void initAll() {
	
}
@BeforeEach
void init() {
}
@Test
@DisplayName("get Statistics")

public void getStatistics() {
	try {
		log.info("Starting execution of getStatistics");
		Statistics expectedValue= null;
		
		StatisticsDomainController statisticsdomaincontroller= new StatisticsDomainController(null);
		Statistics actualValue= statisticsdomaincontroller.getStatistics(0);
		log.info("Expected value");
		System.out.println("Expected value");
		assertEquals(null, statisticsdomaincontroller.getStatistics(0) );
	}
	
	catch(Exception exception) {
		log.config("Exception in execution");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("get Statistics Exception")

public void getStatisticsException() {
	try {
		log.info("Starting execution");
		
		StatisticsDomainController statisticsdomaincontroller= new StatisticsDomainController(null);
		statisticsdomaincontroller.getStatistics(0);
		Assertions.assertTrue(false);
		
	}
	catch(Exception exception) {
		((Object) log).equals(exception);
		exception.printStackTrace();
		Assertions.assertFalse(true);
	}
}
@Test
@DisplayName("get Statistics By Hour")

public void getStatisticsByHour() {
	try {
		log.info("Starting execution");
		
		Statistics[] expectedValue= null;
		LocalDateTime date1= null;
		LocalDateTime date2= null;
		StatsCategory category= null;
		
		StatisticsDomainController statisticsdomaincontroller= new StatisticsDomainController(null);
		Statistics[] actualValue=statisticsdomaincontroller.getStatisticsByHour(date1, date2, category);
		log.info("Expected Value");
		System.out.println("Expected Value");
		assertEquals(null,statisticsdomaincontroller.getStatisticsByHour(date1, date2, category) );
	}
	catch(Exception exception) {
		log.config("Exception in execution");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("add Statistics")

public void addStatistics() {
	try {
		log.info("Starting execution");
		
		BindingResult result= null;
		
		StatisticsDomainController statisticsdomaincontroller= new StatisticsDomainController(null);
		statisticsdomaincontroller.addStatistics(Statistics, result);
		Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.config("Exception in execution");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("get Stats By Category")

public void getStatsByCategory() {
	try {
		log.info("Starting execution of getStatsByCategory");
		Statistics[] expectedValue= null;
		
	StatisticsDomainController statisticsdomaincontroller= new StatisticsDomainController(null);
	Statistics[] actualValue= statisticsdomaincontroller.getStatsByCategory(Category);
	log.info("Expected Value");
	System.out.println("Expected Value");
	Assertions.assertEquals(expectedValue, actualValue);
	}
	catch(Exception exception) {
		log.config("Exception in execution");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("get Stats By Date")

public void getStatsByDate() {
	try {
		log.info("Starting execution");
		
		Statistics[] expectedValue= null;
		Object LocalDateTime = null;
		
		StatisticsDomainController statisticsdomaincontroller= new StatisticsDomainController(null);
		Statistics[] actualValue= statisticsdomaincontroller.getStatsByDate((java.time.LocalDateTime) LocalDateTime);
		log.info("Expected Value");
		Assertions.assertEquals(expectedValue, actualValue);
	}
	catch(Exception exception) {
		log.config("Exception in execution");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("delete Statistics")

public void deleteStatistics( ) {
	try {
		log.info("Starting execution");
		
		StatisticsDomainController statisticsdomainController= new StatisticsDomainController(null);
		statisticsdomainController.deleteStatistics((Long) null);
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
