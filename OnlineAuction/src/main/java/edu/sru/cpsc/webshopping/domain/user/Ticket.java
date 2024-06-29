package edu.sru.cpsc.webshopping.domain.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import edu.sru.cpsc.webshopping.util.enums.TicketState;
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
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @Enumerated(EnumType.STRING)
  private TicketState state;
  
  @ManyToOne
  private User createdBy;
  
  @ManyToOne
  private User assignedTo;
  
  private String subject;

  private String createdAt;
  private String assignedAt;
  private String resolvedAt;
  private String updatedAt;

  // Lazy Initialization Exception
  // https://stackoverflow.com/questions/23932684/hibernate-failed-to-lazily-initialize-a-collection
  @OneToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinTable(
      name = "ticket_message",
      joinColumns = @JoinColumn(name = "ticket_id"),
      inverseJoinColumns = @JoinColumn(name = "message_id"))
  @Fetch(FetchMode.SELECT)
  private List<Message> messages = new ArrayList<>();

  public void addMessage(Message message) {
    if (messages == null) {
      messages = new ArrayList<>();
    }
    messages.add(message);
  }

  public boolean isResolved() {
    return TicketState.RESOLVED.equals(state);
  }

  @Override
  public String toString() {
    return "Ticket{"
        + "id="
        + id
        + ", createdBy="
        + createdBy
        + ", subject='"
        + subject
        + '\''
        + ", state="
        + state
        + ", createdAt='"
        + createdAt
        + '\''
        + ", updatedAt='"
        + updatedAt
        + '\''
        + ", assignedTo="
        + assignedTo
        + '}';
  }
  
}
