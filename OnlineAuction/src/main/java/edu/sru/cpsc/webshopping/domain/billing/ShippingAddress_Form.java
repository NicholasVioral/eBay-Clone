package edu.sru.cpsc.webshopping.domain.billing;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/** 
 * Used to create a validation layer over the ShippingAddress JPA entity
 */
public class ShippingAddress_Form {
	
	@NotEmpty(message = "Recipient cannot be empty.")
	private String recipient;
	
	@NotEmpty(message = "Street Address cannot be empty.")
	private String streetAddress;
	
	private String extraLocationInfo;
	
	@Size(min = 5, max = 5, message = "The Postal Code must have five numbers")
	private String postalCode;
	
	private StateDetails state;
	
	@NotEmpty(message = "The city cannot be empty.")
	private String city;

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getExtraLocationInfo() {
		return extraLocationInfo;
	}

	public void setExtraLocationInfo(String extraLocationInfo) {
		this.extraLocationInfo = extraLocationInfo;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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
		this.city = city;
	}
}
