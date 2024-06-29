package edu.sru.cpsc.webshopping.domain.billing;

import javax.validation.constraints.NotEmpty;

/**
 * Class for validating Paypal purchase input
 */
public class Paypal_Form {
	
	@NotEmpty(message = "Paypal login cannot be empty.")
	private String paypalLogin;
	
	@NotEmpty(message = "Paypal password cannot be empty.")
	private String paypalPassword;

	public String getPaypalLogin() {
		return paypalLogin;
	}

	public void setPaypalLogin(String paypalLogin) {
		this.paypalLogin = paypalLogin;
	}

	public String getPaypalPassword() {
		return paypalPassword;
	}

	public void setPaypalPassword(String paypalPassword) {
		this.paypalPassword = paypalPassword;
	}
	
}
