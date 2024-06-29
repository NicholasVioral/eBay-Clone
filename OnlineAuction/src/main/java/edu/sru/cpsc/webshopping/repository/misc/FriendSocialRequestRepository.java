package edu.sru.cpsc.webshopping.repository.misc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sru.cpsc.webshopping.domain.misc.FriendStatus;
import edu.sru.cpsc.webshopping.domain.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.domain.user.User;

@Repository
public interface FriendSocialRequestRepository extends JpaRepository<SocialFriendRequest, Long> {
	List<SocialFriendRequest> findByReceiverAndStatus(User receiver, FriendStatus status);
	List<SocialFriendRequest> findAllByReceiver(User receiver);
	List<SocialFriendRequest> findBySenderAndReceiverAndStatus(User sender, User receiver, FriendStatus status);
	List<SocialFriendRequest> findBySenderAndStatus(User user, FriendStatus pending);
}