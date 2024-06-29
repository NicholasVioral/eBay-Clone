package edu.sru.cpsc.webshopping.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.util.enums.MessageType;

@SpringBootTest(classes = {MessageTest.class})
public class MessageTest {

    private Message message;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;
    private User user8;
    private User user9;
    private User user10;
    private List<Ticket> tickets;

    @BeforeEach
    void setUp() {
        message = new Message();
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();
        user5 = new User();
        user6 = new User();
        user7 = new User();
        user8 = new User();
        user9 = new User();
        user10 = new User();
        tickets = new ArrayList<>();
    }


    @Test
    void testSetMsgDate() {
        message.setMsgDate();
        assertNotNull(message.getMsgDate());
    }

    @Test
    void testSetDateSentToTrashOwner() {
        message.setDateSentToTrashOwner();
        assertNotNull(message.getDateSentToTrashOwner());
    }

    @Test
    void testResetDateSentToTrashOwner() {
        message.setDateSentToTrashOwner();
        message.resetDateSentToTrashOwner();
        assertNull(message.getDateSentToTrashOwner());
    }

    @Test
    void testSetExpireDateOwner() {
        message.setDateSentToTrashOwner();
        message.setExpireDateOwner();
        assertNotNull(message.getExpireDateOwner());
    }

    @Test
    void testResetExpireDateOwner() {
        message.setDateSentToTrashOwner();
        message.setExpireDateOwner();
        message.resetExpireDateOwner();
        assertNull(message.getExpireDateOwner());
    }

    @Test
    void testSetDateSentToTrashReceiver() {
        message.setDateSentToTrashReceiver();
        assertNotNull(message.getDateSentToTrashReceiver());
    }

    @Test
    void testResetDateSentToTrashReceiver() {
        message.setDateSentToTrashReceiver();
        message.resetDateSentToTrashReceiver();
        assertNull(message.getDateSentToTrashReceiver());
    }

    @Test
    void testSetExpireDateReceiver() {
        message.setDateSentToTrashReceiver();
        message.setExpireDateReceiver();
        assertNotNull(message.getExpireDateReceiver());
    }

    @Test
    void testResetExpireDateReceiver() {
        message.setDateSentToTrashReceiver();
        message.setExpireDateReceiver();
        message.resetExpireDateReceiver();
        assertNull(message.getExpireDateReceiver());
    }

    @Test
    void testToString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        message.setId(1L);
        message.setContent("Test message");
        message.setSender("Sender");
        message.setMsgDate(dtf.format(now));
        message.setSubject("Test subject");
        message.setReceiverName("Receiver");
        message.setSpamReporter("Reporter");
        message.setOffender("Offender");
        message.setReviewerName("Reviewer");
        message.setMessageType(MessageType.SPAM);
        message.setUserFeedback("Feedback");
        message.setDateSentToTrashOwner(now);
        message.setExpireDateOwner(now.plusDays(30));
        message.setDateSentToTrashReceiver(now);
        message.setExpireDateReceiver(now.plusDays(30));
        assertEquals("Message{id=1, content='Test message', sender='Sender', msgDate='" + dtf.format(now) + "', subject='Test subject', receiverName='Receiver', spamReporter='Reporter', offender='Offender', reviewerName='Reviewer', messageType=SPAM, userFeedback='Feedback', dateSentToTrashOwner=" + now + ", expireDateOwner=" + now.plusDays(30) + ", dateSentToTrashReceiver=" + now + ", expireDateReceiver=" + now.plusDays(30) + '}', message.toString());
    }

    @Test
    void testTickets() {
        Ticket ticket = new Ticket();
        message.setTicket(ticket);
        assertEquals(ticket, message.getTicket());
    }

}