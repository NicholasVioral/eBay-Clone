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


/**
 * Holds the direct deposit information of a seller
 * Money given to the seller by the website should be sent here
 */
@Entity
public class DirectDepositDetails {
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	private String accountholderName;
	
	@NonNull
	private String routingNumber;
	
	@NonNull
	private String accountNumber;

	@NonNull
	private String bankName;

	@NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_address_id")
	private BankAddress bankAddress;

	@NonNull
	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	public DirectDepositDetails(User user) {
		this.bankAddress = new BankAddress();
		this.user = user;
	}

	public DirectDepositDetails() {
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public BankAddress getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(BankAddress bankAddress) {
		this.bankAddress = bankAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// Sets the non-id fields of the calling DirectDepositDetails to 
	// match that of the passed DirectDepositDetails
	public void transferFields(DirectDepositDetails other) {
		this.accountholderName = other.accountholderName;
		this.routingNumber = other.routingNumber;
		this.accountNumber = other.accountNumber;
		this.bankName = other.bankName;
		this.bankAddress = other.bankAddress;
		this.user = other.user;
	}

	public void buildFromForm(DirectDepositDetails_Form other) {
		this.accountholderName = other.getAccountholderName();
		this.routingNumber = other.getRoutingNumber();
		this.accountNumber = other.getAccountNumber();
		this.bankName = other.getBankName();
		this.bankAddress.setBankName(other.getBankName());
		this.bankAddress.setStreetAddress(other.getStreetAddress());
		this.bankAddress.setExtraLocationInfo(other.getExtraLocationInfo());
		this.bankAddress.setPostalCode(other.getPostalCode());
		this.bankAddress.setState(other.getState());
		this.bankAddress.setCity(other.getCity());
	}
	
}
