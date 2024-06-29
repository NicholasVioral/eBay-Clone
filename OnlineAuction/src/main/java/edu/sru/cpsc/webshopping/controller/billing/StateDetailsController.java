package edu.sru.cpsc.webshopping.controller.billing;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.billing.StateDetails;
import edu.sru.cpsc.webshopping.repository.billing.StateDetailsRepository;

/**
 * A controller for handling the retrieval of StateDetails from the database
 */
@RestController
public class StateDetailsController {
	private StateDetailsRepository stateDetailsRepository;
	
	StateDetailsController(StateDetailsRepository stateDetailsRepository) {
		this.stateDetailsRepository = stateDetailsRepository;
	}

	/**
	 * Gets an Iterable collection of all of the StateDetails stored in the database
	 * Useful for creating a selection field of every state
	 * @return an Iterable of all StateDetails
	 */
	@RequestMapping("/get-all-states") 
	public Iterable<StateDetails> getAllStates() {
		return stateDetailsRepository.findAll();
	}
	
	/**
	 * Gets the record for a specific state
	 * @param stateId the name of the state to retrieve
	 * @return the StateDetails associated with the name
	 */
	@RequestMapping("/get-state/{stateId}")
	public StateDetails getState(@PathVariable("stateId") String stateId) {
		return stateDetailsRepository.findById(stateId).orElseThrow(() -> new IllegalArgumentException("Invalid stateId passed to getState"));
	}
}
