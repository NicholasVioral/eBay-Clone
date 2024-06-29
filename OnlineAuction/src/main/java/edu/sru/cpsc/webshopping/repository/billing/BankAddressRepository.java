package edu.sru.cpsc.webshopping.repository.billing;


import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.billing.BankAddress;

	public interface BankAddressRepository extends CrudRepository<BankAddress, Long> {
	}
