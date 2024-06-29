package edu.sru.cpsc.webshopping.service;

import edu.sru.cpsc.webshopping.domain.billing.RefundTicket;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.billing.RefundTicketRepository;
import edu.sru.cpsc.webshopping.util.enums.TicketState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefundService {

    @Autowired
    private RefundTicketRepository refundTicketRepository;

    public boolean processRefundRequest(Transaction transaction, String refundReason) {
        try {
            User user = transaction.getBuyer();
            Double totalPriceAfterTaxes = transaction.getTotalPriceAfterTaxes().doubleValue();
            RefundTicket refundTicket = createRefundTicket(user, totalPriceAfterTaxes, refundReason);
            refundTicketRepository.save(refundTicket);
            return true;
        } catch (Exception e) {
            // Log the exception and handle it appropriately
            return false;
        }
    }
    
    private RefundTicket createRefundTicket(User user, Double totalPriceAfterTaxes, String refundReason) {
        RefundTicket refundTicket = new RefundTicket();
        refundTicket.setCreatedBy(user);
        refundTicket.setTotalPriceAfterTaxes(totalPriceAfterTaxes);
        refundTicket.setSubject(refundReason);
        refundTicket.setState(TicketState.UNASSIGNED);
        refundTicket.setCreatedAt(LocalDateTime.now());

        return refundTicket;
    }
    
    public Iterable<RefundTicket> getAllRefunds() {
        return refundTicketRepository.findAll();
    }
    
    public RefundTicket getRefundTicketById(Long id) {
        return refundTicketRepository.findById(id).orElse(null);
    }

    public void save(RefundTicket refundTicket) {
        refundTicketRepository.save(refundTicket);
    }

}