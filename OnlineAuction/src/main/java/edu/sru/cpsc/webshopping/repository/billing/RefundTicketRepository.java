package edu.sru.cpsc.webshopping.repository.billing;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sru.cpsc.webshopping.domain.billing.RefundTicket;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;

public interface RefundTicketRepository extends JpaRepository<RefundTicket, Long> {
	Iterable<RefundTicket> findAllByCreatedBy(User user);
}
