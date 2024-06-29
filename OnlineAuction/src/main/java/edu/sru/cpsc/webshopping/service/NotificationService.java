package edu.sru.cpsc.webshopping.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sru.cpsc.webshopping.domain.misc.Notification;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.misc.NotificationRepository;

/**
 * Service for creating and modifying notifications
 */
@Service
public class NotificationService {
	
	Logger log = LoggerFactory.getLogger(NotificationService.class);
	
    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Creates a notification for the user with given id and with given message
     * @param userId
     * @param message
     * @return
     */
    @Transactional
    public Notification createNotification(Long userId, String message) {
        Notification notification = new Notification(userId, message);
        Notification savedNotification = notificationRepository.save(notification);
        log.debug("Creating notification for user ID: " + userId + " with message: " + message);
        log.debug("Notification saved with ID: " + savedNotification.getId());
        return savedNotification;
    }

    /**
     * Get all notifications that aren't yet read by given user
     * @param userId
     * @return
     */
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findAllByUserIdAndIsReadFalse(userId);
    }

    /**
     * Marks given notification as read
     * @param notificationId
     */
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
    
    /**
     * Marks all notifications for given user as read
     * @param userId
     */
    public void markAllNotificationsAsReadForUser(Long userId) {
        List<Notification> notifications = notificationRepository.findAllByUserIdAndIsReadFalse(userId);
        for (Notification notification : notifications) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
    }
    
}