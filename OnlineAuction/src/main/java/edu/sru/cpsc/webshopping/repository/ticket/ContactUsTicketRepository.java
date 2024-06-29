package edu.sru.cpsc.webshopping.repository.ticket;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sru.cpsc.webshopping.domain.user.ContactUsTicket;

@Repository
public interface ContactUsTicketRepository extends CrudRepository<ContactUsTicket, Long> {
	Iterable<ContactUsTicket> findByProcessedFalse();
}
