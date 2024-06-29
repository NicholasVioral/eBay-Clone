package edu.sru.cpsc.webshopping.repository.billing;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.user.User;

	public interface ShippingAddressRepository extends CrudRepository<ShippingAddress, Long> {
		List<ShippingAddress> findAllByUser(User user);
	}
