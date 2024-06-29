package edu.sru.cpsc.webshopping.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.group.GroupRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.service.NotificationService;

/**
 * Service for handling groups
 * @author Nick
 */
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate messagingTemplate;

    public static class MemberRemovalDto {
        private Long groupId;
        private Long memberId;

        // Default no-argument constructor
        public MemberRemovalDto() {}

        // Getters and setters
        public Long getGroupId() { return groupId; }
        public void setGroupId(Long groupId) { this.groupId = groupId; }
        public Long getMemberId() { return memberId; }
        public void setMemberId(Long memberId) { this.memberId = memberId; }
    }
    
    // Made static and added default constructor
    public static class MemberAdditionDto {
        private Long groupId;
        private Long memberId;

        // Default no-argument constructor
        public MemberAdditionDto() {}

        // Getters and setters
        public Long getGroupId() {
            return groupId;
        }

        public void setGroupId(Long groupId) {
            this.groupId = groupId;
        }

        public Long getMemberId() {
            return memberId;
        }

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }
    }

    @Autowired
    public GroupService(GroupRepository groupRepository, 
                         UserRepository userRepository, 
                         NotificationService notificationService, 
                         SimpMessagingTemplate messagingTemplate) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Creates group with given name and description
     * @param name
     * @param description
     * @return
     */
    @Transactional
    public Group createGroup(String name, String description) {
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        return groupRepository.save(group);
    }

    /**
     * Adds given user to given group
     * @param groupId
     * @param userId
     * @return
     */
    @Transactional
    public boolean addUserToGroup(Long groupId, Long userId) {
        Group group = findGroupWithMembersEagerly(groupId); // Make sure this method is implemented to eagerly fetch members
        User user = userRepository.findById(userId).orElse(null);
        
        if (group != null && user != null) {
            group.addMember(user); // Assuming addMember handles both sides of the relationship
            groupRepository.save(group);
            return true;
        }
        return false;
    }
    
    /**
     * Returns group by group name
     * @param name
     * @return
     */
    public List<Group> findGroupsByName(String name) {
        return groupRepository.findByName(name);
    }
    
    /**
     * Returns all groups in the database
     * @return
     */
    public Iterable<Group> findAllGroups() {
        return groupRepository.findAll();
    }
    
    /**
     * Removes given group by ID from the database
     * @param groupId
     */
    @Transactional
    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    /**
     * Creates group with a list of userIDs
     * @param groupName
     * @param groupDescription
     * @param userIds
     * @param currentUser
     * @return
     */
    @Transactional
    public Group createGroupWithUsers(String groupName, String groupDescription, List<Long> userIds, User currentUser) {
        Group newGroup = new Group();
        newGroup.setName(groupName);
        newGroup.setDescription(groupDescription);
        
        // Set the current user as the owner of the group
        newGroup.setOwner(currentUser);
        
        Set<User> members = new HashSet<>();
        // Optionally add the owner as a member of the group as well
        members.add(currentUser);

        if (userIds != null && !userIds.isEmpty()) {
            userIds.forEach(friendId -> userRepository.findById(friendId).ifPresent(members::add));
        }

        newGroup.setMembers(members);
        return groupRepository.save(newGroup);
    }

    /**
     * Returns group by given ID
     * @param groupId
     * @return
     */
    @Transactional(readOnly = true)
    public Group findGroupById(Long groupId) {
        System.out.println("Attempting to find group with ID: " + groupId); // Debug log
        Group group = groupRepository.findByIdWithMembersEagerly(groupId).orElse(null);

        return group;
    }

    /**
     * Returns all groups that a given user is in
     * @param user
     * @return
     */
    public List<Group> getAllGroupsForUser(User user) {
        return groupRepository.findByMembersContaining(user);
    }

    /**
     * Returns groups that user with given username is in
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public List<Long> findGroupIdsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            // Assuming getGroups is properly initialized in the User entity
            return user.getGroups().stream()
                       .map(Group::getId)
                       .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
    
    /**
     * Finds group with given ID using eager fetching
     * @param groupId
     * @return
     */
    @Transactional
    public Group findGroupWithMembersEagerly(Long groupId) {
        // Directly use the repository method intended for eager fetching
        return groupRepository.findByIdWithMembersEagerly(groupId).orElse(null);
    }

    /**
     * Returns group by given ID
     * @param groupId
     * @return
     */
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    /**
     * Returns members of group by group ID
     * @param groupId
     * @return
     */
    public Set<User> findMembersByGroupId(Long groupId) {
        Optional<Group> groupOpt = groupRepository.findByIdWithMembersEagerly(groupId);
        if (groupOpt.isPresent()) {
            return groupOpt.get().getMembers();
        } else {
            return Collections.emptySet();
        }
    }
    
    /**
     * Returns given user from given group
     * @param groupId
     * @param memberId
     * @param username
     * @return
     */
    @Transactional
    public boolean removeUserFromGroup(Long groupId, Long memberId, String username) {
        // Fetch the group by ID
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            return false; // Group not found
        }

        // Ensure the current user is the owner of the group
        if (!group.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Only the group owner can remove members.");
        }

        // Prevent the owner from removing themselves
        if (group.getOwner().getId() == memberId) {
            throw new IllegalArgumentException("The owner cannot remove themselves from the group.");
        }

        // Fetch the member/user by ID
        User member = userRepository.findById(memberId).orElse(null);
        if (member == null) {
            return false; // Member not found
        }

        // Check if the member is part of the group and remove them
        boolean isMemberRemoved = group.getMembers().remove(member);
        if (isMemberRemoved) {
            // Save the updated group if the member was successfully removed
            groupRepository.save(group);
            return true;
        }

        return false; // Member was not part of the group
    }
    
    /**
     * Sends notification that given group was archived
     * @param group
     */
    @Transactional
    public void sendGroupArchivedNotification(Group group) {
        String notificationMessage = "The group '" + group.getName() + "' has been archived.";

        if (group.getMembers().isEmpty()) {
        }

        group.getMembers().forEach(member -> {
            // Assuming you have a method in your notification service to send notifications
            notificationService.createNotification(member.getId(), notificationMessage);

            // Optionally, you could use the messagingTemplate to broadcast this notification to a specific topic
            // that the group members are subscribed to.
            messagingTemplate.convertAndSend("/topic/groupArchived/" + group.getId(), notificationMessage);
        });
    }
    
    /**
     * Sends notification that given group was deleted
     * @param group
     */
    @Transactional
    public void sendGroupDeletedNotification(Group group) {
        String notificationMessage = "The group '" + group.getName() + "' has been deleted.";

        // Notify all group members about the deletion
        group.getMembers().forEach(member -> {
            notificationService.createNotification(member.getId(), notificationMessage);

            // If you're using real-time messaging
            messagingTemplate.convertAndSend("/topic/groupDeleted/" + group.getId(), notificationMessage);
        });
    }
}