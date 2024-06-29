package edu.sru.cpsc.webshopping.domain.widgets;

import java.util.Set;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
/**
 * Widget class stores basic information such as name, description, and the category of the widget. Other widget classes inherit these from it.
 * @author NICK
 *
 */
// Types of Widgets that can be sold through the website
// This type will have different Widget subtypes, for each type of website sold
// Each Subtype will have more detailed information
@Entity
// We expect to have many different Widget subtypes, so JOINED is chosen to split each subtype's unique characteristics into a
// separate table
@Inheritance(strategy=InheritanceType.JOINED)
public class Widget {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NonNull
	private String name;
	
	@NonNull
	@Column(length = 1000)
	private String description;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "widget", cascade=CascadeType.ALL)
    private Set<WidgetAttribute> widgetAttributes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Set<WidgetAttribute> getAttributes() {
        return widgetAttributes;
    }
	
	public void setAttributes(Set<WidgetAttribute> widgetAttributes) {
        this.widgetAttributes = widgetAttributes;
    }
	
}