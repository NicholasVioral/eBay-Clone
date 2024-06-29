package edu.sru.cpsc.webshopping.repository.user;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.user.UserList;

public interface UserListRepository extends CrudRepository<UserList, Long> {
	UserList[] findByOwner(User owner);
	
}
	