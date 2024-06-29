package edu.sru.cpsc.webshopping.domain;

import java.util.Date;
import edu.sru.cpsc.webshopping.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 5000)
    private String content;
    
    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public void setPost(Post post) {
	    this.post = post;
	}
	
	public Post getPost() {		
		return post;
	}
	
	public void setUser(User user) {
	    this.user = user;
	}
	
	public User getUser() {
	    return user;
	}
	
    public Date getCreatedOn() {
        return createdOn;
    }
    
	public void setCreatedOn(Date createdOn) {
	    this.createdOn = createdOn;
	}
}