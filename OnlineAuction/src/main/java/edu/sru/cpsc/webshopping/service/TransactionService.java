package edu.sru.cpsc.webshopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.billing.RefundTicket;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.repository.market.TransactionRepository;

/**
 * Service that handles transactions
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction getTransactionByRefundTicket(RefundTicket refundTicket) {
        return refundTicket.getTransaction();
    }

    /**
     * Saves given transaction to the database
     * @param transaction
     */
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
    
    /**
     * Returns transaction by given market listing ID
     * @param marketListingId
     * @return
     */
    public Transaction getTransactionByMarketListingId(Long marketListingId) {
        return transactionRepository.findByMarketListingId(marketListingId).orElse(null);
    }
    
    public MarketListing getMarketListingForRefundTicket(RefundTicket refundTicket) {
        // Assuming refundTicket has a linked Transaction
        Transaction transaction = refundTicket.getTransaction();
        if (transaction != null) {
            // Return the MarketListing associated with the Transaction
            return transaction.getMarketListing();
        }
        return null; // Or handle this case as per your business logic
    }
}