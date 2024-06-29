package edu.sru.cpsc.webshopping.repository.ticket;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

  Iterable<Ticket> findAllByCreatedBy(User user);
}
