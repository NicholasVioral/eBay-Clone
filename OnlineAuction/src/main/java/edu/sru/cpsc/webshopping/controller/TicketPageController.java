package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import edu.sru.cpsc.webshopping.domain.billing.RefundTicket;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.repository.market.TransactionRepository;
import edu.sru.cpsc.webshopping.repository.ticket.TicketRepository;
import edu.sru.cpsc.webshopping.service.RefundService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.util.constants.TimeConstants;
import edu.sru.cpsc.webshopping.util.enums.MessageType;
import edu.sru.cpsc.webshopping.util.enums.TicketState;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data
@RequiredArgsConstructor
public class TicketPageController {

  private String page;
  private final TicketRepository ticketRepository;
  private final EmailController emailController;

  @PersistenceContext
	private EntityManager entityManager;

  @Autowired
  private SessionFactory sessionFactory;  

  @Autowired
  private UserService userService;
  
  @Autowired
  private StatisticsDomainController statControl;
 
  @Autowired
  private RefundService refundService;
  
  @Autowired
  WidgetController widgetController;
  
  @Autowired
  MarketListingDomainController marketController;
  
  @Autowired
  private TransactionController transController;
  
  @Autowired
  private TransactionRepository transactionRepository;

  @GetMapping("/tickets")
  public String getTicketsPage(Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Iterable<Ticket> tickets = ticketRepository.findAllByCreatedBy(user);
    model.addAttribute("tickets", tickets);

    setPage("tickets");
    model.addAttribute("page", getPage());
    return "tickets";
  }

  @GetMapping("/tickets/{id}")
  @Transactional
  public String getTicketDetailsPage(@PathVariable("id") Long id, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);
    
    Ticket ticket = ticketRepository.findById(id).get();
    //Hibernate.initialize(ticket.getMessages());
    //get the ticket's messages
    //List<Message> messages = new ArrayList<>(ticket.getMessages());

    model.addAttribute("ticketdetail", ticket);
    //model.addAttribute("messages", messages);
    model.addAttribute("message", new Message());

    setPage("ticketdetails");
    model.addAttribute("page", getPage());
    return "tickets";
  }

  @PostMapping("/reopenTicket/{id}")
  @Transactional
  public String reopenTicket(@PathVariable("id") Long id, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Ticket ticket = ticketRepository.findById(id).get();
    ticket.setState(TicketState.UNANSWERED);
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    ticketRepository.save(ticket);

    emailController.updateTicketStatus(user, ticket, "reopen");

    Iterable<Ticket> tickets = ticketRepository.findAllByCreatedBy(user);
    model.addAttribute("tickets", tickets);

    setPage("tickets");
    model.addAttribute("page", getPage());
    return "redirect:/tickets";
  }

  @GetMapping("/createTickets")
  public String createTicketsPage(Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Ticket ticket = new Ticket();
    ticket.addMessage(new Message());
    model.addAttribute("ticket", ticket);

    Iterable<Ticket> tickets = ticketRepository.findAllByCreatedBy(user);
    model.addAttribute("tickets", tickets);

    setPage("createTickets");
    model.addAttribute("page", getPage());
    return "tickets";
  }
  
  @PostMapping("/createTickets")
  @Transactional
  public String createTickets(Model model, Ticket ticket, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);
    model.addAttribute("ticket", ticket);

    ticket.setCreatedBy(user);
    ticket.setState(TicketState.UNASSIGNED);
    ticket.setCreatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    ticket.setUpdatedAt(ticket.getCreatedAt());
    ticket
        .getMessages()
        .forEach(
            message -> {
              message.setMessageType(MessageType.TICKET);
              message.setSender(user.getUsername());
              message.setMsgDate();
            });
    
    ticketRepository.save(ticket);

    emailController.updateTicketStatus(user, ticket, "create");
    
    // log event
    StatsCategory cat = StatsCategory.TICKETS;
    Statistics stat = new Statistics(cat, 1);
    stat.setDescription(user.getUsername() + " created ticket: " + ticket.getSubject() + " (ID: " + ticket.getId() + ")");
    statControl.addStatistics(stat);

    setPage("tickets");
    return "redirect:/tickets";
  }
  
  @GetMapping("/refund")
  public String showRefundPage(Model model, Model widgetModel,Model listingModel, String tempSearch, Principal principal) {
	String username = principal.getName();
	User user = userService.getUserByUsername(username);
	model.addAttribute("user", user);
	model.addAttribute("page", "refund");
	widgetModel.addAttribute("widgets", widgetController.getAllWidgets());
	listingModel.addAttribute("listings", marketController.getAllListings());
	Iterable<Transaction> purchases = transController.getUserPurchases(user);
	model.addAttribute("purchases", purchases);
	
	return "refund";
  }
  
  @PostMapping("/requestRefund")
  public String requestRefund(Model model, 
                              @RequestParam("purchaseId") Long purchaseId, // This is actually the transaction ID
                              @RequestParam("totalPriceAfterTaxes") Double totalPriceAfterTaxes,
                              @RequestParam("refundReason") String refundReason,
                              Principal principal) {
      User user = userService.getUserByUsername(principal.getName());
      model.addAttribute("user", user);

      // Retrieve the transaction from the database
      Transaction transaction = transactionRepository.findById(purchaseId).orElse(null);

      if (transaction != null) {
          // Create a new RefundTicket
          RefundTicket refundTicket = new RefundTicket();
          refundTicket.setCreatedBy(user);
          refundTicket.setSubject(refundReason);
          refundTicket.setState(TicketState.PENDING);
          refundTicket.setTotalPriceAfterTaxes(totalPriceAfterTaxes);
          refundTicket.setCreatedAt(LocalDateTime.now());
          refundTicket.setTransaction(transaction); // Link the transaction with the refund ticket

          // Save the refund ticket
          refundService.save(refundTicket);

          // Update the transaction's refund status to PENDING
          transaction.setRefundStatus(Transaction.RefundStatus.PENDING);
          transaction.setRefundTicket(refundTicket); // Link the refund ticket with the transaction
          transactionRepository.save(transaction);

          // Additional processing or notifications if needed
      } else {
          // Handle the case where the transaction is not found
          model.addAttribute("error", "Transaction not found");
          return "redirect:/refund";
      }

      return "redirect:/refund";
  }
	
  /**
  @GetMapping("/refund")
  public String showRefundPage(Model model, Model widgetModel,Model listingModel, String tempSearch) {
	  User user = userService.getUserByUsername(principal.getName());
	  model.addAttribute("user", user);
	  model.addAttribute("page", "refund");
	  widgetModel.addAttribute("widgets", widgetController.getAllWidgets());
	  listingModel.addAttribute("listings", marketController.getAllListings());
	  Iterable<Transaction> purchases =
			  transController.getUserPurchases(user);
	  listingModel.addAttribute("purchases", purchases);
	  
      return "refund";
  }
 */ 

  @PostMapping("/replyTicket/{id}")
  @Transactional
  public String getTicketsPage(
      @PathVariable Long id, @ModelAttribute Message message, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);
    
    Ticket ticket = ticketRepository.findById(id).get();
    Message newMessage = new Message();
    newMessage.setContent(message.getContent());
    newMessage.setMsgDate();
    newMessage.setMessageType(MessageType.TICKET);
    newMessage.setSender(user.getUsername());
    newMessage.setTicket(ticket);
  
    if (ticket.getAssignedTo() != null) {
      ticket.setState(TicketState.UNANSWERED);
    } else {
      ticket.setState(TicketState.UNASSIGNED);
    }
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));

    ticket.addMessage(newMessage);

    emailController.updateTicketStatus(user, ticket, "reply");
    entityManager.merge(ticket);
    //ticketRepository.save(ticket);

    model.addAttribute("ticketdetail", ticket);
    model.addAttribute("message", new Message());

    setPage("ticketdetails");
    model.addAttribute("page", getPage());
    return "redirect:/tickets/" + id;
  }
}
