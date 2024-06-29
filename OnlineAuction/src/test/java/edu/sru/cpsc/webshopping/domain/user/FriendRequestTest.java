package edu.sru.cpsc.webshopping.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {FriendRequestTest.class})
public class FriendRequestTest {

	@Test
	void testConstructorWithTimeSent() {
		User sender = new User();
		User receiver = new User();
		LocalDateTime timeSent = LocalDateTime.now();
		FriendRequest friendRequest = new FriendRequest(1L, receiver, sender, timeSent);
		assertEquals(1L, friendRequest.getId());
		assertEquals(receiver, friendRequest.getReceiver());
		assertEquals(sender, friendRequest.getSender());
		assertEquals(timeSent, friendRequest.getTimeSent());
		assertEquals(0, friendRequest.getStatus());
	}

	@Test
	void testConstructorWithoutTimeSent() {
		User sender = new User();
		User receiver = new User();
		FriendRequest friendRequest = new FriendRequest(1L, receiver, sender);
		assertEquals(1L, friendRequest.getId());
		assertEquals(receiver, friendRequest.getReceiver());
		assertEquals(sender, friendRequest.getSender());
		assertNull(friendRequest.getTimeSent());
		assertEquals(0, friendRequest.getStatus());
	}

	@Test
	void testSetSender() {
		User sender = new User();
		User receiver = new User();
		FriendRequest friendRequest = new FriendRequest(1L, receiver, null);
		assertNull(friendRequest.getSender());
		friendRequest.setSender(sender);
		assertEquals(sender, friendRequest.getSender());
	}

	@Test
	void testSetReceiver() {
		User sender = new User();
		User receiver = new User();
		FriendRequest friendRequest = new FriendRequest(1L, null, sender);
		assertNull(friendRequest.getReceiver());
		friendRequest.setReceiver(receiver);
		assertEquals(receiver, friendRequest.getReceiver());
	}

	@Test
	void testSetStatus() {
		User sender = new User();
		User receiver = new User();
		FriendRequest friendRequest = new FriendRequest(1L, receiver, sender);
		assertEquals(0, friendRequest.getStatus());
		friendRequest.setStatus(true);
		assertEquals(1, friendRequest.getStatus());
		friendRequest.setStatus(false);
		assertEquals(0, friendRequest.getStatus());
	}

	@Test
	void testGetStatus() {
		User sender = new User();
		User receiver = new User();
		FriendRequest friendRequest = new FriendRequest(1L, receiver, sender);
		assertEquals(0, friendRequest.getStatus());
	}

}