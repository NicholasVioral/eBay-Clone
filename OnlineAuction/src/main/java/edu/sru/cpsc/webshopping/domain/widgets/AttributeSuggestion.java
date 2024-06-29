package edu.sru.cpsc.webshopping.domain.widgets;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AttributeSuggestion {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="attribute_id")
	private Attribute associatedAttribute;

	private String value;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Attribute getAssociatedAttribute() {
		return associatedAttribute;
	}

	public void setAssociatedAttribute(Attribute associatedAttribute) {
		this.associatedAttribute = associatedAttribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
