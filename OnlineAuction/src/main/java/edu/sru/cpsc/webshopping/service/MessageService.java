package edu.sru.cpsc.webshopping.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.group.GroupRepository;
import edu.sru.cpsc.webshopping.repository.misc.MessageSocialRepository;
import jakarta.persistence.EntityManager;

/**
 * Service for handling messages between users
 * @author Tiffany
 */
@Service
public class MessageService {
	Logger log = LoggerFactory.getLogger(MessageService.class);
	
    @Autowired
    private MessageSocialRepository messageRepository;
    
    @Autowired
    private GroupRepository groupRepository;

    
    /**
     * Returns a list of all messages for given user
     * @param user
     * @return
     */
    public List<SocialMessage> getAllMessagesForUser(User user) {
        // This method fetches all messages where the user is either the sender or the receiver.
        return messageRepository.findBySenderOrReceiver(user, user);
    }
    
    /**
     * Returns messages sent between two given users
     * @param user1
     * @param user2
     * @return
     */
    public List<SocialMessage> getAllMessagesForUser(User user1, User user2) {
        // This method fetches all messages between two specific users.
        List<SocialMessage> user1ToUser2Messages = messageRepository.findAllBySenderAndReceiver(user1, user2);
        List<SocialMessage> user2ToUser1Messages = messageRepository.findAllBySenderAndReceiver(user2, user1);

        List<SocialMessage> allMessages = new ArrayList<>(user1ToUser2Messages);
        allMessages.addAll(user2ToUser1Messages);

        // Sort the messages by timestamp.
        allMessages.sort(Comparator.comparing(SocialMessage::getSentTimestamp));

        return allMessages;
    }
    
    /**
     * Returns all messages sent in given group
     * @param groupId
     * @return
     */
    public List<SocialMessage> getAllMessagesForGroup(Long groupId) {
        // Fetch the group and then the messages associated with this group.
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found for id " + groupId));

        return messageRepository.findByGroup(group);
    }

    /**
     * Gets unread messages for a user
     * @param recipient
     * @return
     */
    public List<SocialMessage> getUnreadMessagesForUser(User recipient) {
        // Fetch all unread messages for a given recipient.
        return messageRepository.findByReceiverAndIsReadFalse(recipient);
    }

    /**
     * Marks given message as read
     * @param message
     */
    public void markMessageAsRead(SocialMessage message) {
        // Mark a specific message as read.
        message.setRead(true);
        message.setReadTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }

    /**
     * Marks given message as delivered
     * @param message
     */
    public void markMessageAsDelivered(SocialMessage message) {
        // Mark a specific message as delivered.
        message.setDelivered(true);
        messageRepository.save(message);
    }
    
    /**
     * Saves given message to the database
     * @param message
     * @return
     */
    public SocialMessage saveMessage(SocialMessage message) {
        // Save a message to the database.
        SocialMessage savedMessage = messageRepository.save(message);
        return savedMessage;
    }
    
    /**
     * Gets the amount of unread messages of a given user
     * @param user
     * @return
     */
    public int getUnreadMessageCount(User user) {
    	int unreadMessageCount = getUnreadMessagesForUser(user).size();
    	log.info("unread message count -- " + unreadMessageCount);
    	return unreadMessageCount;
    }

    // Removed redundant save method.
}
