package edu.sru.cpsc.webshopping.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {UserListTest.class})
public class UserListTest {

	@Test
	void testSetFriendDenied() {
		User owner = new User();
		User friend = new User();
		UserList userList = new UserList();
		userList.setOwner(owner);
		FriendRequest friendRequest = new FriendRequest(1L, owner, friend);
		friendRequest.setStatus(false);
		assertEquals("Friend Request Denied!", userList.setFriend(friend));
	}

	@Test
	void testGetOwner() {
		User owner = new User();
		UserList userList = new UserList();
		userList.setOwner(owner);
		assertEquals(owner, userList.getOwner());
	}

	@Test
	void testSetOwner() {
		User owner = new User();
		UserList userList = new UserList();
		userList.setOwner(owner);
		assertEquals(owner, userList.getOwner());
	}

	@Test
	void testGetBlock() {
		User block = new User();
		UserList userList = new UserList();
		userList.setBlock(block);
		assertEquals(block, userList.getBlock());
	}

	@Test
	void testSetBlock() {
		User block = new User();
		UserList userList = new UserList();
		userList.setBlock(block);
		assertEquals(block, userList.getBlock());
	}

}