package edu.sru.cpsc.webshopping.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmailController {

  @Value("${server.port}")
  private String port;

  @Autowired
  private ServletContext servletContext;

  @Bean
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

  public void sendTwoFactorAuthEmail(User user, String code) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject("<no-reply> Two Factor Authentication");
    message.setText(
        "Your two factor authentication code is: "
            + code
            + "\n"
            + "If you have received this email in error please contact us immediately.");
    this.getJavaMailSender().send(message);
  }

  public void verificationEmail(User recipient, String code) {
    User user = recipient;
    user.setEmailVerification(code);
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject("<no-reply> Welcome!");

    String verificationLink = String.format("%s/emailverification", servletContext.getContextPath());
    try {
      message.setText(
          String.format(
              "Hello %s please use the following link http://%s:%s%s to verify your account with code:\n%s",
              user.getUsername(), InetAddress.getLocalHost().getHostAddress(), port, verificationLink, code));
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
    this.getJavaMailSender().send(message);
  }

  public void messageEmail(User recipient, User sender, Message theMessage) {
    User user = recipient;
    User messageSender = sender;
    Message msg = theMessage;

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject(
        "<no-reply> " + messageSender.getUsername() + " sent you a message on World of Widgets");
    message.setText(
        "From: "
            + msg.getOwner().getUsername()
            + "\n"
            + "Subject: "
            + msg.getSubject()
            + "\n"
            + msg.getContent());
    this.getJavaMailSender().send(message);
  }

  public void usernameRecovery(User user) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject("<no-reply> Username Recovery");
    message.setText(
        "Your username is: "
            + user.getUsername()
            + "\n"
            + "If you have received this email in error please contact us immediately.");
    this.getJavaMailSender().send(message);
  }
  
  @PostMapping("/helpDesk/submitTicket/{id}")
  public void sendHelpDeskTicket(@PathVariable long id) {
	  //TODO: create ticket class that generates ticket value/id and takes in data from contact form 
	  //direct info to email address contact@offerly.fake
	  
  }

  public void applicationAccepted(
      String applicantEmail,
      String firstName,
      String lastName,
      String phoneNumber,
      String roleAppliedFor) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(applicantEmail);
    message.setSubject("Your World of Widgets application is moving on!");
    message.setText(
        "Dear "
            + firstName
            + " "
            + lastName
            + "\n"
            + "\n"
            + "We are excited to be letting you know that we think you may be a fit at our company!"
            + "\n"
            + "\n"
            + "You applied for: "
            + roleAppliedFor
            + "\n"
            + "\n"
            + "The next step in the hiring process will be for one of our hiring managers to call you at the times specified on your application to set up an interview."
            + "\n"
            + "\n"
            + "Your phone number on file with us is: "
            + phoneNumber
            + "\n"
            + "\n"
            + "We are really looking forward to moving to this next step with you!"
            + "\n"
            + "\n"
            + "If there are any questions please feel free to call us at 555-555-5555");

    this.getJavaMailSender().send(message);
  }

  public void applicationRejected(String applicantEmail, String firstName, String lastName) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(applicantEmail);
    message.setSubject("World of Widgets application: we regret is not moving forward");
    message.setText(
        "Dear "
            + firstName
            + " "
            + lastName
            + "\n"
            + "\n"
            + "Thank you for applying with us unfortunently we are not moving forward at this time"
            + "\n"
            + "\n"
            + "We will keep your application on file as there are always opportunities in the future."
            + "\n"
            + "\n"
            + "If there are any questions please feel free to call us at 555-555-5555");

    this.getJavaMailSender().send(message);
  }

  @Async
  public void updateTicketStatus(final User recipient, final Ticket ticket, final String activity) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(recipient.getEmail());
    if (activity.equalsIgnoreCase("reopen")) {
      message.setSubject("<no-reply> Ticket with id " + ticket.getId() + " has been reopened");
      message.setText(
          "From: Admin\nSubject: Reg reopening of ticket\nTicket has been reopened by "
              + recipient.getUsername()
              + " and the current ticket status is - "
              + ticket.getState().name());
    } else if (activity.equalsIgnoreCase("create")) {
      message.setSubject("<no-reply> Ticket with id " + ticket.getId() + " has been created");
      message.setText(
          "From: Admin\nSubject: Reg creation of ticket\nTicket has been created by "
              + recipient.getUsername()
              + " and the current ticket status is - "
              + ticket.getState().name());
    } else if (activity.equalsIgnoreCase("reply")) {
      message.setSubject("<no-reply> Ticket with id " + ticket.getId() + " has been updated");
      message.setText(
          "From: Admin\nSubject: Reg update of ticket\nThere is a new message on the ticket by "
              + recipient.getUsername()
              + " and the current ticket status is - "
              + ticket.getState().name());
    } else if (activity.equalsIgnoreCase("assign")) {
      message.setSubject("<no-reply> Ticket with id " + ticket.getId() + " has been assigned");
      message.setText(
          "From: Admin\nSubject: Reg assigning the ticket\nTicket has been assigned to "
              + ticket.getAssignedTo().getUsername()
              + " and the current ticket status is - "
              + ticket.getState().name());
    } else if (activity.equalsIgnoreCase("resolve")) {
      message.setSubject("<no-reply> Ticket with id " + ticket.getId() + " has been resolved");
      message.setText(
          "From: Admin\nSubject: Reg resolving the ticket\nTicket has been resolved "
              + ticket.getAssignedTo().getUsername()
              + " and the current ticket status is - "
              + ticket.getState().name());
    }

    this.getJavaMailSender().send(message);

    if (!recipient.getUsername().equals(ticket.getCreatedBy().getUsername())) {
      message.setTo(ticket.getCreatedBy().getEmail());
      this.getJavaMailSender().send(message);
    }

    if (ticket.getAssignedTo() != null) {
      if (!recipient.getUsername().equals(ticket.getAssignedTo().getUsername())) {
        message.setTo(ticket.getAssignedTo().getUsername());
      }
      this.getJavaMailSender().send(message);
    }
  }
}
