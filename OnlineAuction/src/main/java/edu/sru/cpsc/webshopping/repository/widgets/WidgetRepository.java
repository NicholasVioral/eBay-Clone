package edu.sru.cpsc.webshopping.repository.widgets;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;

// Generic WidgetRepository that is to be used by Widget subtypes
// Work in progress, using as reference:
// https://blog.netgloo.com/2014/12/18/handling-entities-inheritance-with-spring-data-jpa/
public interface WidgetRepository extends CrudRepository<Widget, Long> {
  Widget findByName(String name);
  List<Widget> findByCategory(Category category);
}
