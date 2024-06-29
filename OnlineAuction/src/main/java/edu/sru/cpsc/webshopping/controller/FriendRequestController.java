package edu.sru.cpsc.webshopping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.repository.misc.FriendSocialRequestRepository;

@RestController
public class FriendRequestController{
	private FriendSocialRequestRepository requestRepo;
	
	FriendRequestController(FriendSocialRequestRepository requestRepo)
	{
		this.requestRepo = requestRepo;
	}
	
	@RequestMapping("/get-all-friend-requests")
	public Iterable<SocialFriendRequest> getAllFriendRequests()
	{
		return requestRepo.findAll();
	}
}


