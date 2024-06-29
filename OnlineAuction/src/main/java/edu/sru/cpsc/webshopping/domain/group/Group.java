package edu.sru.cpsc.webshopping.domain.group;

import edu.sru.cpsc.webshopping.domain.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.User; // Ensure this import matches the location of your User entity
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_groups") // Ensure this table name does not conflict with reserved SQL keywords
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    
    // New field for storing group image path or identifier
    private String groupImage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "group_user", // Name of the join table
        joinColumns = @JoinColumn(name = "group_id"), // Column for Group ID
        inverseJoinColumns = @JoinColumn(name = "user_id") // Column for User ID
    )
    private Set<User> members = new HashSet<>();
    
    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private Set<SocialMessage> messages = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

	private String status;


    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }
    
    public void addMember(User user) {
        this.members.add(user);
        user.getGroups().add(this); // Ensure bidirectional synchronization
    }

    public void removeMember(User user) {
        this.members.remove(user);
        user.getGroups().remove(this); // Ensure bidirectional synchronization
    }
    
    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public Set<SocialMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<SocialMessage> messages) {
        this.messages = messages;
    }

    // No-argument constructor is required by JPA
    public Group() {}
    
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
