package edu.sru.cpsc.webshopping.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sru.cpsc.webshopping.domain.misc.FriendStatus;
import edu.sru.cpsc.webshopping.domain.misc.Friendship;
import edu.sru.cpsc.webshopping.domain.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.misc.FriendSocialRequestRepository;
import edu.sru.cpsc.webshopping.repository.misc.FriendshipRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

/**
 * Service for handling friends
 */
@Service
public class FriendshipService {
	
    @Autowired
    private FriendshipRepository friendshipRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FriendSocialRequestRepository friendSocialRequestRepository;
    
    /**
     * Returns a list of all friends of given user
     * @param user
     * @return
     */
    public List<User> getAllFriendsForUser(User user) {
        List<Friendship> friendships1 = friendshipRepository.findAllByUser1(user);
        List<Friendship> friendships2 = friendshipRepository.findAllByUser2(user);

        List<User> friends = new ArrayList<>();
        
        for(Friendship friendship : friendships1) {
            friends.add(friendship.getUser2());
        }
        
        for(Friendship friendship : friendships2) {
            friends.add(friendship.getUser1());
        }

        return friends;
    }
    
    /**
     * Adds given friendship to database
     * @param friendship
     */
    public void addFriend(Friendship friendship) {
        friendshipRepository.save(friendship);
    }
    
    /**
     * Returns the friendship between two users
     * @param user1
     * @param userId2
     * @return
     */
    public Friendship getFriendshipBetweenUsers(User user1, Long userId2) {
        User user2 = userRepository.findById(userId2).orElse(null);
        if(user2 == null) {   
            return null;
        }
        
        // Attempt to find a friendship in one direction
        List<Friendship> friendships = friendshipRepository.findByUser1AndUser2(user1, user2);
        
        // If no friendship is found in the first direction, attempt the other direction
        if(friendships.isEmpty()) {
            friendships = friendshipRepository.findByUser2AndUser1(user1, user2);
        }

        // Return the first friendship found, or null if none exists
        return friendships.isEmpty() ? null : friendships.get(0);
    }
    
    /**
     * Removes given friendship from database
     * @param friendship
     */
    public void removeFriendship(Friendship friendship) {
        friendshipRepository.delete(friendship);
    }
    
    /**
     * Sends friend request from one user to another
     * @param sender
     * @param receiver
     * @return
     */
    public boolean sendFriendRequest(User sender, User receiver) {
        if(sender.equals(receiver)) {
            System.out.println("Cannot send a friend request to oneself.");
            return false;
        }
        
        // Check if they are already friends
        if(!friendshipRepository.findByUser1AndUser2(sender, receiver).isEmpty() || 
           !friendshipRepository.findByUser2AndUser1(sender, receiver).isEmpty()) {
            System.out.println("Users are already friends.");
            return false;
        }

        // Check if there's already a pending friend request from sender to receiver
            System.out.println("A pending friend request from sender to receiver already exists.");
            if(!friendSocialRequestRepository.findBySenderAndReceiverAndStatus(sender, receiver, FriendStatus.PENDING).isEmpty()) {
            return false;
        }
        
        SocialFriendRequest request = new SocialFriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(FriendStatus.PENDING);
        request.setTimestamp(LocalDateTime.now()); // Set the current timestamp here
        friendSocialRequestRepository.save(request);
        return true;
    }
    
    /**
     * Gets all pending requests for the given user
     * @param user
     * @return
     */
    public List<SocialFriendRequest> getPendingRequestsForUser(User user) {
        return friendSocialRequestRepository.findByReceiverAndStatus(user, FriendStatus.PENDING);
    }
    
    /**
     * Changes friend request status to accepted
     * @param request
     */
    public void acceptRequest(SocialFriendRequest request) {
        request.setStatus(FriendStatus.ACCEPTED);
        friendSocialRequestRepository.save(request);
        
    }
    
    /**
     * Changes friend request status to declined
     * @param request
     */
    public void declineRequest(SocialFriendRequest request) {
        request.setStatus(FriendStatus.DECLINED);
        friendSocialRequestRepository.delete(request);
    }
    
    /**
     * Get all pending friend requests for specific user
     * @param user
     * @return
     */
    public List<SocialFriendRequest> getOutgoingPendingRequests(User user) {
        return friendSocialRequestRepository.findBySenderAndStatus(user, FriendStatus.PENDING);
    }
    
    /**
     * Cancels given friend request by ID
     * @param requestId
     */
    @Transactional
    public void cancelFriendRequest(Long requestId) {
        friendSocialRequestRepository.deleteById(requestId);
    }
}
