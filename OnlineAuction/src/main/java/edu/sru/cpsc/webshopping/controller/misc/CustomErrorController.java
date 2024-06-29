package edu.sru.cpsc.webshopping.controller.misc;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.NestedServletException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/**
 *  Controller for managing the errorPage
 *  The errorPage is used for presenting a user readable section for errors
 *  that allows the user to recover from the problem
 */
@Controller
public class CustomErrorController implements ErrorController {
	
	CustomErrorController() {
		
	}
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Object errorException = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

		if (errorException instanceof NestedServletException) {
			NestedServletException exception = (NestedServletException) errorException;
			model.addAttribute("errorMessage", exception.getLocalizedMessage());
			model.addAttribute("exceptionFound", true);
		} else if (errorException instanceof Throwable) { // For any other Throwable that is not a NestedServletException
			Throwable throwable = (Throwable) errorException;
			model.addAttribute("errorMessage", throwable.getLocalizedMessage());
			model.addAttribute("exceptionFound", true);
		} else {
			model.addAttribute("errorMessage", "No exception was thrown.");
			model.addAttribute("exceptionFound", false);
		}
		
		model.addAttribute("statusCode", status != null ? status.toString() : "N/A");

		return "error";
	}
}
