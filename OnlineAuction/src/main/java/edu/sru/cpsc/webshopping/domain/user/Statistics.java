package edu.sru.cpsc.webshopping.domain.user;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Statistics {
	public Statistics() {
		
	}
	
	StatsCategory category;
	
	public Statistics(StatsCategory cat, float value) {
		this.category = cat;
		this.value = value;
		this.setDate(LocalDateTime.now());
		this.setHour();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String description;
	private float value;
	private LocalDateTime date;
	private int hour;
	public enum StatsCategory{
		AUCTION,
		SALEVALUE,
		SALE,
		ACCOUNTCREATION,
		ACCOUNTDELETED,
		EDITEDUSER,
		USERLOGIN,
		SOCIAL,
		MESSAGES,
		FRIENDREQUESTSENT,
		FRIENDREQUESTACCEPTED,
		FRIENDREQUESTDECLINED,
		REMOVEDFRIEND,
		WIDGETCREATED,
		LISTINGDELETED,
		TICKETS,
		WATCHLIST;
		
		@Override
	    public String toString() {
	        return name();
	    }
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public int getHour() {
		return hour;
	}
	public void setHour() {
		this.hour = date.getHour();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public StatsCategory getCategory() {
		return category;
	}

}
