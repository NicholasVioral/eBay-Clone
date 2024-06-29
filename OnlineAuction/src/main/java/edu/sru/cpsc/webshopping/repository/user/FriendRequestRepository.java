package edu.sru.cpsc.webshopping.repository.user;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.misc.SocialFriendRequest;

public interface FriendRequestRepository extends CrudRepository<SocialFriendRequest, Long> {
}
