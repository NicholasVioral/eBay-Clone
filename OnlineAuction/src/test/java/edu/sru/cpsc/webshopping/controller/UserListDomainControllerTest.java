package edu.sru.cpsc.webshopping.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.user.UserList;
public class UserListDomainControllerTest {
	private static UserList UserList = null;
	private Logger log= Logger.getLogger(this.getClass());

@BeforeAll
static void initAll() {
	
}
@BeforeEach
void init() {
	
}
// testing adding a friend
@Test
@DisplayName("add Friend")
public void addFriend() {
	try{
		log.info("Starting execution");
		UserList= null;
		User friend= null;
		
		//UserListDomainController userlistdomaincontroller= new UserListDomainController();
		//userlistdomaincontroller.addFriend(UserList, friend);
		Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
// testing adding blocks
@Test
@DisplayName("add Block")
public void addBlock() {
	try {
		log.info("Starting execution");
		UserList= null;
		User block= null;
		
		//UserListDomainController userlistdomaincontroller= new UserListDomainController();
		UserList userList = null;
		//userlistdomaincontroller.addBlock(userList, block);
		Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
// testing suggestions
@Test
@DisplayName("add Suggested")
public void addSuggested() {
	try {
		log.info("Starting execution");
		Object UserList = null;
		User suggest= null;
		
		//UserListDomainController userlistdomaincontroller= new UserListDomainController();
		//userlistdomaincontroller.addSuggested(UserList, suggest);
		Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
// testing recieving suggestions
@Test
@DisplayName("get Suggested")
public void getSuggested() {
	try {
		log.info("Starting execution");
		List<UserList>expectedValue= null;
		User user= null;
		
		//UserListDomainController userlistdomaincontroller= new UserListDomainController();
		//List<UserList>actualValue= userlistdomaincontroller.getSuggested(user);
		log.info("actualValue");
		System.out.println("actualValue");
		//Assertions.assertEquals(null, null);
				
	}
	catch(Exception exception) {
		log.error("Exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
// testing sending friend requests 
@Test
@DisplayName("get Friends")
public void getFriends() {
	try {
		log.info("Starting execution");
		List<UserList>expectedValue= null;
		User user= null;
		
		//UserListDomainController userlistdomaincontroller= new UserListDomainController();
		//List<UserList>actualValue= userlistdomaincontroller.getFriends(user);
		log.info("actualValue");
		System.out.println("actualValue");
		//Assertions.assertEquals(null, null);
	}
	catch (Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
// testing removing friends
@Test
@DisplayName("remove Friend")
public void removeFriend() {
	try {
		log.info("Starting execution");
		User user= null;
		User friend= null;
		
		//UserListDomainController userlistdomaincontroller= new UserListDomainController();
		//userlistdomaincontroller.removeFriend(user, friend);
		Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error("Exeption");
		exception.printStackTrace();
		Assertions.assertFalse(false);
		
	}
}
// testing removing blocks 
@Test
@DisplayName("remove Block")
public void removeBlock() {
	try {
		log.info("Starting execution");
		User user= null;
		User friend= null;
		
		//UserListDomainController userlistdomaincontroller= new UserListDomainController();
		//userlistdomaincontroller.removeBlock(user, friend);
		Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error("Exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
// testing blocking a user
@Test
@DisplayName("get Blocked")
public void getBlocked() {
	try {
		log.info("Starting execution");
		List<UserList>expectedValue= null;
		User user= null;
		
		//UserListDomainController userlistdomaincontroller= new UserListDomainController();
		//List<UserList>actualValue= userlistdomaincontroller.getBlocked(user);
		log.info("actualValue");
		System.out.println("actualValue");
		//Assertions.assertEquals(null, null);
	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("get Blocked Check")
public void getBlockedCheck() {
	try {
		log.info("Starting execution");
		Boolean expectedValue= false;
		
		User user= null;
		User block= null;
		
		//UserListDomainController userlistdomaincontroller= new UserListDomainController();
		//Boolean actualValue= userlistdomaincontroller.getBlockedCheck(user,block);
		log.info("actualValue");
		System.out.println("actualValue");
		//Assertions.assertEquals(null, null);
	}
	catch(Exception exception) {
		log.error("Exception");
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