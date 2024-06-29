package edu.sru.cpsc.webshopping.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.sru.cpsc.webshopping.domain.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	User findByEmail(String email);
	
	// custom method to get the number of users that have a specific market listing added to their watchlist
	@Query("SELECT COUNT(DISTINCT u) FROM User u JOIN u.wishlistedWidgets w WHERE w.id = :marketListingId")
    Long countUsersWithMarketListingInWatchlist(@Param("marketListingId") Long marketListingId);
	
	List<User> findByUsernameStartingWith(String query);
	List<User> findByEmailContaining(String query);
	
}
	