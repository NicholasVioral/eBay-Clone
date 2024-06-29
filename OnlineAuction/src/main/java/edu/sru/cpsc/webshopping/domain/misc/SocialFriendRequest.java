package edu.sru.cpsc.webshopping.domain.misc;

import java.time.LocalDateTime;

import edu.sru.cpsc.webshopping.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class SocialFriendRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User sender;
    
    @ManyToOne
    private User receiver;
    
    @Enumerated(EnumType.STRING)
    private FriendStatus status; // PENDING, ACCEPTED, DECLINED

	private LocalDateTime timestamp;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }

    public void setTimestamp(LocalDateTime now) {
        this.timestamp = now;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}