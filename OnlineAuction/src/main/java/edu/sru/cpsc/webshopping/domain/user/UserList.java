package edu.sru.cpsc.webshopping.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UserList {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
@ManyToOne
private User friend;
@ManyToOne
private User block;
@ManyToOne
private User owner;

public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public User getOwner() {
	return owner;
}
public void setOwner(User owner) {
	this.owner = owner;
}
public User getFriend() {
	return friend;
}
public String setFriend(User friend) 
{
	//This needs to link to the database instead of just an empty instance.
	FriendRequest f = new FriendRequest(id, friend, owner);
	int status= f.getStatus();
	if(status==1)
	{
		this.friend = friend;
		return "Friend Request Accepted!";
	}
	else if(status==0)
	{
		return "Friend Request Denied!";
	}
	else if(status==-1)
	{
		// Update to send the request to the database
		
		return "Friend Request Sent!";
	}
	return "An Error has Occured With the Friend Request Function"; 
}
public User getBlock() {
	return block;
}
public void setBlock(User block) {
	this.block = block;
}

}
