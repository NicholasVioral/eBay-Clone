package edu.sru.cpsc.webshopping.repository.misc;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.sru.cpsc.webshopping.domain.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.group.Group; // Ensure this import matches your Group entity location

public interface MessageSocialRepository extends JpaRepository<SocialMessage, Long> {
    List<SocialMessage> findBySenderOrReceiver(User sender, User receiver);
    List<SocialMessage> findByReceiverAndIsReadFalse(User recipient);
    List<SocialMessage> findAllBySenderAndReceiver(User sender, User receiver);

    // Method to fetch messages by group. This will find all SocialMessage entities associated with the given Group.
    List<SocialMessage> findByGroup(Group group);

    // Optionally, if you want to fetch messages directly by the groupId and you have a mapping for the groupId in your SocialMessage entity, you could define this method as well. Uncomment and use it if needed.
    // List<SocialMessage> findByGroupId(Long groupId);
}

