package edu.sru.cpsc.webshopping.controller.billing;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.service.UserService;

/**
 * Controller for determining a User's seller rating
 */
@RestController
public class SellerRatingController {
	@Autowired
	private UserService userService;

	// Late if hasn't shipped within a business week
	public long LATE_SHIPPING_NUMBER_DAYS = 5;
	
	SellerRatingController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/rate/{userId}")
    public ResponseEntity<String> rateUser(@PathVariable Long userId, @RequestParam float rating, Principal principal) {

		// rate user
		userService.rateUser(userId, rating);
		return ResponseEntity.ok("Seller rated " + rating);
    }
}
