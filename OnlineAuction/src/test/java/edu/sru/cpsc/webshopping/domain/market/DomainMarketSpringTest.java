package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.user.User;

@SpringBootTest(classes = {DomainMarketSpringTest.class})
public class DomainMarketSpringTest {
	
	@Test
	void marketListingTest() {
		MarketListing ml = new MarketListing();
		Set<Transaction> trans = new HashSet<Transaction>();
		
		ml.setCoverImage("test.png");
		ml.setDeleted(false);
		ml.setId(0);
		ml.setQtyAvailable(20);
		ml.setTransactions(trans);
		
		assertEquals(ml.getCoverImage(), "test.png");
		assertEquals(ml.isDeleted(), false);
		assertEquals(ml.getId(), 0);
		assertEquals(ml.getQtyAvailable(), 20);
		assertEquals(ml.getTransactions(), trans);
	}
	
	@Test
	void shippingTest() {
		Shipping shipping = new Shipping();
		Transaction trans = new Transaction();
		LocalDate LA = LocalDate.now();
		Date date = Date.valueOf(LA);
		ShippingAddress SA = new ShippingAddress();
		
		shipping.setAddress(SA);
		shipping.setCarrier("Jeff");
		shipping.setId(0);
		shipping.setTransaction(trans);
		
		assertEquals(shipping.getId(), 0);
		assertEquals(shipping.getCarrier(), "Jeff");
		assertEquals(shipping.getAddress(), SA);
		assertEquals(shipping.getTransaction(), trans);
	}
	
	@Test
	void transaction() {
		Transaction transaction = new Transaction();
		Shipping shipping = new Shipping();
		LocalDate LA = LocalDate.now();
		Date date = Date.valueOf(LA);
		User user = new User();
		BigDecimal BD = new BigDecimal(22);
		MarketListing ml = new MarketListing();
		PaymentDetails pd = new PaymentDetails();
		
		transaction.setBuyer(user);
		transaction.setId(0);
		transaction.setMarketListing(ml);
		transaction.setPaymentDetails(pd);
		transaction.setPurchaseDate(date);
		transaction.setQtyBought(20);
		transaction.setSeller(user);
		transaction.setSellerProfit(BD);
		transaction.setShippingEntry(shipping);
		transaction.setTotalPriceAfterTaxes(BD);
		transaction.setTotalPriceBeforeTaxes(BD);
		
		assertEquals(transaction.getId(), 0);
		assertEquals(transaction.getBuyer(), user);
		assertEquals(transaction.getMarketListing(), ml);
		assertEquals(transaction.getPaymentDetails(), pd);
		assertEquals(transaction.getPurchaseDate(), date);
		assertEquals(transaction.getQtyBought(), 20);
		assertEquals(transaction.getSeller(), user);
		assertEquals(transaction.getSellerProfit(), BD);
		assertEquals(transaction.getShippingEntry(), shipping);
		assertEquals(transaction.getTotalPriceAfterTaxes(), BD);
		assertEquals(transaction.getTotalPriceBeforeTaxes(), BD);
	}

}
