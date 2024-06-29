package edu.sru.cpsc.webshopping.controller.billing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.billing.CardType;
import edu.sru.cpsc.webshopping.repository.billing.CardTypeRepository;

/**
 * Controller for retrieving a list of card types
 */
@RestController
public class CardTypeController {
	private CardTypeRepository cardRepository;
	
	CardTypeController(CardTypeRepository cardRepository) {
		this.cardRepository= cardRepository;
	}
	
	/** Returns all stored CardType
	 * @return an iterable of all CardType
	 */
	@RequestMapping("/get-all-card-types")
	public Iterable<CardType> getAllCardTypes() {
		return cardRepository.findAll();
	}

}
