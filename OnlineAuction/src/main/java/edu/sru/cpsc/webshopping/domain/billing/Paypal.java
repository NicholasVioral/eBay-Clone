package edu.sru.cpsc.webshopping.domain.billing;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Database entity for storing information on a Paypal login
 */
@Entity
public class Paypal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	private String paypalLogin;
	
	@NonNull
	private String paypalPassword;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
	
	public void transferFields(Paypal other) {
		this.paypalLogin = other.paypalLogin;
		this.paypalPassword = other.paypalPassword;
	}
	
	public void buildFromForm(Paypal_Form other) {
		this.paypalLogin = other.getPaypalLogin();
		this.paypalPassword = other.getPaypalPassword();
	}
	
}
