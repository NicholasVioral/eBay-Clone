package edu.sru.cpsc.webshopping.domain.user;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class FriendRequest
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private User requestReceiver;
	@ManyToOne
	private User requestSender;
	
	private LocalDateTime timeSent;
	
	//-1 = neither accepted or denied.
	//0 = denied request
	//1 = accepted request
	@Value("-1")
	private int status;
	
	FriendRequest(Long id, User requestReciever, User requestSender, LocalDateTime timeSent)
	{
		this.id=id;
		this.requestReceiver=requestReciever;
		this.requestSender=requestSender;
		this.timeSent=timeSent;
	}
	FriendRequest(Long id, User requestReciever, User requestSender)
	{
		this.id=id;
		this.requestReceiver=requestReciever;
		this.requestSender=requestSender;
	}
	public void setSender(User sender)
	{
		requestSender=sender;
	}
	
	public void setReceiver(User reciever)
	{
		requestReceiver=reciever;
	}
	
	public User getSender()
	{
		return requestSender;
	}
	
	public User getReceiver()
	{
		return requestReceiver;
	}
	
	//This will also need to be set for vis versa before messaging is allowed.
	public void setStatus(boolean decision)
	{
		if(decision)
			status=1;
		else
			status=0;
	}
	
	public int getStatus()
	{
		return status;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getTimeSent() {
		return timeSent;
	}
}
	