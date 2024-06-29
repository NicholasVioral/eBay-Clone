package edu.sru.cpsc.webshopping.domain.misc;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Revenue {
	private float revenueAmount;
	private static Revenue instance = new Revenue();
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private Revenue() {
		revenueAmount = 0;
	}
	
	public void setRevenue(float revenue) {
		this.revenueAmount = revenue;
	}
	
	public float getRevenue() {
		return this.revenueAmount;
	}
	
	public void addRevenue(float value) {
		revenueAmount = revenueAmount + value;
	}
	
	public void removeRevenue(float value) {
		revenueAmount = revenueAmount - value;
	}
	
	public static Revenue getInstance() {
		return instance;
	}
}
