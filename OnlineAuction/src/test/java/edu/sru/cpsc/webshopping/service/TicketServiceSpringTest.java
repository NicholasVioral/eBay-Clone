package edu.sru.cpsc.webshopping.service;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;

import edu.sru.cpsc.webshopping.domain.user.ContactUsTicket;
import edu.sru.cpsc.webshopping.repository.ticket.TicketRepository;
import jakarta.mail.internet.MimeMessage;


/*
 * Tests that the overall ticketing service is functioning
 * and that tickets can be found
 */

@SpringBootTest(classes = {TicketServiceSpringTest.class})
public class TicketServiceSpringTest {
	
	@Mock
	private TicketRepository ticketRepository;
	
	@Mock
	private ContactUsTicket ticket;

	@Mock
	private JavaMailSender mailSender;
	
	@InjectMocks @Spy
	private TicketService ticketService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testSendContactTicketRecieptEmail() {		
		ContactUsTicket contactUsTicket = new ContactUsTicket();
		contactUsTicket.setId(1L);
		contactUsTicket.setEmail("test@example.com");
		contactUsTicket.setMessage("Test message");
		
		SimpleMailMessage expectedMessage = new SimpleMailMessage();
		expectedMessage.setTo(contactUsTicket.getEmail());
		expectedMessage.setSubject(contactUsTicket.getId() + ": Your contact service request has been received");
		expectedMessage.setText(
		        "Contact us ticket: " + contactUsTicket.getMessage()
		                + "\n"
		                + "Your ticket has been received and is currently being processed. A staff member will contact you shortly.");
		
		ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage .class);
		doReturn(mailSender).when(ticketService).getJavaMailSender();
		ticketService.sendContactTicketReceiptEmail(contactUsTicket);
		
		verify(mailSender, times(1)).send(emailCaptor.capture());
		
	}

}
