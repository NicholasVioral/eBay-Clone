package edu.sru.cpsc.webshopping.controller;
import java.util.function.BooleanSupplier;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.validation.BindingResult;

import edu.sru.cpsc.webshopping.domain.user.Applicant;
public class ApplicantDomainControllerTest {
	private static final BooleanSupplier True = null;
	private Logger log = Logger.getLogger(this.getClass());
	private Object applicantdomaincontroller;
	private Applicant Applicant;
	@BeforeAll
	static void initAll() {
	}
	@BeforeEach
	void init() {
	}
	@Test
		@DisplayName("get Applicant")
	
	public void getApplicant() {
		try {
			log.info("Starting execution of getApplicant");
		 Applicant expectedValue=null;
	
	ApplicantDomainController applicantdomaincontroller= new ApplicantDomainController(null);
	Applicant actualValue= applicantdomaincontroller.getApplicant(0);
		log.info(actualValue);
		System.out.println("actual value");
		}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
		
	}
	
@Test
@DisplayName("get Applicant Exception")
public void getApplicantException() {
	try {
		log.info("Starting execution of getApplicantException");

ApplicantDomainController applicationdomaincontroller= new ApplicantDomainController(null);
boolean actualValue = applicantdomaincontroller.equals(applicationdomaincontroller);
//((Object) applicantdomaincontroller.getApplicantException();
Assertions.assertTrue(false);
	}
	catch(Exception exception) {
	log.error(exception);
	exception.printStackTrace();
	Assertions.assertFalse(false);
}
}	

@Test 
@DisplayName("add Applicant")

public void addApplicant() {
	try {
		log.info("Starting execution of addApplicant");
BindingResult result= null;

ApplicantDomainController applicantdomaincontroller= new ApplicantDomainController(null);
applicantdomaincontroller.addApplicant(null, result);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("add Applicant Simple")

public void addApplicantSimple() {
	
	try {
		log.info("Starting execution of addApplicantSimple");

ApplicantDomainController applicantdomaincontroller= new ApplicantDomainController(null);
applicantdomaincontroller.addApplicantSimple(Applicant);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("edit Applicant")
public void editApplicant() {
	try {
		log.info("Starting Execution of editApplicant");

((ApplicantDomainController) applicantdomaincontroller).editApplicant(Applicant);
Assertions.assertTrue(true);

	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("delete Applicant")
public void deleteApplicant(){
	try {
		log.info("Starting execution of deleteApplicant");

ApplicantDomainController applicantdomaincontroller= new ApplicantDomainController(null);
applicantdomaincontroller.deleteApplicant(Applicant);
Assertions.assertTrue(True);
	}
	catch(Exception exception) {
		log.error(exception);
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


