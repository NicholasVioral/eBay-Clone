package edu.sru.cpsc.webshopping.repository.misc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sru.cpsc.webshopping.domain.misc.Friendship;
import edu.sru.cpsc.webshopping.domain.user.User;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
	    List<Friendship> findByUser1OrUser2(User user1, User user2);
	    List<Friendship> findAllByUser1(User user1);
	    List<Friendship> findAllByUser2(User user2);    
	    List<Friendship> findByUser1AndUser2(User user1, User user2);
	    List<Friendship> findByUser2AndUser1(User user1, User user2);
	}

