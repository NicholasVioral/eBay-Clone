package edu.sru.cpsc.webshopping.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.market.Shipping;
import edu.sru.cpsc.webshopping.repository.market.ShippingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Interaction controller with the Shipping table in the database
 */
@RestController
public class ShippingDomainController {
	private ShippingRepository shippingRepository;
	@PersistenceContext
	private EntityManager entityManager;
	
	public ShippingDomainController(ShippingRepository shippingRepository) {
		this.shippingRepository = shippingRepository;
	}
	
	/**
	 * Returns the Shipping row with the associated id
	 * @param shippingId id of the entry to find
	 * @return the found Shipping item
	 * @exception IllegalArgumentException if no entry is associated with the shippingId
	 */
	@GetMapping("/get-shipping-entry/{shippingId}")
	public Shipping getShippingEntry(@PathVariable("shippingId") long shippingId) {
		return shippingRepository.findById(shippingId).orElseThrow(() 
				-> new IllegalArgumentException("Invalid ID passed to getShippingEntry"));
	}
	
	/**
	 * Deletes the sent shipping details
	 * @param details
	 */
	public void deleteShipping(Shipping details) {
		System.out.println("entered pdcont");
		Shipping shippingDetails = entityManager.find(Shipping.class, details.getId());
		if (shippingDetails != null)
		{
			System.out.println(shippingDetails.getId());
			shippingRepository.deleteById(shippingDetails.getId());
		}
	}
	
	
	/**
	 * Edits the fields of the associated Shipping row to match the values of the passed Shipping item
	 * @param shipping Shipping instance with the new values
	 * @exception IllegalArgumentException if no row in the database has the same id as the passed Shipping entry
	 */
	@PostMapping("/edit-shipping-entry") 
	public void editShippingEntry(@Validated Shipping shipping) {
		Shipping currEntry = entityManager.find(Shipping.class, shipping.getId());
		if (currEntry == null) {
			throw new IllegalArgumentException("Invalid entry passed to editShippingEntry");
		}
		currEntry.setAddress(shipping.getAddress());
		currEntry.setCarrier(shipping.getCarrier());
		currEntry.setTrackingNumber(shipping.getTrackingNumber());
		
		shippingRepository.save(currEntry);
	}

}
