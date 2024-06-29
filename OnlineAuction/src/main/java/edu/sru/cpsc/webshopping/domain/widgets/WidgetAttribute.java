package edu.sru.cpsc.webshopping.domain.widgets;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WidgetAttribute {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Copy key from parent attribute
    @NonNull
    private String attributeKey;
    
    // Value to be filled by user. key is
    private String value;

    @ManyToOne
    @JoinColumn(name="widget_id")
    private Widget widget;
    
    @ManyToOne
    @JoinColumn(name="attribute_id")
    private Attribute widAttribute;
    
	public WidgetAttribute() {

	}

	public WidgetAttribute(Attribute attribute) {
    	this.widAttribute = attribute;
    	this.attributeKey = attribute.getAttributeKey(); //set key to attribute key
    }

    public WidgetAttribute(Widget widget, Attribute attribute) {
    	this.widAttribute = attribute;
    	this.widget = widget;
    	this.attributeKey = attribute.getAttributeKey(); //set key to attribute key
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

	public Attribute getAttribute() {
		return widAttribute;
	}

	public void setAttribute(Attribute attribute) {
		this.widAttribute = attribute;
		this.attributeKey = attribute.getAttributeKey(); //set key to attribute key
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}
}
