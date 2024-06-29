package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.Date;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;

@SpringBootTest(classes = {TransactionTest.class})
public class TransactionTest {
	
	@Test
	public void testTransaction() {
		// Create a new transaction
		Transaction transaction = new Transaction();
		
		// Set the transaction properties
		transaction.setQtyBought(2);
		transaction.setTotalPriceBeforeTaxes(new BigDecimal(10.00));
		transaction.setTotalPriceAfterTaxes(new BigDecimal(11.50));
		transaction.setSellerProfit(new BigDecimal(8.50));
		transaction.setPurchaseDate(new Date(0));
		
		User seller = new User();
		seller.setUsername("seller");
		transaction.setSeller(seller);
		
		User buyer = new User();
		buyer.setUsername("buyer");
		transaction.setBuyer(buyer);

		Widget widget = new Widget();
		widget.setName("Test Widget");
		
		MarketListing marketListing = new MarketListing();
		marketListing.setWidgetSold(widget);
		transaction.setMarketListing(marketListing);
		
		Shipping shippingEntry = new Shipping();
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setStreetAddress("123 Main St.");
		shippingEntry.setAddress(shippingAddress);
		transaction.setShippingEntry(shippingEntry);
		
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setCardNumber("1234-5678-9012-3456");
		transaction.setPaymentDetails(paymentDetails);
		
		DirectDepositDetails depositDetails = new DirectDepositDetails();
		depositDetails.setAccountNumber("1234567890");
		transaction.setDepositDetails(depositDetails);
		
		// Verify the transaction properties
		assertEquals(2, transaction.getQtyBought());
		assertEquals(new BigDecimal(10.00), transaction.getTotalPriceBeforeTaxes());
		assertEquals(new BigDecimal(11.50), transaction.getTotalPriceAfterTaxes());
		assertEquals(new BigDecimal(8.50), transaction.getSellerProfit());
		assertNotNull(transaction.getPurchaseDate());
		assertEquals("seller", transaction.getSeller().getUsername());
		assertEquals("buyer", transaction.getBuyer().getUsername());
		assertEquals("Test Widget", transaction.getMarketListing().getWidgetSold().getName());
		assertEquals("123 Main St.", transaction.getShippingEntry().getAddress().getStreetAddress());
		assertEquals("1234-5678-9012-3456", transaction.getPaymentDetails().getCardNumber());
		assertEquals("1234567890", transaction.getDepositDetails().getAccountNumber());
	}
}