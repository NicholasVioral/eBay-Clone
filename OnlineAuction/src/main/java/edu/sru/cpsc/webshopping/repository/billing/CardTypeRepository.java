package edu.sru.cpsc.webshopping.repository.billing;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.billing.CardType;

public interface CardTypeRepository extends CrudRepository<CardType, String> {

}
