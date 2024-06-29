package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.service.GroupService;
import edu.sru.cpsc.webshopping.service.MessageService;
import edu.sru.cpsc.webshopping.service.NotificationService;
import edu.sru.cpsc.webshopping.service.UserService;
import jakarta.persistence.EntityManager;

@Controller
public class WebSocketController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    StatisticsDomainController statControl;

    @MessageMapping("/send/groupMessage")
    public void broadcastGroupMessage(@Payload IncomingGroupMessageDTO messageDto, Principal principal) {
        System.out.println("Received group message for broadcast");
        if (principal == null || principal.getName() == null) {
            return;
        }

        String username = principal.getName();
        User sender = userService.getUserByUsername(username);
        if (sender == null) {
            return;
        }

        if (messageDto.getGroupId() == null) {
            return;
        }

        Group group = groupService.findGroupById(messageDto.getGroupId());
        if (group == null) {
            System.err.println("Group not found for ID: " + messageDto.getGroupId() + ". Cannot process group message.");
            return;
        }

        if ("archived".equals(group.getStatus())) {
            System.err.println("Cannot send messages to an archived group: " + group.getName());
            return;
        }

        SocialMessage message = new SocialMessage();
        message.setContent(messageDto.getContent());
        message.setSender(sender);
        message.setGroup(group);
        message.setSentTimestamp(LocalDateTime.now());

        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            return;
        }

        SocialMessage savedMessage = messageService.saveMessage(message);
        if (savedMessage == null || savedMessage.getId() == null) {
            return;
        }

        SocialMessageDTO messageDTO = toDTO(savedMessage);
        messagingTemplate.convertAndSend("/topic/groupMessages/" + group.getId(), messageDTO);

        // Creating notifications for all group members except the sender
        String notificationMessage = "New message in group: " + group.getName() + " from " + sender.getUsername();
        group.getMembers().forEach(member -> {
            if (!member.equals(sender)) {
                notificationService.createNotification(member.getId(), notificationMessage);
            }
        });
    }

    public static class IncomingGroupMessageDTO {
        private String content;
        private Long groupId; // Identify the group

        // Default constructor
        public IncomingGroupMessageDTO() {
        }

        // Constructor with fields
        public IncomingGroupMessageDTO(String content, Long groupId) {
            this.content = content;
            this.groupId = groupId;
        }

        // Getter for content
        public String getContent() {
            return content;
        }

        // Setter for content
        public void setContent(String content) {
            this.content = content;
        }

        // Getter for groupId
        public Long getGroupId() {
            return groupId;
        }

        // Setter for groupId
        public void setGroupId(Long groupId) {
            this.groupId = groupId;
        }

        // Optionally, you can override toString(), equals(), and hashCode() methods based on your requirements.

        @Override
        public String toString() {
            return "IncomingGroupMessageDTO{" +
                   "content='" + content + '\'' +
                   ", groupId=" + groupId +
                   '}';
        }
    }

    @MessageMapping("/send/message")
    @SendTo("/topic/messages")
    public SocialMessageDTO broadcastMessage(@Payload IncomingSocialMessageDTO messageDto, Principal principal) {
    	System.out.println("/send/message endpoint, message received: " + messageDto);
        User currentUser = userService.getUserByUsername(principal.getName());

        if (messageDto.getGroupId() != null) {
            // This is a group message
            Group group = groupService.findGroupById(messageDto.getGroupId());
            if (group == null) {
                System.err.println("Group not found");
                return null; // Or handle appropriately
            }

            group.getMembers().forEach(member -> {
                if (!member.equals(currentUser)) {
                    SocialMessage message = new SocialMessage();
                    message.setContent(messageDto.getContent());
                    message.setSender(currentUser);
                    message.setReceiver(member); // Consider how you want to handle this for group messages
                    message.setSentTimestamp(LocalDateTime.now());
                    messageService.saveMessage(message);

                    // Consider creating a method to broadcast to group without duplicating messages
                    messagingTemplate.convertAndSend("/topic/groupMessages/" + group.getId(), toDTO(message));
                }
            });
            return null; // For group messages, return null
        } else if (messageDto.getReceiverId() != null) {
            // Handle as individual message
            User receiver = userService.getUserById(messageDto.getReceiverId());
            if (receiver == null) {
                System.out.println("Receiver is null");
                return null; // Or handle appropriately
            }

            SocialMessage message = new SocialMessage();
            message.setContent(messageDto.getContent());
            message.setSender(currentUser);
            message.setReceiver(receiver);
            message.setSentTimestamp(LocalDateTime.now());
            messageService.saveMessage(message);

            Statistics stats = new Statistics(StatsCategory.MESSAGES, 1);
            stats.setDescription(currentUser.getUsername() + " messaged " + receiver.getUsername() + ": " + message.getContent());
            statControl.addStatistics(stats);

            notificationService.createNotification(receiver.getId(), currentUser.getUsername() + " messaged you!");

            return toDTO(message);
        } else {
            // Handle error: neither group nor receiver specified
            System.err.println("Message destination not specified");
            return null; // Or handle appropriately
        }
    }

 // Convert SocialMessage entity to DTO
    private SocialMessageDTO toDTO(SocialMessage message) {
        UserDTO senderDTO = message.getSender() != null ? toDTO(message.getSender()) : null;
        UserDTO receiverDTO = message.getReceiver() != null ? toDTO(message.getReceiver()) : null;
        String senderImage = message.getSender() != null ? message.getSender().getUserImage() : null;
        
        return new SocialMessageDTO(
            message.getContent(),
            senderDTO,
            receiverDTO,
            message.getSentTimestamp(),
            senderImage
        );
    }

    // Convert User entity to DTO
    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }

    public static class SocialMessageDTO {
        private String content;
        private UserDTO sender;
        private UserDTO receiver;
        private LocalDateTime timestamp;
        private String senderImage;

        public SocialMessageDTO(String content, UserDTO sender, UserDTO receiver, LocalDateTime timestamp, String senderImage){
            this.setContent(content);
            this.setSender(sender);
            this.setReceiver(receiver);
            this.setTimestamp(timestamp);
            this.setSenderImage(senderImage);
        }

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public UserDTO getSender() {
			return sender;
		}

		public void setSender(UserDTO sender) {
			this.sender = sender;
		}

		public UserDTO getReceiver() {
			return receiver;
		}

		public void setReceiver(UserDTO receiver) {
			this.receiver = receiver;
		}
		
		 public LocalDateTime getTimestamp() {
	            return timestamp;
	        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public String getSenderImage() {
            return senderImage;
        }

        public void setSenderImage(String senderImage) {
            this.senderImage = senderImage;
        }

    }

    public static class UserDTO {
        private Long id;
        private String username;

        public UserDTO(Long id, String username) {
            this.setId(id);
            this.setUsername(username);
        }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

    }

    public static class IncomingSocialMessageDTO {
        @Override
		public String toString() {
			return "IncomingSocialMessageDTO [content=" + content + ", receiverId=" + receiverId + ", groupId="
					+ groupId + "]";
		}
		private String content;
        private Long receiverId;
        private Long groupId;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Long getReceiverId() {
			return receiverId;
		}
		public void setReceiverId(Long receiverId) {
			this.receiverId = receiverId;
		}
		public Long getGroupId() {
			return groupId;
		}
		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}

    }
}


