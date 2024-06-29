package edu.sru.cpsc.webshopping.domain.billing;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;


/**
 * Holds the payment information of a buyer
 * Money spent on purchases should be withdrawn from this account
 */
@Entity
public class PaymentDetails {
	
	@SequenceGenerator(
			name = "sequence", 
			sequenceName = "idSequence",
			initialValue = 10,
			allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequence")
	private long id;
	
	// Card processor, i.e. Discover Card, Visa
	@NonNull
	private String cardType;
	
	@NonNull
	private String cardholderName;
	
	@NonNull
	private String cardNumber;
	
	@NonNull
	private String last4Digits;
	
	@NonNull
	private String expirationDate;
	
	@NonNull
	private String securityCode;

	@NonNull
    @ManyToOne
    @JoinColumn(name = "billing_address_id")
	private ShippingAddress billingAddress;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
	
	public String getLast4Digits() {
		return last4Digits;
	}

	public void setLast4Digits(String last4Digits) {
		this.last4Digits = last4Digits;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public ShippingAddress getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(ShippingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	// Sets the non-id fields of the calling PaymentDetails to match that of the passed PaymentDetails
	public void transferFields(PaymentDetails other) {
		this.cardType = other.cardType;
		this.cardholderName = other.cardholderName;
		this.cardNumber = other.cardNumber;
		this.last4Digits = other.last4Digits;
		this.expirationDate = other.expirationDate;
		this.securityCode = other.securityCode;
		this.billingAddress = other.billingAddress;
	}
	
	public void buildFromForm(PaymentDetails_Form form, ShippingAddress billingAddress) {
		this.cardType = form.getCardType();
		this.cardholderName = form.getCardholderName();
		this.cardNumber = form.getCardNumber();
		if(this.cardNumber.length() >= 8 && this.cardNumber.length() <= 19)
			this.last4Digits = form.getCardNumber().substring(this.cardNumber.length() - 4);
		this.expirationDate = form.getExpirationDate();
		this.securityCode = form.getSecurityCode();
		this.billingAddress = billingAddress;
	}

}
