package edu.sru.cpsc.webshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * Junit testing for Landing Page Controller
 */

@SpringBootTest
public class LandingPageControllerTest {

	@Autowired
	private WidgetController widgetController;
	@Autowired 
	private UserController userController;
	@Autowired
	private MarketListingDomainController mlDomainController;
}
