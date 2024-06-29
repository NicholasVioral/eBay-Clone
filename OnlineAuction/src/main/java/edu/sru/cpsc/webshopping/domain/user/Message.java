package edu.sru.cpsc.webshopping.domain.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.sru.cpsc.webshopping.util.enums.MessageType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne private User owner;
  @ManyToOne private User receiver;
  private String content;
  private String sender;
  private String msgDate;
  private String subject;
  private String receiverName;
  // These are for when a message is sent to spam
  private String spamReporter;
  private String offender;
  private String reviewerName;

  @Enumerated(EnumType.STRING)
  private MessageType messageType;

  private String userFeedback;
  private LocalDateTime dateSentToTrashOwner;
  private LocalDateTime expireDateOwner;
  private LocalDateTime dateSentToTrashReceiver;
  private LocalDateTime expireDateReceiver;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinTable(
      name = "ticket_message",
      joinColumns = @JoinColumn(name = "message_id"),
      inverseJoinColumns = @JoinColumn(name = "ticket_id"))
  private Ticket ticket;

  public void setMsgDate() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd HH:mm");
    LocalDateTime now = LocalDateTime.now();
    this.msgDate = dtf.format(now);
  }

  public void setDateSentToTrashOwner() {
    this.dateSentToTrashOwner = LocalDateTime.now();
  }

  public void resetDateSentToTrashOwner() {
    this.dateSentToTrashOwner = null;
  }

  public void setExpireDateOwner() {
    this.expireDateOwner = this.getDateSentToTrashOwner().plusDays(30);
  }

  public void resetExpireDateOwner() {
    this.expireDateOwner = null;
    ;
  }

  public void setDateSentToTrashReceiver() {
    this.dateSentToTrashReceiver = LocalDateTime.now();
  }

  public void resetDateSentToTrashReceiver() {
    this.dateSentToTrashReceiver = null;
  }

  public void setExpireDateReceiver() {
    this.expireDateReceiver = this.getDateSentToTrashReceiver().plusDays(30);
  }

  public void resetExpireDateReceiver() {
    this.expireDateReceiver = null;
  }

  @Override
  public String toString() {
    return "Message{" +
        "id=" + id +
        ", content='" + content + '\'' +
        ", sender='" + sender + '\'' +
        ", msgDate='" + msgDate + '\'' +
        ", subject='" + subject + '\'' +
        ", receiverName='" + receiverName + '\'' +
        ", spamReporter='" + spamReporter + '\'' +
        ", offender='" + offender + '\'' +
        ", reviewerName='" + reviewerName + '\'' +
        ", messageType=" + messageType +
        ", userFeedback='" + userFeedback + '\'' +
        ", dateSentToTrashOwner=" + dateSentToTrashOwner +
        ", expireDateOwner=" + expireDateOwner +
        ", dateSentToTrashReceiver=" + dateSentToTrashReceiver +
        ", expireDateReceiver=" + expireDateReceiver +
        '}';
  }
}
