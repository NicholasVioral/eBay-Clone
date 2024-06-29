package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.cpsc.webshopping.domain.user.ContactUsTicket;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.ticket.TicketRepository;
import edu.sru.cpsc.webshopping.service.TicketService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.util.enums.TicketState;

@Controller
public class ContactController {

	@Autowired
	private UserService userService;
	
	@Autowired 
	private TicketService ticketService;
	
	@Autowired
	private TicketRepository ticketRepository;

	@RequestMapping({ "/contactUs" })
	public String viewContactUsPage(Model model, Principal principal) {
		if (principal != null) {
			User user = userService.getUserByUsername(principal.getName());
			model.addAttribute("user", user);
		}

		ContactUsTicket ticketInfo = (ContactUsTicket) model.asMap().get("ticketInfo");

		model.addAttribute("ticketInfo", ticketInfo != null ? ticketInfo : null);
		model.addAttribute("contactUsTicket", new ContactUsTicket());
		model.addAttribute("page", "contactUs");

		return "contactUs";
	}

	@RequestMapping(value = "/contactUsTicket", method = RequestMethod.POST)
	public String submitContactUsTicket(@ModelAttribute("contactUsTicket") ContactUsTicket contactUsTicket, RedirectAttributes attributes, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		
		Ticket ticket = new Ticket();
		ticket.setCreatedBy(user);
		ticket.setCreatedAt(new Date().toString());
		ticket.setState(TicketState.UNASSIGNED);
		ticket.setSubject("Email: " + contactUsTicket.getEmail() + " Name: " + contactUsTicket.getFirstName() + " " + contactUsTicket.getLastName() + " Content: " + contactUsTicket.getMessage());
		
		//contactUsTicketRepository.save(contactUsTicket);
		ticketRepository.save(ticket);
		ticketService.sendContactTicketReceiptEmail(contactUsTicket);
		attributes.addFlashAttribute("ticketInfo", contactUsTicket);
		
		
		return "redirect:/contactUs";
	}
}
