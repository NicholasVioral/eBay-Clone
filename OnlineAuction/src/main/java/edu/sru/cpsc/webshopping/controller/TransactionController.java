package edu.sru.cpsc.webshopping.controller;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Shipping;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.misc.Revenue;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.market.ShippingRepository;
import edu.sru.cpsc.webshopping.repository.market.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Class for interacting with the database for Transaction objects
 */
@RestController
public class TransactionController {
	private TransactionRepository repository;
	private ShippingRepository shippingRepository;
	private MarketListingDomainController marketController;
	@PersistenceContext
	private EntityManager entityManager;
	
	public TransactionController(TransactionRepository repository, MarketListingDomainController marketController,
			ShippingRepository shippingRepository) {
		this.repository = repository;
		this.marketController = marketController;
		this.shippingRepository = shippingRepository;
	}
	
	@PostMapping("add-transaction-binding")
	public void addTransaction(@Validated Transaction transaction, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid Transaction provided for adding a new Transaction");
		}
		repository.save(transaction);
	}
	
	/**
	 * Creates a new Transaction, based on the details held in the passed Transaction
	 * Requires that the MarketListing held by transaction already exist in the database
	 * Requires that the ShippingEntry held by transaction be new
	 * @param transaction the transaction to add to the database
	 */
	@Transactional
	@PostMapping("add-transaction")
	public Transaction addTransaction(@Validated Transaction transaction) {
		MarketListing ml = entityManager.find(MarketListing.class, transaction.getMarketListing().getId());
		// Shipping entry and the associated address are created at the same time as the Transaction
		// entityManager.persist(transaction.getShippingEntry());
		transaction.setMarketListing(ml);
		
		return repository.save(transaction);
	}
	
	/**
	 * Gets an Iterable<Transaction> of all of the purchases that a user has made
	 * @param user the user to find the purchases of
	 * @return an Iterable<Transaction> of a User's purchases
	 */
	@Transactional
	@GetMapping("get-users-purchases")
	public Iterable<Transaction> getUserPurchases(@Validated User user) {
		return repository.findByBuyer(user);
	}
	
	@Transactional
	@GetMapping("find-by-payment-details")
	public List<Transaction> findByPaymentDetails(@Validated PaymentDetails details) {
		return repository.findByPaymentDetails(details);
	}
	
	@Transactional
	@GetMapping("find-by-shipping-details")
	public List<Transaction> findByShippingDetails(@Validated ShippingAddress details) {
		Shipping shipping = shippingRepository.findByAddress(details);
		return repository.findByShippingEntry(shipping);
	}
	
	/**
	 * Gets an Iterable<Transaction> of all the transactions sold by the user
	 * @param user the user to find the sold transactions of
	 * @return an Iterable<Transaction> of the Transactions sold by the user
	 */
	@Transactional
	@GetMapping("get-users-sold-items")
	public Iterable<Transaction> getUserSoldItems(@Validated User user) {		
		return repository.findBySeller(user);
	}
	
	/**
	 * Gets a specific Transaction
	 * @param id the id of the Transaction to find
	 * @return the found Transaction
	 * @exception IllegalArgumentException is thrown if the id is not associated with an existing transaction
	 */
	@Transactional
	@RequestMapping("get-transaction/{id}")
	public Transaction getTransaction(@PathVariable("id") long id) {
		Transaction transaction = entityManager.find(Transaction.class, id);
		return transaction;
	}
	
	/**
	 * Cancels a specific Transaction
	 * This deletes the Transaction and its associated Shipping entry
	 * @param trans the Transaction to delete
	 * @return true if deleted, false otherwise
	 */
	@Transactional
	@GetMapping("cancel-transaction")
	public boolean cancelTransaction(@Validated Transaction trans) {
		// Not allowed to cancel a transaction if shipping label created
		if (trans.getShippingEntry().getTrackingNumber() != null) 
			return false;
		else if (getTransaction(trans.getId()) == null)
			return false;
		shippingRepository.delete(trans.getShippingEntry());
		repository.delete(trans);
		marketController.undoPurchase(trans);
		return true;
	}
	
}
