package edu.sru.cpsc.webshopping.domain.billing;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Holds the name of a credit card network
 * Table entries built using data from:
 * https://www.creditcardinsider.com/blog/credit-card-companies-which-are-biggest-and-which-are-best/
 */
@Entity
public class CardType {
	
	@Id 
	private String cardType;

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
}
