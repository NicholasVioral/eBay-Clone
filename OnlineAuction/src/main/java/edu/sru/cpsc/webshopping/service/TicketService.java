package edu.sru.cpsc.webshopping.service;

import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import edu.sru.cpsc.webshopping.domain.user.ContactUsTicket;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.repository.ticket.ContactUsTicketRepository;
import edu.sru.cpsc.webshopping.repository.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

	
	@Autowired
	private final TicketRepository ticketRepository;
	
	@Autowired
	private ContactUsTicketRepository contactUsTicketRepository;

	public Iterable<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

  public Ticket getTicketById(Long id) {
    return ticketRepository.findById(id).get();
  }

  public Optional<Ticket> findById(Long id) {
    return ticketRepository.findById(id);
  }

  public void save(Ticket ticket) {
    ticketRepository.save(ticket);
  }
  
  public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);

	    mailSender.setUsername("worldofwidgetsinc@gmail.com");
	    mailSender.setPassword("zxfeppgfuibrisay");

	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

	    return mailSender;
	  }
  
  //sends confirmation email to user
  public void sendContactTicketReceiptEmail(ContactUsTicket ticket) {
	  JavaMailSender emailConfirmation = getJavaMailSender();
	  SimpleMailMessage message = new SimpleMailMessage();
	  message.setTo(ticket.getEmail());
	  message.setSubject(ticket.getId() + ": Your contact service request has been received");
	  message.setText(
		        "Contact us ticket: " + ticket.getMessage()
		            + "\n"
		            + "Your ticket has been received and is currently being processed. A staff member will contact you shortly.");
	  
	  emailConfirmation.send(message);
  }
}
