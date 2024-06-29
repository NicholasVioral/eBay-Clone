package edu.sru.cpsc.webshopping.repository.billing;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.user.User;

public interface PaymentDetailsRepository extends CrudRepository<PaymentDetails, Long> {
	
	PaymentDetails findByCardNumberAndExpirationDate(String cardNumber, String expirationDate);
	PaymentDetails findByCardholderName(String name);
	List<PaymentDetails> findAllByUser(User user);
}
