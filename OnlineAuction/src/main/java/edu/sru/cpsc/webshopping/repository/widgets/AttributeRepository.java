package edu.sru.cpsc.webshopping.repository.widgets;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sru.cpsc.webshopping.domain.widgets.Attribute;

// Generic WidgetRepository that is to be used by Widget subtypes
// Work in progress, using as reference:
// https://blog.netgloo.com/2014/12/18/handling-entities-inheritance-with-spring-data-jpa/
@Repository
public interface AttributeRepository extends CrudRepository<Attribute, Long> {

    // Find all attributes by attribute name
    Optional<Attribute> findFirstByAttributeKey(String attributeKey);
	
}