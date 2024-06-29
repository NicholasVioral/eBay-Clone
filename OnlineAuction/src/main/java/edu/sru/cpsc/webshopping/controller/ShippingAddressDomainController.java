package edu.sru.cpsc.webshopping.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.billing.ShippingAddressRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RestController
public class ShippingAddressDomainController {
		

		private ShippingAddressRepository shippingAddressRepository;
		@PersistenceContext
		private EntityManager entityManager;
		
		public ShippingAddressDomainController(ShippingAddressRepository shippingAddressRepository) {
			this.shippingAddressRepository = shippingAddressRepository;
		}
		
		@Transactional
		public void addShippingAddress(@Validated ShippingAddress details, User user) {
			System.out.println("add shipping details database function called");
				if(user.getDefaultShipping() == null)
					user.setDefaultShipping(details);
				shippingAddressRepository.save(details);
				entityManager.persist(details);
			
		}
		
		@Transactional
		public void updateShippingAddress(@Validated ShippingAddress details, @Validated ShippingAddress currDetails) {
			System.out.println("update payment details database function called");
			currDetails = shippingAddressRepository.findById(currDetails.getId()).get();
			// Encode fields
			details.setId(currDetails.getId());
			// No assigned details - add to user
			ShippingAddress curr = entityManager.find(ShippingAddress.class, currDetails.getId());
			curr.transferFields(details);
			shippingAddressRepository.save(curr);
		}
		
		/**
		 * Returns the Shipping row with the associated id
		 * @param shippingId id of the entry to find
		 * @return the found Shipping item
		 * @exception IllegalArgumentException if no entry is associated with the shippingId
		 */
		@GetMapping("/get-shipping-address-entry/{shippingId}")
		public ShippingAddress getShippingAddressEntry(@PathVariable("shippingId") long shippingId) {
			return shippingAddressRepository.findById(shippingId).orElseThrow(() 
					-> new IllegalArgumentException("Invalid ID passed to getShippingEntry"));
		}
		
		/**
		 * Deletes the sent shipping details
		 * @param details
		 */
		public void deleteShippingAddress(ShippingAddress details) {
			System.out.println("entered pdcont");
			ShippingAddress shippingDetails = entityManager.find(ShippingAddress.class, details.getId());
			if (shippingDetails != null)
			{
				System.out.println(shippingDetails.getId());
				shippingAddressRepository.deleteById(shippingDetails.getId());
			}
		}
		
		@RequestMapping("/get-shipping-address-details-by-user") 
		public ShippingAddress[] getShippingDetailsByUser(@PathVariable("user") User user) {
			List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAllByUser(user);
			return shippingAddresses.toArray(new ShippingAddress[shippingAddresses.size()]);
		}
		
		/**
		 * Edits the fields of the associated Shipping row to match the values of the passed Shipping item
		 * @param shipping Shipping instance with the new values
		 * @exception IllegalArgumentException if no row in the database has the same id as the passed Shipping entry
		 */
		@PostMapping("/edit-shipping-address-entry") 
		public void editShippingEntry(@Validated ShippingAddress shipping) {
			ShippingAddress currEntry = entityManager.find(ShippingAddress.class, shipping.getId());
			if (currEntry == null) {
				throw new IllegalArgumentException("Invalid entry passed to editShippingEntry");
			}
			currEntry.setStreetAddress(shipping.getStreetAddress());
			currEntry.setPostalCode(shipping.getPostalCode());
			currEntry.setState(shipping.getState());
			currEntry.setRecipient(shipping.getRecipient());
			
			shippingAddressRepository.save(currEntry);
		}

}
