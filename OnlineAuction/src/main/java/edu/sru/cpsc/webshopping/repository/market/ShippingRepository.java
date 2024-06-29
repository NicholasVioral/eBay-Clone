package edu.sru.cpsc.webshopping.repository.market;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.market.Shipping;

public interface ShippingRepository extends CrudRepository<Shipping, Long> {
	Shipping findByAddress(ShippingAddress address);
}
