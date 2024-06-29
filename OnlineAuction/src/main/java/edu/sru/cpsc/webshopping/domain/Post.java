package edu.sru.cpsc.webshopping.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.sru.cpsc.webshopping.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Post {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String content;

    @ManyToOne
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastCommentedOn = new Date();

    //  OneToMany relationship between Post and Comment
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("createdOn ASC")
    private List<Comment> comments = new ArrayList<>();

    // For archiving posts
    private boolean archived = false;
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    public Date getLastCommentedOn() {
        return lastCommentedOn;
    }

    public void setLastCommentedOn(Date lastCommentedOn) {
        this.lastCommentedOn = lastCommentedOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    public List<Comment> getComments() {
        return this.comments;
    }

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
}