package edu.sru.cpsc.webshopping.repository.billing;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.billing.Paypal;

public interface PaypalRepository extends CrudRepository<Paypal, Long> {

}
