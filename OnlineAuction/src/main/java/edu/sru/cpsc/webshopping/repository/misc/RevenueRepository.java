package edu.sru.cpsc.webshopping.repository.misc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sru.cpsc.webshopping.domain.misc.Revenue;

@Repository
public interface RevenueRepository extends CrudRepository<Revenue, Long> {
	
}
