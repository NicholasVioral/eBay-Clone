package edu.sru.cpsc.webshopping.domain.market;

import java.math.BigDecimal;
import java.sql.Date;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import javax.validation.constraints.Min;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.RefundTicket;
import edu.sru.cpsc.webshopping.domain.user.User;



/**
 * Holds information on a purchase a User has made
 */
@Entity
public class Transaction {
	public final static BigDecimal WEBSITE_CUT_PERCENTAGE = new BigDecimal(0.1);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	@Min(value=1, message="Must have at least one item purchased.")
	private int qtyBought;
	
	@NonNull
	@Column(precision = 10, scale = 2, columnDefinition="DECIMAL(10, 2)")
	private BigDecimal totalPriceBeforeTaxes;
	
	@NonNull
	@Column(precision = 10, scale = 2, columnDefinition="DECIMAL(10, 2)")
	private BigDecimal totalPriceAfterTaxes;
	
	@NonNull
	@Column(precision = 10, scale = 2, columnDefinition="DECIMAL(10, 2)")
	private BigDecimal sellerProfit;
	
	@NonNull
	@CreationTimestamp
	private Date purchaseDate;
	
	@NonNull
	@ManyToOne
	private User seller;
	
	@NonNull
	@ManyToOne
	private User buyer;

	@NonNull
	@ManyToOne(cascade = CascadeType.MERGE)
	private MarketListing marketListing;

	private boolean isLocalPickup;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Shipping shippingEntry;

	@OneToOne
	private Pickup localPickup;
	
	@NonNull
	@ManyToOne(cascade = CascadeType.MERGE)
	private PaymentDetails paymentDetails;
	
	@NonNull
	@ManyToOne(cascade = CascadeType.MERGE)
	private DirectDepositDetails depositDetails;
	
	@Enumerated(EnumType.STRING)
    private RefundStatus refundStatus = RefundStatus.NOT_REQUESTED;

	@OneToOne
	@JoinColumn(name = "refund_ticket_id")
	private RefundTicket refundTicket;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQtyBought() {
		return qtyBought;
	}

	public void setQtyBought(int qtyBought) {
		this.qtyBought = qtyBought;
	}

	public BigDecimal getTotalPriceBeforeTaxes() {
		return totalPriceBeforeTaxes;
	}

	public void setTotalPriceBeforeTaxes(BigDecimal totalPrice) {
		this.totalPriceBeforeTaxes = totalPrice;
	}

	public BigDecimal getTotalPriceAfterTaxes() {
		return totalPriceAfterTaxes;
	}

	public void setTotalPriceAfterTaxes(BigDecimal totalPriceAfterTaxes) {
		this.totalPriceAfterTaxes = totalPriceAfterTaxes;
	}

	public BigDecimal getSellerProfit() {
		return sellerProfit;
	}

	public void setSellerProfit(BigDecimal sellerProfit) {
		this.sellerProfit = sellerProfit;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	
	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public Shipping getShippingEntry() {
		return shippingEntry;
	}

	public void setShippingEntry(Shipping shippingEntry) {
		this.shippingEntry = shippingEntry;
	}

	public MarketListing getMarketListing() {
		return marketListing;
	}

	public void setMarketListing(MarketListing marketListing) {
		this.marketListing = marketListing;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public DirectDepositDetails getDepositDetails() {
		return depositDetails;
	}

	public void setDepositDetails(DirectDepositDetails depositDetails) {
		this.depositDetails = depositDetails;
	}
	
	public Pickup getLocalPickup() {
		return localPickup;
	}

	public void setLocalPickup(Pickup localPickup) {
		this.localPickup = localPickup;
	}

	public boolean isLocalPickup() {
		return isLocalPickup;
	}

	public void setLocalPickup(boolean isLocalPickup) {
		this.isLocalPickup = isLocalPickup;
	}
	
	public RefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }
    
    public RefundTicket getRefundTicket() {
        return refundTicket;
    }

    public void setRefundTicket(RefundTicket refundTicket) {
        this.refundTicket = refundTicket;
    }
    
    public enum RefundStatus {
        NOT_REQUESTED,
        PENDING,
        APPROVED,
        REJECTED
    }

	
}
