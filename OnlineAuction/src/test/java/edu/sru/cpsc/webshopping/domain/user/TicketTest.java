package edu.sru.cpsc.webshopping.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.util.enums.TicketState;

@SpringBootTest(classes = {TicketTest.class})
public class TicketTest {

  private Ticket ticket;
  private User user;
  private Message message;

  @BeforeEach
  public void setUp() {
    user = new User();
    message = new Message();
    ticket = new Ticket();
  }

  @Test
  public void testGetId() {
    assertNull(ticket.getId());
  }

  @Test
  public void testSetId() {
    Long id = 1L;
    ticket.setId(id);
    assertEquals(id, ticket.getId());
  }

  @Test
  public void testGetCreatedBy() {
    assertNull(ticket.getCreatedBy());
  }

  @Test
  public void testSetCreatedBy() {
    ticket.setCreatedBy(user);
    assertEquals(user, ticket.getCreatedBy());
  }

  @Test
  public void testGetSubject() {
    assertNull(ticket.getSubject());
  }

  @Test
  public void testSetSubject() {
    String subject = "Test Subject";
    ticket.setSubject(subject);
    assertEquals(subject, ticket.getSubject());
  }

  @Test
  public void testGetState() {
    assertNull(ticket.getState());
  }

  @Test
  public void testSetState() {
    TicketState state = TicketState.UNASSIGNED;
    ticket.setState(state);
    assertEquals(state, ticket.getState());
  }

  @Test
  public void testGetCreatedAt() {
    assertNull(ticket.getCreatedAt());
  }

  @Test
  public void testSetCreatedAt() {
    String createdAt = "2022-01-01 00:00:00";
    ticket.setCreatedAt(createdAt);
    assertEquals(createdAt, ticket.getCreatedAt());
  }

  @Test
  public void testGetAssignedAt() {
    assertNull(ticket.getAssignedAt());
  }

  @Test
  public void testSetAssignedAt() {
    String assignedAt = "2022-01-01 00:00:00";
    ticket.setAssignedAt(assignedAt);
    assertEquals(assignedAt, ticket.getAssignedAt());
  }

  @Test
  public void testGetResolvedAt() {
    assertNull(ticket.getResolvedAt());
  }

  @Test
  public void testSetResolvedAt() {
    String resolvedAt = "2022-01-01 00:00:00";
    ticket.setResolvedAt(resolvedAt);
    assertEquals(resolvedAt, ticket.getResolvedAt());
  }

  @Test
  public void testGetUpdatedAt() {
    assertNull(ticket.getUpdatedAt());
  }

  @Test
  public void testSetUpdatedAt() {
    String updatedAt = "2022-01-01 00:00:00";
    ticket.setUpdatedAt(updatedAt);
    assertEquals(updatedAt, ticket.getUpdatedAt());
  }

  @Test
  public void testGetAssignedTo() {
    assertNull(ticket.getAssignedTo());
  }

  @Test
  public void testSetAssignedTo() {
    ticket.setAssignedTo(user);
    assertEquals(user, ticket.getAssignedTo());
  }

  @Test
  public void testAddMessage() {
    ticket.addMessage(message);
    List<Message> messages = new ArrayList<>();
    messages.add(message);
    assertEquals(messages, ticket.getMessages());
  }

  @Test
  public void testIsResolved() {
    ticket.setState(TicketState.RESOLVED);
    assertTrue(ticket.isResolved());
    ticket.setState(TicketState.UNANSWERED);
    assertFalse(ticket.isResolved());
  }

  @Test
  public void testToString() {
    assertNotNull(ticket.toString());
  }
}