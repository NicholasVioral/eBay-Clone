package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.misc.Revenue;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.service.UserService;

@Controller
public class RevenueController {
	private final float percent = (float) 0.1;
	@Autowired
	UserService userService;
	
	// displays revenue
	@GetMapping({"/viewRevenue"})
	public String viewRevenue(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		Revenue revenue = null;
		
		try {
			revenue = Revenue.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float amount = revenue.getRevenue();
		
		model.addAttribute("user", user);
		model.addAttribute("revenue", amount);
		model.addAttribute("page", "revenue");
		
		return "revenue";
	}
	
	public float calculateRevenueEarned(Transaction transaction) {
		float revenueEarned = transaction.getTotalPriceBeforeTaxes().floatValue() * percent;
		return revenueEarned;
	}
}
