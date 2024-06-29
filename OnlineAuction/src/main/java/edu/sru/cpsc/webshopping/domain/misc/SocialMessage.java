package edu.sru.cpsc.webshopping.domain.misc;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SocialMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="sender_id")
    @NotNull
    private User sender;

    @ManyToOne
    @JoinColumn(name="receiver_id")
    @NotNull
    private User receiver;

    @Column(columnDefinition = "TEXT")
    @NotNull
    @Size(min = 1, max = 5000) // Example sizes; adjust as necessary
    private String content;

    private LocalDateTime sentTimestamp = LocalDateTime.now();
    private LocalDateTime readTimestamp;
    private boolean isDelivered = false;
    private boolean isRead = false;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialMessage that = (SocialMessage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SocialMessage{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", sentTimestamp=" + sentTimestamp +
                ", readTimestamp=" + readTimestamp +
                ", isRead=" + isRead +
                '}';
    }
    
    @ManyToOne
    @JoinColumn(name = "group_id") // This column should exist in your social_message table.
    private Group group;

    // Getters and setters for the group
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public LocalDateTime getSentTimestamp() {
		return sentTimestamp;
	}

	public void setSentTimestamp(LocalDateTime sentTimestamp) {
		this.sentTimestamp = sentTimestamp;
	}

	public LocalDateTime getReadTimestamp() {
		return readTimestamp;
	}

	public void setReadTimestamp(LocalDateTime readTimestamp) {
		this.readTimestamp = readTimestamp;
	}
	
	 public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
    
}