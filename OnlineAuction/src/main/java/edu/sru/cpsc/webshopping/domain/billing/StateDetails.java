package edu.sru.cpsc.webshopping.domain.billing;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


/**
 * Holds information on available states, and their associated sales tax
 * Generated using: https://worldpopulationreview.com/state-rankings/sales-tax-by-state
 */
@Entity
public class StateDetails {
	@Id
	private String stateName;
	
	@NonNull
	@Column(precision = 10, scale = 2, columnDefinition="DECIMAL(10, 2)")
	private BigDecimal salesTaxRate;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public BigDecimal getSalesTaxRate() {
		return salesTaxRate;
	}

	public void setSalesTaxRate(BigDecimal salesTaxRate) {
		this.salesTaxRate = salesTaxRate;
	}
}
