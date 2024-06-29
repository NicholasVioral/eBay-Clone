package edu.sru.cpsc.webshopping.domain.widgets;

import java.util.List;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.util.enums.AttributeDataType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Attribute {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // attribute to be filled by user. Value is determined when WidgetAttribute is created.
    @NonNull
    private String attributeKey;

	@Enumerated(EnumType.STRING)
	private AttributeDataType dataType;

    @OneToMany(mappedBy = "attribute", fetch = FetchType.EAGER)
    private List<AttributeRecommendation> categories;

    @OneToMany(mappedBy = "widAttribute", fetch = FetchType.EAGER)
    private List<WidgetAttribute> widgetAttribute;
    
    @OneToMany(mappedBy = "associatedAttribute", fetch = FetchType.EAGER)
    private List<AttributeSuggestion> suggestedValues;
    
	public Attribute() {}

	public Attribute(String name, AttributeDataType dataType) {
		this.attributeKey = name;
		this.dataType = dataType;
	}

    // Getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAttributeKey() {
		return attributeKey;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}

	public AttributeDataType getDataType() {
		return dataType;
	}

	public void setDataType(AttributeDataType dataType) {
		this.dataType = dataType;
	}

	public List<AttributeRecommendation> getCategories() {
		return categories;
	}
	public void setCategories(List<AttributeRecommendation> categories) {
		this.categories = categories;
	}
}
