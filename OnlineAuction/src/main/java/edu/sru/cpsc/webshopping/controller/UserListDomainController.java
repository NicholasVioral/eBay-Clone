package edu.sru.cpsc.webshopping.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.user.UserList;
import edu.sru.cpsc.webshopping.repository.user.UserListRepository;
import edu.sru.cpsc.webshopping.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RestController
public class UserListDomainController {
	
	private UserListRepository userListRepository;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	UserService userService;
	@Autowired
	StatisticsDomainController statControl;
	
	UserListDomainController(UserListRepository userListRepository) {
		
		this.userListRepository = userListRepository;
		
	}

	@Transactional
	@PostMapping("/add-user-friend") 
	public void addFriend(@Validated UserList friendList, User friend) {
		User owner = entityManager.find(User.class, friendList.getOwner().getId());
		
		friendList.setOwner(owner);
		friendList.setFriend(friend);
		userListRepository.save(friendList);
	    
		return;
	}
	
	@Transactional
	@PostMapping("/add-user-block") 
	public void addBlock(@Validated UserList blockList, User block) {
		
		User owner = entityManager.find(User.class, blockList.getOwner().getId());
		
		blockList.setOwner(owner);
		blockList.setBlock(block);
		userListRepository.save(blockList);
		return;
		
	}

	@Transactional
	@PostMapping("/get-users-friends") 
	public List<UserList> getFriends(User user) {
		UserList[] myUserList = userListRepository.findByOwner(user);
		
		List<UserList> myFriendList = new ArrayList<>();
		for(int i = 0 ; i < myUserList.length; i++) {
			if(myUserList[i].getFriend() != null) {
				myFriendList.add(myUserList[i]);
			}
		}
		
		return myFriendList;
		
	}
	
	@Transactional
	@PostMapping("/remove-friend") 
	public void removeFriend(User user, User friend) {
		UserList[] myUserList = userListRepository.findByOwner(user);
		
		System.out.println(friend.getUsername());
		for(int i = 0 ; i < myUserList.length; i++) {
			
			if(myUserList[i].getFriend() != null) {
				if(myUserList[i].getFriend().getUsername().equals(friend.getUsername())) {
					System.out.println(myUserList[i].getFriend().getUsername());
					userListRepository.delete(myUserList[i]);
				}
			}
		}
		
	}
	
	@Transactional
	@PostMapping("/remove-block") 
	public void removeBlock(User user,User friend) {
		UserList[] myUserList = userListRepository.findByOwner(user);
		
		
		for(int i = 0 ; i < myUserList.length; i++) {
			
			if(myUserList[i].getBlock() != null) {
			if(myUserList[i].getBlock().getUsername().equals(friend.getUsername())) {
				
				userListRepository.delete(myUserList[i]);
			}}
		}
		
	}
	@Transactional
	@PostMapping("/get-users-blocked") 
	public List<UserList> getBlocked(User user) {
		UserList[] myUserList = userListRepository.findByOwner(user);
		
		List<UserList> myBlockList = new ArrayList<>();
		for(int i = 0 ; i < myUserList.length; i++) {
			if(myUserList[i].getBlock() != null) {
				myBlockList.add(myUserList[i]);
			}
		}
	
		
		return myBlockList;
		
	}
	@Transactional
	@PostMapping("/get-users-blocked-check") 
	public Boolean getBlockedCheck(User user,User block) {
		UserList[] myUserList = userListRepository.findByOwner(block);
		
	
		for(int i = 0 ; i < myUserList.length; i++) {
			if(myUserList[i].getBlock() != null) {
				if(myUserList[i].getBlock().getUsername().equals(user.getUsername())) {
					System.out.println("returning true");
				return true;
				}
			}
		}
	
		
		return false;
		
	}
	
}
