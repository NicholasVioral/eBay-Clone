package edu.sru.cpsc.webshopping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.domain.widgets.AttributeRecommendation;
import edu.sru.cpsc.webshopping.domain.widgets.Category;

@Service
public class CategoryService {

    /**
     * Returns the stack or breadcrumb trail of categories for a given category
     * @param category The category to get the stack for
     * @return List<Category> A list of all categories in the stack
     */
    public List<String> generateCategoryStack(Category category) {
        List<String> categoryStack = new ArrayList<>();
        while (category != null) {
            categoryStack.add(0, category.getName()); // Add to the beginning of the list
            category = category.getParent();
        }
        return categoryStack;
    }

    /**
     * Returns a list of all attributes that are recommended for a given category and parent categories
     * @param category The category to get attributes for
     * @return List<AttributeRecommendation> A list of all attributes recommended for the category
     */
    public List<Attribute> getRecommendedAttributes(Category category) {
        List<Attribute> attributes = new ArrayList<>();
        List<AttributeRecommendation> attributeRecommendations = new ArrayList<>();
        while (category != null) {
            attributeRecommendations.addAll(category.getAttributes());
            category = category.getParent();
        }
        attributeRecommendations.sort((a1, a2) -> a2.getRecommendation() - a1.getRecommendation());
        for (AttributeRecommendation attributeRecommendation : attributeRecommendations) {
            attributes.add(attributeRecommendation.getAttribute());
        }
        return attributes;
    }

    /**
     * Returns top 5 attributes that are recommended for a given category and parent categories
     * @param category The category to get attributes for
     * @param offset The offset to start at
     * @return List<AttributeRecommendation> A list of the top 5 attributes recommended for the category
     */
    public List<Attribute> getTopRecommendedAttributes(Category category, int offset) {
        List<Attribute> attributes = new ArrayList<>();
        List<AttributeRecommendation> attributeRecommendations = new ArrayList<>();

        //Only one category for right now, uncomment for when multiple categories are added
        //        while (category != null) {
            attributeRecommendations.addAll(category.getAttributes());
//            category = category.getParent();
//        }
            //UNCOMMENT WHEN REIMPLEMENTING SORT BY AND GET TOP 5 RECOMMENDED ATTRIBUTES
//        attributeRecommendations.sort((a1, a2) -> a2.getRecommendation() - a1.getRecommendation());
//        attributeRecommendations = attributeRecommendations.subList(offset, Math.min(offset + 5, attributeRecommendations.size()));

        for (AttributeRecommendation attributeRecommendation : attributeRecommendations) {
            attributes.add(attributeRecommendation.getAttribute());
        }
        return attributes;
    }
}
