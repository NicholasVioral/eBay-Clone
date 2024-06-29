package edu.sru.cpsc.webshopping.repository.market;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.RefundTicket;
import edu.sru.cpsc.webshopping.domain.market.Shipping;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.User;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	
	Iterable<Transaction> findByBuyer(User user);
	
	Iterable<Transaction> findBySeller(User user);
	
	List<Transaction> findByPaymentDetails(PaymentDetails details);
	List<Transaction> findByShippingEntry(Shipping details);
	Transaction findByRefundTicket(RefundTicket refundTicket);
	Optional<Transaction> findByMarketListingId(Long marketListingId);
}
