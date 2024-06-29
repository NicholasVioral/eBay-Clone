package edu.sru.cpsc.webshopping.domain.widgets;

import java.util.List;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity
public class Category{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    private String name;

    // Self-join to represent the parent-child relationship
    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<AttributeRecommendation> attributes;

    // Version field for optimistic locking and tracking updates
    @Version
    private int version;
    
    public Category() {
    	
    }
    
    public Category(String name) {
    	this.name = name;
    	this.version = 1;
    }

	// Getters and Setters
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

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
    public List<AttributeRecommendation> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<AttributeRecommendation> attributes) {
        this.attributes = attributes;
    }
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
	
}