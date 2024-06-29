package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.sru.cpsc.webshopping.domain.misc.FriendStatus;
import edu.sru.cpsc.webshopping.domain.misc.Friendship;
import edu.sru.cpsc.webshopping.domain.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.misc.FriendSocialRequestRepository;
import edu.sru.cpsc.webshopping.repository.misc.FriendshipRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

public class FriendshipServiceTest {

	@Mock
	private FriendshipRepository friendshipRepository;

	@Mock
	private User user1;

	@Mock
	private User user2;

	@Mock
	private UserRepository userRepository;

	@Mock
	private FriendSocialRequestRepository friendSocialRequestRepository;

	@InjectMocks
	private FriendshipService friendshipService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllFriendsForUser() {
		Friendship friendship = new Friendship();
		friendship.setUser1(user1);
		friendship.setUser2(user2);
		when(friendshipRepository.findAllByUser1(user1)).thenReturn(List.of(friendship));
		when(friendshipRepository.findAllByUser2(user1)).thenReturn(List.of(friendship));

		List<User> friends = friendshipService.getAllFriendsForUser(user1);

		assertTrue(friends.contains(user2));
	}

	@Test
	void testGetFriendshipBetweenUsers() {
		Friendship friendship = new Friendship();
		friendship.setUser1(user1);
		user2.setId(1);
		friendship.setUser2(user2);

		when(friendshipRepository.findByUser1AndUser2(user1, user2)).thenReturn(List.of(friendship));
		when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user2));

		Friendship testingFriendship = friendshipService.getFriendshipBetweenUsers(user1, Long.valueOf(1));

		assertSame(friendship, testingFriendship);
	}

	@Test
	void testSendFriendRequest() {		
		//Testing check for same user
		assertFalse(friendshipService.sendFriendRequest(user1, user1));
		
		//Testing if users are already friends
		Friendship friendship = new Friendship();
		friendship.setUser1(user1);
		friendship.setUser2(user2);
		when(friendshipRepository.findByUser1AndUser2(user1, user2)).thenReturn(List.of(friendship));
		when(friendshipRepository.findByUser2AndUser1(user1, user2)).thenReturn(List.of(friendship));
		assertFalse(friendshipService.sendFriendRequest(user1, user2));		
		
		//Testing if a friend request has already been issued
		reset(friendshipRepository);
		when(friendSocialRequestRepository.findBySenderAndReceiverAndStatus(user1, user2, FriendStatus.PENDING))
				.thenReturn(List.of(new SocialFriendRequest()));
		assertFalse(friendshipService.sendFriendRequest(user1, user2));
		
		//Check if users can send friend requests
		reset(friendSocialRequestRepository);
		assertTrue(friendshipService.sendFriendRequest(user1, user2));
	}

}
