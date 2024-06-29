package edu.sru.cpsc.webshopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;

@Controller
public class TestPageController {
	UserController userController;
	WidgetController widgetController;
	
	public TestPageController(UserController userController, WidgetController widgetController) {
		this.userController = userController;
		this.widgetController = widgetController;
	}
	
	@RequestMapping({"/userWidgetTestPage"})
	public String userWidgetTestPage(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("users", userController.getAllUsers());
		model.addAttribute("widget", new Widget());
		model.addAttribute("widgets", widgetController.getAllWidgets());
		model.addAttribute("selectedWidget", null);
		return "userWidgetTestPage";
	}
	
	@RequestMapping("/add-user-test")
	public String addUser(@Validated User user, BindingResult result) {
		userController.addUser(user, result);
		return "redirect:/userWidgetTestPage";
	}
	
	@GetMapping("/delete-user-test/{id}") 
	public String deleteUser(@PathVariable("id") long id, Model model) {
		userController.deleteUser(id, model);
		return "redirect:/userWidgetTestPage";
	}
	
	@RequestMapping("/add-widget-test")
	public String addWidget(@Validated Widget widget, BindingResult result) {
		widgetController.addWidget(widget, result);
		return "redirect:/userWidgetTestPage";
	}
	
	@GetMapping("/delete-widget-test/{id}")
	public String deleteWidget(@PathVariable("id") long id) {
		widgetController.deleteWidget(id);
		return "redirect:/userWidgetTestPage";
	}
}
