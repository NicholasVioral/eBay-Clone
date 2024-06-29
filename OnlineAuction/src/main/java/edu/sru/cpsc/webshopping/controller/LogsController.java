package edu.sru.cpsc.webshopping.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.stats.StatsRepository;
import edu.sru.cpsc.webshopping.service.UserService;

@Controller
public class LogsController {	
	@Autowired
	private UserService userService;
	@Autowired private StatsRepository statsRepository;
	
	// displays logs
	@GetMapping({"/viewLogs"})
	public String viewLogs (Model model, Principal principal) {
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		
		List<Statistics> statList = getLogs();
		List<String> categoryList = getCategories();
	

		model.addAttribute("user", user);
		model.addAttribute("page", "logs");
		model.addAttribute("statList", statList);
		model.addAttribute("categoryList", categoryList);
		
		return "logs";
	}
	
	// get the list of logs from the database
	public List<Statistics> getLogs() {
		Iterable<Statistics> statsIterable = statsRepository.findAll();
        List<Statistics> statsList = new ArrayList<>();
        
        for (Statistics stat : statsIterable) {
            statsList.add(stat);
        }
        
        logsToTextFile(statsIterable);
		
		return statsList;
	}
	
	// get the list of category names
	public List<String> getCategories() {
		List<String> categoryList = new ArrayList<>();
		
		for (Statistics.StatsCategory category : Statistics.StatsCategory.values()) {
            categoryList.add(category.name());
        }

		return categoryList;
	}
	
	private void logsToTextFile(Iterable<Statistics> statIterable) {
		String path = "src/main/resources/Logs/logs.log";
		
		try(PrintWriter writer = new PrintWriter(new FileWriter(path))){
			writer.println("TIME\t\t\t\t\t\tDESCRIPTION\t\t\t\t\t\t\t\t\t\t\tCATEGORY");
			writer.println("----------------------------------------------------------------------------------------------------------------------------------------");
			
			for (Statistics stat : statIterable) {
				String description = padRight(stat.getDescription(), 50);
				writer.printf("%s\t%s\t%s\n", stat.getDate(), description, stat.getCategory().name());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String padRight(String s, int n) {
	    return String.format("%-" + n + "s", s).substring(0, n); // Truncate if longer than specified width
	}

}
