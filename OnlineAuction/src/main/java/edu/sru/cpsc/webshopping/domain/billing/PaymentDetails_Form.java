package edu.sru.cpsc.webshopping.domain.billing;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

/**
 * A mirror of PaymentDetails used for form submission
 */
public class PaymentDetails_Form {
	// Card processor, i.e. Discover Card, Visa
	@NotEmpty(message = "Card Type cannot be empty.")
	private String cardType;
	
	@NotEmpty(message = "Cardholder Name cannot be empty.")
	private String cardholderName;
	
	@Size(min = 8, max = 19, message = "Card Number must be 8-19 characters.")
	private String cardNumber;
	
	@NotEmpty(message = "Expiration cannot be empty.")
	private String expirationDate;
	
	@NonNull
	@Size(min = 3, max = 4, message = "Security Code must be between 3 and 4 characters.")
	private String securityCode;

	private long billingAddress;

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public long getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(long billingAddress) {
		this.billingAddress = billingAddress;
	}
}
