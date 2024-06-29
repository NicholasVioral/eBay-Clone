package edu.sru.cpsc.webshopping.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.service.BackupService;
import edu.sru.cpsc.webshopping.service.UserService;

/**
 * Controller for scheduling database backups
 * @author Wolfgang
 */

@Controller
public class BackupController {
	@Autowired
	private BackupService backupService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/scheduleBackup")
	public String displayBackupPage(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("user", user);
		return "backup";
	}
	
	@RequestMapping(path = "/createNewBackup", method = RequestMethod.POST)
	public String scheduleBackup(@RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("repeat") String repeat, Model model, Principal principal) {
		System.out.println(date + " " + time + " "  + repeat);
		
		String dateTimeString = date + " " + time;		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date scheduleDate = new Date();
		try {
			scheduleDate = formatter.parse(dateTimeString);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Error parsing date from string.");
		}
		
		backupService.scheduleBackup(scheduleDate, repeat);
		
		model.addAttribute("success", true);
		return "backup";
	}
}
