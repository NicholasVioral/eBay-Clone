package edu.sru.cpsc.webshopping.controller;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import edu.sru.cpsc.webshopping.domain.market.Transaction;

public class TransactionControllerTest {
	public static TransactionController Widget = new TransactionController(null, null, null); 
	@BeforeAll
	public static void newTransaction() {
	
		Widget.addTransaction(null, null);
		Widget.addTransaction(null, null);
		Widget.cancelTransaction(null);
		Widget.equals(Widget);
		Widget.getClass();
		Widget.getTransaction(0);
		Widget.getUserPurchases(null);
		Widget.getUserSoldItems(null);
		Widget.hashCode();
		Widget.notify();
		Widget.notifyAll();
		Widget.toString();
		try {
			Widget.wait();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Widget.wait(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Widget.wait(0, 0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		@Test
		public void addTransaction() {
			Transaction transaction = new Transaction();
			assertNull(Widget.addTransaction(transaction));
		}
		@Test
		public void cancelTransaction() {
			String actual = null;
			assertsEquals(Widget.cancelTransaction(null), actual);
		}
		@Test 
		public void equals() {
			String actual = null;
			assertsEquals(Widget.equals(actual), actual);
			
		}
		@Test
		public void Class() {
			String actual = ("");
			assertsEquals(Widget.getClass(), actual);
		}
		
		@Test
		public void getTransaction() {
			int actual = (0);
			//assertsEquals(Widget.getTransaction(actual), actual);
		}
		@Test
		public void userPurchase() {
			String actual = null;
			assertsEquals(Widget.getUserPurchases(null), actual);
		}
		@Test
		public void UserSold() {
			String actual = null;
			assertsEquals(Widget.getUserSoldItems(null), actual);
		}
		@Test
		public void hashCodeTest() {
			int actual = (3);
			//assertsEquals(Widget.hashCode(), actual);
		}
		@Test
		public void notifyTest() {
			String actual = ("");
			//assertsEquals(Widget.notify(), actual);
		}
	
		@Test
		public void notifyAllTest() {
			String actual = ("");
			//assertsEquals(Widget.notifyAll(), actual);
		}
		private void assertsEquals(Object notifyAll, String actual) {
			// TODO Auto-generated method stub
			
		}
		@Test 
		public void stringTest() {
			String actual = ("");
			assertsEquals(Widget.toString(), actual);
		}
		@Test
		public void waitTest() {
			String actual = ("0");
			//assertsEquals(Widget.wait(), actual);
		}
		

		private void assertsEquals(int hashCode, String actual) {
			// TODO Auto-generated method stub
			
		}
		private void assertsEquals(Iterable<Transaction> userPurchases, String actual) {
			// TODO Auto-generated method stub
			
		}
		private void assertsEquals(boolean b, String actual) {
			// TODO Auto-generated method stub
			
		}

		
	}


