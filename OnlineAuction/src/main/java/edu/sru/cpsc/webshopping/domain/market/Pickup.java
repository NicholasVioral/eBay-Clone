package edu.sru.cpsc.webshopping.domain.market;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


@Entity
public class Pickup {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @OneToOne
    @NonNull
    private MarketListing listing;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private PickupAddress location;

    @OneToOne
	private Transaction transaction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PickupAddress getLocation() {
        return location;
    }

    public void setLocation(PickupAddress location) {
        this.location = location;
    }

    public MarketListing getListing() {
        return listing;
    }
    public void setListing(MarketListing listing) {
        this.listing = listing;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
