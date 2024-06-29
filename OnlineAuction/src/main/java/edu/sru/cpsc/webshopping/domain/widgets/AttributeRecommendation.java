package edu.sru.cpsc.webshopping.domain.widgets;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AttributeRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer recommendation;

    // Getters and setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Attribute getAttribute() {
        return attribute;
    }
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Integer getRecommendation() {
        return recommendation;
    }
    public void setRecommendation(Integer recommendation) {
        this.recommendation = recommendation;
    }

    
}
