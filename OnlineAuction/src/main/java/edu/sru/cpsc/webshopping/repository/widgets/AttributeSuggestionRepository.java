package edu.sru.cpsc.webshopping.repository.widgets;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.domain.widgets.AttributeSuggestion;

@Repository
public interface AttributeSuggestionRepository extends CrudRepository<AttributeSuggestion, Long> {

	Iterable<AttributeSuggestion> findAllByAssociatedAttribute(Attribute attribute);
}
