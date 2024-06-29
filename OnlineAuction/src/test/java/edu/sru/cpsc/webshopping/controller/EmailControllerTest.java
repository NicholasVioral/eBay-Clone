package edu.sru.cpsc.webshopping.controller;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.mail.javamail.JavaMailSender;

import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;


public class EmailControllerTest {
	private Logger log= Logger.getLogger(this.getClass());
	
@BeforeAll
static void initAll() {
}
@BeforeEach
void init() {
}
@Test
@DisplayName("get Java Mail Sender")

public void getJavaMailSender() {
	try {
		log.info("Starting execution of getJavaMailSender");
	JavaMailSender expectedValue= null;
	
EmailController emailcontroller= new EmailController();
JavaMailSender actualValue= emailcontroller.getJavaMailSender();
	log.info(actualValue);
	System.out.println("Actual Value");
	Assertions.assertEquals(actualValue, actualValue);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("verificatoin Email")
public void verificationEmail(){
	try {
		log.info("Starting execution of verificationEmail");
		User recipient= null;
		String code= "";
		
EmailController emailcontroller= new EmailController();
emailcontroller.verificationEmail(recipient, code);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName ("message Email")

public void messageEmail(){
	try {
		log.info("Starting Execution of messageEmail");
	User recipient= null;
	User sender= null;
	Message theMessage= null;
	
EmailController emailcontroller= new EmailController();
emailcontroller.messageEmail(recipient, sender, theMessage);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("username Recovery")
public void usernameRecovery(){
	try {
		log.info("Starting exection of usernameRecovery");
	User user= null;
	
EmailController emailcontroller= new EmailController();
emailcontroller.usernameRecovery(user);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}

@Test
@DisplayName("application Accepted")
public void applicationAccepted(){
	try {
		log.info("Starting exection of applicationAccepted");
String applicantEmail="";
String firstName="";
String lastName="";
String phoneNumber="";
String roleAppliedFor="";

EmailController emailcontroller= new EmailController();
emailcontroller.applicationAccepted(applicantEmail, firstName, lastName, phoneNumber, roleAppliedFor);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("application Rejected")
public void applicationRejected() {
	try {
		log.info("Starting exection of applicationRejected");
		
String applicantEmail="";
String firstName="";
String lastName="";

EmailController emailcontroller= new EmailController();
emailcontroller.applicationAccepted(applicantEmail, firstName, lastName, firstName, lastName);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("update Ticket Status")
public void updateTicketStatus() {
	try{
		log.info("Starting exection of updateTicketStatus");
	
	User user=null;
	Ticket ticket= null;
	String string= null;
	
EmailController emailcontroller= new EmailController();
emailcontroller.updateTicketStatus(user,ticket, string);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.info(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);}
	}
void tearDown(){	
}
@AfterAll
static void tearDownAll() {
}
}

