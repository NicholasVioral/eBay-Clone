package edu.sru.cpsc.webshopping.domain.billing;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/** 
 * A mirror of DirectDepositDetails used for validating form input
 */
public class DirectDepositDetails_Form {

	@Size(min = 1, message = "The Bank Name must be at least 1 character.")
	private String bankName;
	
	@NotEmpty(message = "Street Address cannot be empty.")
	private String streetAddress;
	
	private String extraLocationInfo;
	
	@Size(min = 5, max = 5, message = "The Postal Code must have five numbers")
	private String postalCode;
	
	private StateDetails state;
	
	@NotEmpty(message = "The city cannot be empty.")
	private String city;
	
	@NotEmpty(message = "The Account Holder Name cannot be empty.")
	private String accountholderName;
	
	@Size(min = 9, max = 9, message = "The Routing Number must have 9 numbers.")
	private String routingNumber;
	
	@Size(min = 1, max = 17, message = "The Account Number must be between 1 and 17 numbers.")
	private String accountNumber;

	public String getAccountholderName() {
		return accountholderName;
	}

	public void setAccountholderName(String accountholderName) {
		this.accountholderName = accountholderName;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		if(streetAddress != null) {
			this.streetAddress = streetAddress.trim();
		}
	}

	public String getExtraLocationInfo() {
		return extraLocationInfo;
	}

	public void setExtraLocationInfo(String extraLocationInfo) {
		if(extraLocationInfo != null) {
			this.extraLocationInfo = extraLocationInfo.trim();
		}
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		if(postalCode != null) {
			this.postalCode = postalCode.trim();
		}
	}

	public StateDetails getState() {
		return state;
	}

	public void setState(StateDetails selectedState) {
		this.state = selectedState;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if(city != null) {
			this.city = city.trim();
		}
	}
}
