package edu.sru.cpsc.webshopping.repository.misc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sru.cpsc.webshopping.domain.misc.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndIsReadFalse(Long userId);

	List<Notification> findAllByUserIdAndIsReadFalse(Long userId);
}