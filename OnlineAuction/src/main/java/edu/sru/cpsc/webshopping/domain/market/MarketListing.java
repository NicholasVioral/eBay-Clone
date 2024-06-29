package edu.sru.cpsc.webshopping.domain.market;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Min;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/**
 * Holds information on a marketplace listing that has been posted by a seller,
 * and that a user can buy from 
 */
@Entity
public class MarketListing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(columnDefinition="bigint default 0")
	private MarketListingStatus listingStatus;
	
	@NonNull
	@Column(precision = 10, scale = 2, columnDefinition="DECIMAL(10, 2)")
	private BigDecimal pricePerItem;
	
	private boolean setAutomatically;
	
	// removing auctionPrice because we are using the Auction class to hold the auction price
/* 	@NonNull
	@Column(precision = 10, scale = 2, columnDefinition="DECIMAL(10, 2)")
	private BigDecimal auctionPrice; */
	
	@NonNull
	@Min(value=0, message="Must have 0 or more items available.")
	private long qtyAvailable;
	
	@NonNull 
	private boolean isDeleted;
	
	@Column(columnDefinition="boolean default false")
	private boolean hasAcceptedOffer;
	
	@Column(columnDefinition="bigint default -1")
	private long acceptedOfferId;
	
	@ManyToOne
	@NonNull
	private User seller;
	
	private Date creationDate;
	
	@ManyToOne
	@NonNull
	private Widget widgetSold;
	
	private String coverImage;

	private boolean isLocalPickupOnly;

	@OneToOne(cascade = CascadeType.ALL)
	private Pickup localPickup;
	
	@OneToMany(mappedBy = "marketListing", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<WidgetImage> images;
	
	@OneToMany(mappedBy="marketListing", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<Transaction> transactions;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "auction_id")
	private Auction auction;

	@ManyToMany(mappedBy = "wishlistedWidgets")
	private Set<User> wishlistingUsers;
	
	private long pageViews;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPricePerItem() {
		return pricePerItem;
	}

	public void setPricePerItem(BigDecimal pricePerItem) {
		this.pricePerItem = pricePerItem;
	}

	public long getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(long qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	
	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Widget getWidgetSold() {
		return widgetSold;
	}

	public void setWidgetSold(Widget widgetSold) {
		this.widgetSold = widgetSold;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public boolean hasAcceptedOffer() {
		return hasAcceptedOffer;
	}
	
	public void setHasAcceptedOffer(boolean accepted) {
		this.hasAcceptedOffer = accepted;
	}
	
	public Long getAcceptedOfferId() {
		return acceptedOfferId;
	}

	public void setAcceptedOfferId(Long acceptedOfferId) {
		this.acceptedOfferId = acceptedOfferId;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Set<WidgetImage> getImages() {
		return images;
	}

	public void setImages(Set<WidgetImage> images) {
		this.images = images;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}
	
	public Auction getAuction() {
	    return auction;
    }

	public void setAuction(Auction auction) {
	    this.auction = auction;
    }

	public boolean getSetAutomatically() {
		return setAutomatically;
	}

	public void setSetAutomatically(boolean setAutomatically) {
		this.setAutomatically = setAutomatically;
	}

	public Pickup getLocalPickup() {
		return localPickup;
	}

	public void setLocalPickup(Pickup localPickup) {
		this.localPickup = localPickup;
	}

	public boolean getIsLocalPickupOnly() {
		return isLocalPickupOnly;
	}

	public void setIsLocalPickupOnly(boolean isLocalPickupOnly) {
		this.isLocalPickupOnly = isLocalPickupOnly;
	}

	public Set<User> getWishlistingUsers() {
		return wishlistingUsers;
	}

	public void setWishlistingUsers(Set<User> wishlistingUsers) {
		this.wishlistingUsers = wishlistingUsers;
	}

	public long getPageViews() {
		return pageViews;
	}
	
	public void setPageViews(long pageViews) {
		this.pageViews = pageViews;
	}
	
	public MarketListingStatus getListingStatus() {
		return listingStatus;
	}

	public void setListingStatus(MarketListingStatus listingStatus) {
		this.listingStatus = listingStatus;
	}
	
	public void acceptOffer(OfferNotification offer) {
		this.acceptedOfferId = offer.getId();
		this.hasAcceptedOffer = true;
	}
	
	public boolean isSeller(long id) {
		return id == seller.getId();
	}
	
	public boolean isBuyer(long id) {
		return id != seller.getId();
	}
}
