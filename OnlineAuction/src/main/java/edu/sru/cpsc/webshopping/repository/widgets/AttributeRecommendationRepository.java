package edu.sru.cpsc.webshopping.repository.widgets;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.domain.widgets.AttributeRecommendation;
import edu.sru.cpsc.webshopping.domain.widgets.Category;

public interface AttributeRecommendationRepository extends CrudRepository<AttributeRecommendation, Long>  {
    List<AttributeRecommendation> findByCategoryOrderByRecommendationDesc(Category category);
    Optional<AttributeRecommendation> findByAttributeAndCategory(Attribute attribute, Category category);
}
