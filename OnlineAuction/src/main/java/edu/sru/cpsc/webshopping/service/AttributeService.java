package edu.sru.cpsc.webshopping.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.domain.widgets.AttributeRecommendation;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.repository.widgets.AttributeRecommendationRepository;
import edu.sru.cpsc.webshopping.repository.widgets.AttributeRepository;
import edu.sru.cpsc.webshopping.util.enums.AttributeDataType;

/**
 * Service for handling attributes of a listing
 */
@Service
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeRecommendationRepository attributeRecommendationRepository;

    /**
     * If attribute is not in database save and return it otherwise return the attribute from the database
     * @param attributeKey
     * @param dataType
     * @return
     */
    public Attribute addOrGetAttribute(String attributeKey, AttributeDataType dataType) {
//        return attributeRepository.findByAttributeKey(attributeKey.toLowerCase().trim())
//                .orElseGet(() -> attributeRepository.save(new Attribute(attributeKey.toLowerCase().trim(), dataType)));
    	if(attributeRepository.findFirstByAttributeKey(attributeKey.toLowerCase().trim()).isPresent()) {
    		return attributeRepository.findFirstByAttributeKey(attributeKey.toLowerCase().trim()).get();
    	}
    	else {
    		return attributeRepository.save(new Attribute(attributeKey.toLowerCase().trim(), dataType));
    	}

    }

    /**
     * Associates an attribute with a category. Checks if the attribute is already associated with the category.
     * If the attribute is already associated with the category, the recommendation level is updated.
     * @param attributeName name of attribute to be associated
     * @param dataType data type of attribute
     * @param category category to associate attribute with
     * @param recommendationLevel recommendation level of attribute
     * @return void
     */
    public Attribute associateAttributeWithCategory(String attributeName, String dataType, Category category) {
        attributeName = attributeName.toLowerCase();
        //set string to dataType enum
        AttributeDataType dataTypeEnum = AttributeDataType.valueOf(dataType.toUpperCase());
        Attribute attribute = addOrGetAttribute(attributeName, dataTypeEnum);
        
        // Check if the attribute is already associated with the category
        //TODO UNDO THIS CHANGE TO ALLOW CUSTOM CREATED ATTRIBUTES TO BE ASSOCIATED WITH CATEGORY
//        if (!isAttributeAssociatedWithCategory(attribute, category)) {
//            AttributeRecommendation mapping = new AttributeRecommendation();
//            mapping.setAttribute(attribute);
//            mapping.setCategory(category);
//            mapping.setRecommendation(1);
//            attributeRecommendationRepository.save(mapping);
//        }
//        // If the attribute is already associated with the category, update the recommendation level +1
//        else {
//            updateRecommendationLevel(attribute, category, 1);
//        }
        
        return attribute;
        
      
    }
    
    /**
     * Checks if given attribute is associated with given category
     * @param attribute
     * @param category
     * @return
     */
    private boolean isAttributeAssociatedWithCategory(Attribute attribute, Category category) {
        return attributeRecommendationRepository.findByAttributeAndCategory(attribute, category).isPresent();
    }

    /**
     * Removes an association between an attribute and a category if the recommendation level is zero or below.
     * @param attribute
     * @param category
     */
    public void removeAssociationIfRecommendationIsZero(Attribute attribute, Category category) {
        Optional<AttributeRecommendation> mappingOpt = attributeRecommendationRepository.findByAttributeAndCategory(attribute, category);
        
        if (mappingOpt.isPresent()) {
            AttributeRecommendation mapping = mappingOpt.get();
            if (mapping.getRecommendation() <= 0) {
                attributeRecommendationRepository.delete(mapping);
            }
        }
    }

    /**
     * Updates the recommendation level of an attribute associated with a category.
     * @param attribute
     * @param category
     * @param delta
     */
    public void updateRecommendationLevel(Attribute attribute, Category category, Integer delta) {
        Optional<AttributeRecommendation> mappingOpt = attributeRecommendationRepository.findByAttributeAndCategory(attribute, category);
        
        if (mappingOpt.isPresent()) {
            AttributeRecommendation mapping = mappingOpt.get();
            mapping.setRecommendation(mapping.getRecommendation() + delta);
            attributeRecommendationRepository.save(mapping);
            
            // Check if the recommendation level has dropped to zero or below
            removeAssociationIfRecommendationIsZero(attribute, category);
        }
    }

}
