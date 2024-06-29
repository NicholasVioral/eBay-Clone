package edu.sru.cpsc.webshopping.controller.billing;

import org.hibernate.engine.internal.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;


/**
 * Controller for handling PaymentDetails in database
 */
@RestController
public class PaymentDetailsController {
	
	private final UserRepository userRepository;
    private final PaymentDetailsRepository paymentDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PaymentDetailsController(UserRepository userRepository, PaymentDetailsRepository paymentDetailsRepository) {
        this.userRepository = userRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    @GetMapping("/check-payment-details-exist")
    public ResponseEntity<?> checkPaymentDetailsExist() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean hasPaymentDetails = user.getPaymentDetails() != null && !user.getPaymentDetails().isEmpty();
        return ResponseEntity.ok(hasPaymentDetails);
    }
	
	/**
	 * Deletes the passed PaymentDetails from the database
	 * @param details details to delete
	 */
	public void deletePaymentDetails(PaymentDetails details) {
		System.out.println("entered pdcont");
		PaymentDetails dbDetails = entityManager.find(PaymentDetails.class, details.getId());
		if (dbDetails != null)
		{
			System.out.println(dbDetails.getId());
			paymentDetailsRepository.deleteById(dbDetails.getId());
		}
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public PaymentDetails addPaymentDetails(@Validated PaymentDetails details) {
	    System.out.println("add payment details database function called");
	    
	    // Encode fields
	    details.setCardholderName(details.getCardholderName());
	    details.setCardNumber(passwordEncoder.encode(details.getCardNumber()));
	    details.setLast4Digits(details.getLast4Digits());
	    details.setCardType(details.getCardType());
	    details.setExpirationDate(details.getExpirationDate());
	    details.setSecurityCode(passwordEncoder.encode(details.getSecurityCode()));
	    details.setBillingAddress(details.getBillingAddress());
	    
	    // Save and return the updated details
	    return paymentDetailsRepository.save(details);
	}

	
	@RequestMapping("/get-all-payment-details") 
	public Iterable<PaymentDetails> getAllPaymentDetails() {
		Iterable<PaymentDetails> paymentDetails = paymentDetailsRepository.findAll();
		return paymentDetails;
	}
	
	@RequestMapping("/get-payment-details-by-user") 
	public PaymentDetails[] getPaymentDetailsByUser(@PathVariable("user") User user) {
		if(user.getPaymentDetails() == null || user.getPaymentDetails().isEmpty())
			return null;
		List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAllByUser(user);
		return paymentDetailsList.toArray(new PaymentDetails[paymentDetailsList.size()]);
	}
	
	/**
	 * checks to see if the paymentDetailsRepository already has the passed PaymentDetails
	 * @param details
	 * @return
	 */
	@RequestMapping("/check-for-same")
	public boolean checkDuplicateCard(PaymentDetails details){
		int check = 1;
		for(PaymentDetails PD : paymentDetailsRepository.findAll())
		{
			if( passwordEncoder.matches(details.getCardholderName(), PD.getCardholderName())
			&& passwordEncoder.matches(details.getCardNumber(), PD.getCardNumber())
			&& passwordEncoder.matches(details.getExpirationDate(), PD.getExpirationDate())
			&& passwordEncoder.matches(details.getSecurityCode(), PD.getSecurityCode()))
				check = 0;
		}
		if(check == 1)
			return false;
		else
			return true;
	}
	
	@RequestMapping({"/get-payment-detail/{id}"}) 
	public PaymentDetails getPaymentDetail(@PathVariable("id") long id, Model model) {
		PaymentDetails paymentDetails = paymentDetailsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID passed to find a user"));
		return paymentDetails;
	}
	
	/**
	 * finds if there are payment details in the paymentDetailsRepository that match the passed PaymentDetails 
	 * @param details
	 * @return
	 */
	public PaymentDetails getPaymentDetailsByCardNumberAndExpirationDate(PaymentDetails details) {
		for(PaymentDetails PD : paymentDetailsRepository.findAll())
		{
			if( passwordEncoder.matches(details.getCardholderName(), PD.getCardholderName())
			&& passwordEncoder.matches(details.getCardNumber(), PD.getCardNumber())
			&& passwordEncoder.matches(details.getExpirationDate(), PD.getExpirationDate())
			&& passwordEncoder.matches(details.getSecurityCode(), PD.getSecurityCode()))
				return PD;
		}
		return details;
	}
	
	/**
	 * Saves or updates the current PaymentDetails of the user
	 * PaymentDetails is encoded using BCryptPasswordEncoder and then stored in the database
	 * @param details new PaymentDetails to save
	 * @exception IllegalStateException is thrown if the user is not logged in
	 */
	@PostMapping("/update-payment-details") 
	@Transactional
	public void updatePaymentDetails(@Validated PaymentDetails details, @Validated PaymentDetails currDetails) {
		System.out.println("update payment details database function called");
		currDetails = paymentDetailsRepository.findById(currDetails.getId()).get();
		// Encode fields
		details.setId(currDetails.getId());
		details.setCardholderName(details.getCardholderName());
		details.setCardNumber(passwordEncoder.encode(details.getCardNumber()));
		details.setLast4Digits(details.getLast4Digits());
		details.setCardType(details.getCardType());
		details.setExpirationDate(details.getExpirationDate());
		details.setSecurityCode(passwordEncoder.encode(details.getSecurityCode()));
		details.setBillingAddress(details.getBillingAddress());
		// No assigned details - add to user
		currDetails.transferFields(details);
		paymentDetailsRepository.save(currDetails);
	}
	
	public boolean matchExistingCard(String secCode, PaymentDetails details)
	{
		if(passwordEncoder.matches(secCode, details.getSecurityCode()))
		{
			return true;
		}
		else
			return false;
	}
}
