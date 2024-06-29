package edu.sru.cpsc.webshopping.domain.market;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class OfferNotification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String potentialBuyerUserName;
	private long potentialBuyerUserId;
	
	private String offerAmount;
	private Long marketListingId;
	
	private Date createdOn = new Date();
	
	private Date acceptedOn = null;
	
	private boolean rejected = false;
	private boolean accepted = false;
	
	public OfferNotification() {}

	public OfferNotification(String potentialBuyerUserName, long potentialBuyerUserId, String offerAmount, long marketListingId) {
		this.potentialBuyerUserName = potentialBuyerUserName;
		this.potentialBuyerUserId = potentialBuyerUserId;
		this.offerAmount = offerAmount;
		this.marketListingId = marketListingId;
	}
}
