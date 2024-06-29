package edu.sru.cpsc.webshopping.domain.billing;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.User;

import edu.sru.cpsc.webshopping.util.enums.TicketState;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Data
public class RefundTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User createdBy; // User who requested the refund

    private String subject; // You can use this for a brief description of the refund request

    @Enumerated(EnumType.STRING)
    private TicketState state;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime assignedAt;
    private LocalDateTime resolvedAt;

    @ManyToOne
    private User assignedTo; // Employee assigned to handle the refund ticket

    private Double totalPriceAfterTaxes; // Total price after taxes from the purchase
    
    @OneToOne(mappedBy = "refundTicket")
    private Transaction transaction;
    
    private Long marketListingId;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
        name = "refund_ticket_message",
        joinColumns = @JoinColumn(name = "refund_ticket_id"),
        inverseJoinColumns = @JoinColumn(name = "message_id"))

    public boolean isResolved() {
        return TicketState.RESOLVED.equals(state);
    }
    
    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    public Long getMarketListingId() {
        return marketListingId;
    }

    public void setMarketListingId(Long marketListingId) {
        this.marketListingId = marketListingId;
    }

}
