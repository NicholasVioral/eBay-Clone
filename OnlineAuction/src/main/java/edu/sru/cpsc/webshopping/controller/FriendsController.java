package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.misc.Friendship;
import edu.sru.cpsc.webshopping.domain.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.domain.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.dto.UserDto;
import edu.sru.cpsc.webshopping.repository.group.GroupRepository;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.misc.FriendSocialRequestRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.service.FriendshipService;
import edu.sru.cpsc.webshopping.service.GroupService;
import edu.sru.cpsc.webshopping.service.GroupService.MemberAdditionDto;
import edu.sru.cpsc.webshopping.service.GroupService.MemberRemovalDto;
import edu.sru.cpsc.webshopping.service.MessageService;
import edu.sru.cpsc.webshopping.service.NotificationService;
import edu.sru.cpsc.webshopping.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;


@Controller
public class FriendsController {
	
	Logger log = LoggerFactory.getLogger(FriendsController.class);
    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FriendSocialRequestRepository friendSocialRequestRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private GroupService groupService;
    
    @Autowired
    private MarketListingRepository marketListingRepository;
    
    @Autowired
    StatisticsDomainController statControl;
    
    @Autowired
    private GroupRepository groupRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    private NotificationService notificationService;

    @Autowired
    public FriendsController(GroupService groupService) {
        this.groupService = groupService;
    }
    
    @GetMapping("/Social")
    public String getSocialPage(Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("currentUser", currentUser); // Adding the currentUser to the model
        model.addAttribute("user", currentUser); // Keeping this for backward compatibility
        model.addAttribute("page", "social");
       
        // Your existing code to add friends, messages, friend requests, and all users to the model
        List<User> friends = friendshipService.getAllFriendsForUser(currentUser);
        List<SocialMessage> messages = messageService.getAllMessagesForUser(currentUser);
        List<SocialMessage> additionalMessages = messageService.getUnreadMessagesForUser(currentUser);
        List<SocialFriendRequest> friendRequests = friendSocialRequestRepository.findAllByReceiver(currentUser);
        List<SocialFriendRequest> outgoingPendingRequests = friendshipService.getOutgoingPendingRequests(currentUser);
        List<User> allUsers = userService.getAllUsers();
        int unreadMessageCount = messageService.getUnreadMessageCount(currentUser);
        
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("myusers", allUsers); // Consider if you need both 'allUsers' and 'myusers'
        model.addAttribute("messages", additionalMessages);
        model.addAttribute("friends", friends);
        model.addAttribute("messages", messages); // Be aware this might overwrite the previous 'messages' attribute
        model.addAttribute("friendRequests", friendRequests);
        model.addAttribute("outgoingPendingRequests", outgoingPendingRequests);
        model.addAttribute("unreadMessageCount", unreadMessageCount);

        // Code to fetch and add groups to the model
        if (currentUser != null) {
            List<Group> groups = groupService.getAllGroupsForUser(currentUser); // Assuming this method is implemented
            model.addAttribute("groups", groups);
        } else {
            // Optionally handle the case where the user is not found
        }
        
        return "social"; // Ensuring this matches your Thymeleaf template name
    }


   
    @PostMapping("/add")
    public String addFriend(@RequestParam(name="value", required=false) String value,
                            @RequestParam(name="filterType", required=false, defaultValue="name") String filterType,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            Principal principal) {

        User currentUser = userService.getUserByUsername(principal.getName());

        if (value != null && (
        	    ("name".equals(filterType) && currentUser.getUsername().equals(value)) || 
        	    ("email".equals(filterType) && currentUser.getEmail().equals(value))
        	)) {
        	    redirectAttributes.addFlashAttribute("errorMessage", "You cannot send a friend request to yourself!");
        	    return "redirect:/Social";
        	}

        User friendToAdd = null;

        if ("name".equals(filterType)) {
            friendToAdd = userRepository.findByUsername(value);
        } else if ("email".equals(filterType)) {
            friendToAdd = userRepository.findByEmail(value);
        }

        if (friendToAdd == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            return "redirect:/Social";
        }

        if (!"ROLE_USER".equals(friendToAdd.getRole())) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            return "redirect:/Social";
        }

        if (friendshipService.sendFriendRequest(currentUser, friendToAdd)) {
        	// log event
		    StatsCategory cat = StatsCategory.FRIENDREQUESTSENT;
		    Statistics stat = new Statistics(cat, 1);
		    stat.setDescription(currentUser.getUsername() + " sent a friend request to " + friendToAdd.getUsername());
		    statControl.addStatistics(stat);
		    
            redirectAttributes.addFlashAttribute("requestSent", true);
        }

        return "redirect:/Social";
    }
  
    @PostMapping("/remove")
    public String removeFriend(@RequestParam("friendId") Long friendId, Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Friendship friendship = friendshipService.getFriendshipBetweenUsers(currentUser, friendId);
        User friend = userService.getUserById(friendId);
        
        if(friendship != null) {
        	// log event
    	    StatsCategory cat = StatsCategory.REMOVEDFRIEND;
    	    Statistics stat = new Statistics(cat, 1);
    	    stat.setDescription(currentUser.getUsername() + " removed " + friend.getUsername() + " as a friend");
    	    statControl.addStatistics(stat);
        	
            friendshipService.removeFriendship(friendship);
        } else {
            model.addAttribute("errorMessage", "Friendship record not found!");
        }
        
        return "redirect:/Social";
    }
   
    @GetMapping("/inbox")
    public String displayInboxPage(Model model, Principal principal, @RequestParam Optional<Long> createdGroupId) {
        User user = userService.getUserByUsername(principal.getName());
        if (user == null) {
            // Redirect to a login page or show an error message as per your application's requirement
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("page", "inbox");

        List<User> friends = friendshipService.getAllFriendsForUser(user);
        
        List<SocialMessage> additionalMessages = messageService.getUnreadMessagesForUser(user);
        
        Set<User> mailFromNotFriend = additionalMessages.stream()
        		.filter(message -> (!friends.contains(message.getSender())))
        		.map(message -> message.getSender())
        		.collect(Collectors.toSet());
        
        List<User> mailFromUsers = Stream.concat(friends.stream(), mailFromNotFriend.stream()).toList();
        
        model.addAttribute("friends", mailFromUsers);

        // Fetch all groups the user is part of
        List<Group> groups = groupService.getAllGroupsForUser(user);
        model.addAttribute("groups", groups);

        // Additionally, highlight a newly created group if its ID is provided
        if (createdGroupId.isPresent()) {
            Group group = groupService.findGroupById(createdGroupId.get());
            if (group != null) {
                model.addAttribute("highlightedGroupId", group.getId());
            } else {
                // Consider adding a message or handling for when the group isn't found
                model.addAttribute("groupNotFound", true);
            }
        }

        return "inbox";
    }

    
    @GetMapping("/viewProfile/{friendId}")
    public String viewFriendProfile(@PathVariable("friendId") Long friendId, Model model, Principal principal, @Valid @ModelAttribute MarketListing marketListing) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("page", "viewProfile");

        User friend = userService.getUserById(friendId); 

        List<MarketListing> friendListings = marketListingRepository.findBySeller(friend); 
        model.addAttribute("listings", friendListings); 

        model.addAttribute("friend", friend);
        
        return "viewProfile";
    }

    @GetMapping("/api/groupConversations/{groupId}")
    public ResponseEntity<Map<String, Object>> getGroupConversation(@PathVariable Long groupId, Principal principal) {

        if (principal == null || principal.getName() == null) {
            System.err.println("Principal information is missing.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Unauthorized access"));
        }

        User currentUser = userService.getUserByUsername(principal.getName());
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "User not found"));
        }

        List<SocialMessage> messages = messageService.getAllMessagesForGroup(groupId);


        List<Map<String, Object>> messagesList = messages.stream().map(msg -> {
            Map<String, Object> map = new HashMap<>();
            map.put("content", msg.getContent());
            map.put("sender", Map.of("id", msg.getSender().getId(), "username", msg.getSender().getUsername()));
            map.put("timestamp", msg.getSentTimestamp().toString());
            map.put("senderImage", msg.getSender().getUserImage());
            return map;
        }).collect(Collectors.toList());

        Group group = groupService.findGroupById(groupId);
        if (group == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Group not found"));
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("groupName", group.getName());
        responseBody.put("messages", messagesList);

        Statistics stats = new Statistics(StatsCategory.SOCIAL, 1);
        stats.setDescription(currentUser.getUsername() + " viewed group conversation for group " + group.getName());
        statControl.addStatistics(stats);

        return ResponseEntity.ok(responseBody);
    }
    
    @GetMapping("/api/conversations/{userId}")
    public ResponseEntity<Map<String, Object>> getConversation(@PathVariable Long userId, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        User friend = userRepository.findById(userId).orElse(null);

        if (friend == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Friend not found"));
        }

        List<SocialMessage> messages = messageService.getAllMessagesForUser(currentUser, friend);

        List<Map<String, Object>> messagesList = messages.stream().map(msg -> {
            Map<String, Object> map = new HashMap<>();
            map.put("content", msg.getContent());
            map.put("sender", new HashMap<String, Object>() {{
                put("id", msg.getSender().getId());
                put("username", msg.getSender().getUsername());
            }}); // Adjust sender to have id and username
            map.put("receiver", new HashMap<String, Object>() {{
                put("id", msg.getReceiver().getId());
                put("username", msg.getReceiver().getUsername());
            }});
            map.put("timestamp", msg.getSentTimestamp().toString()); // Convert LocalDateTime to string
            map.put("senderImage", msg.getSender().getUserImage());
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("friendName", friend.getUsername());
        responseBody.put("messages", messagesList);

        // log event
        StatsCategory cat = StatsCategory.SOCIAL;
        Statistics stat = new Statistics(cat, 1);
        stat.setDescription(currentUser.getUsername() + " viewed conversation with " + friend.getUsername());
        statControl.addStatistics(stat);

        return ResponseEntity.ok(responseBody);
    }
    
 // In your GroupController class
    private static final Logger logger = LoggerFactory.getLogger(FriendsController.class);

    @GetMapping("/api/groupMembers/{groupId}")
    public ResponseEntity<?> getGroupMembers(@PathVariable Long groupId) {
        Set<User> members = groupService.findMembersByGroupId(groupId);

        // Convert members to DTOs
        Set<UserDto> memberDtos = members.stream()
                .map(user -> new UserDto(user.getId(), user.getUsername()))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(memberDtos);
    }



    
    @PostMapping("/acceptRequest")
    public String acceptFriendRequest(@RequestParam("requestId") Long requestId) {
        SocialFriendRequest request = friendSocialRequestRepository.findById(requestId).orElse(null);
        
        if (request != null) {
            User sender = request.getSender();
            User receiver = request.getReceiver();
            
            Friendship friendship = new Friendship();
            friendship.setUser1(sender);
            friendship.setUser2(receiver);
            friendshipService.addFriend(friendship);

            friendSocialRequestRepository.delete(request);
            
            // log event
    	    StatsCategory cat = StatsCategory.FRIENDREQUESTACCEPTED;
    	    Statistics stat = new Statistics(cat, 1);
    	    stat.setDescription(receiver.getUsername() + " accepted a friend request from " + sender.getUsername());
    	    statControl.addStatistics(stat);
        } else {
            System.err.println("Friend request with ID " + requestId + " not found.");
        }
        
        return "redirect:/Social";
    }


    @PostMapping("/declineRequest")
    public String declineFriendRequest(@RequestParam("requestId") Long requestId) {
        SocialFriendRequest request = friendSocialRequestRepository.findById(requestId)
                .orElse(null);
        if (request != null) {
            Long senderId = request.getSender().getId(); // Assuming getSender() returns a User object from which you can get an ID
            User receiver = request.getReceiver();
            
            friendshipService.declineRequest(request);
            
            // Send notification to the sender
            String notificationMessage = String.format("Sorry, your friend request to %s has been declined.", receiver.getUsername());
            notificationService.createNotification(senderId, notificationMessage);

            // Log event
            StatsCategory cat = StatsCategory.FRIENDREQUESTDECLINED;
            Statistics stat = new Statistics(cat, 1);
            stat.setDescription(receiver.getUsername() + " declined a friend request from " + request.getSender().getUsername());
            // Assuming you have a mechanism to log this event or store the statistic
            // statControl.addStatistics(stat); or similar logging functionality
        } else { 
            // Log an error message
            System.err.println("Friend request with ID " + requestId + " not found!!!");
        }
        return "redirect:/Social";
    }
    
    @GetMapping("/searchUser")
    @ResponseBody
    public List<String> searchUser(@RequestParam("value") String value, @RequestParam("filterType") String filterType) {
        List<User> matchedUsers;
        
        // Check the filterType and call the appropriate service method
        if ("name".equals(filterType)) {
            matchedUsers = userService.searchUsers(value, "name");
            return matchedUsers.stream().map(User::getUsername).collect(Collectors.toList());
        } else if ("email".equals(filterType)) {
            matchedUsers = userService.searchUsers(value, "email");
            return matchedUsers.stream().map(User::getEmail).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Invalid filter type");
        }
    } 
    
    @PostMapping("/cancelRequest")
    public String cancelFriendRequest(@RequestParam("requestId") Long requestId, Principal principal, RedirectAttributes redirectAttributes) {
        User currentUser = userService.getUserByUsername(principal.getName());
        SocialFriendRequest request = friendSocialRequestRepository.findById(requestId).orElse(null);

        if (request != null && request.getSender().getId() == currentUser.getId()) {
            friendshipService.cancelFriendRequest(requestId);
            redirectAttributes.addFlashAttribute("successMessage", "Friend request cancelled successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to cancel friend request.");
        }
        return "redirect:/Social";
    }
    
    @PostMapping("/createGroup")
    public String createGroup(@RequestParam("groupName") String groupName,
                              @RequestParam("groupDescription") String groupDescription,
                              @RequestParam(value = "friends", required = false) List<Long> friendsIds,
                              Principal principal, RedirectAttributes redirectAttributes) {
        User currentUser = userService.getUserByUsername(principal.getName());
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Current user not found.");
            return "redirect:/errorPage"; // Adjust as necessary
        }

        try {
            Group createdGroup = groupService.createGroupWithUsers(groupName, groupDescription, friendsIds, currentUser);
            redirectAttributes.addFlashAttribute("groupCreationSuccess", true);
            redirectAttributes.addFlashAttribute("createdGroupName", createdGroup.getName());
            redirectAttributes.addFlashAttribute("createdGroupMembers", createdGroup.getMembers().stream().map(User::getUsername).collect(Collectors.toList()));
            redirectAttributes.addAttribute("createdGroupId", createdGroup.getId()); // Pass the created group's ID as a parameter
        } catch (Exception e) {
            logger.error("Error creating group", e);
            redirectAttributes.addFlashAttribute("groupCreationFailure", true);
            return "redirect:/Social"; // Ensure this redirects back to the Social page where you can handle showing the newly created group
        }

        return "redirect:/Social"; // Ensure this redirects back to the Social page where you can handle showing the newly created group
    }

    @GetMapping("/showGroups")
    public String showGroupsPage(Model model, Principal principal) {
        String username = principal != null ? principal.getName() : "Guest";
        User currentUser = userService.getUserByUsername(username);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            List<Group> groups = groupService.getAllGroupsForUser(currentUser);
            model.addAttribute("groups", groups);
        } else {
            // Optionally handle the scenario where the current user is not found in your userService
            // For example, by redirecting to a login page or displaying an error message
        }
        return "social"; // Ensure this is the correct name of your Thymeleaf template
    }

    
    @GetMapping("/getGroupDetails")
    public ResponseEntity<?> getGroupDetails(@RequestParam("groupId") Long groupId) {
        logger.info("Fetching details for group ID: {}", groupId);
        try {
            Group group = groupService.findGroupById(groupId);
            if (group != null) {
                logger.info("Group found: {}", group.getName());
                return ResponseEntity.ok(group);
            } else {
                logger.error("Group with ID: {} not found.", groupId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group with ID: " + groupId + " not found.");
            }
        } catch (Exception e) {
            logger.error("Error fetching group details for ID: {}", groupId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching group details.");
        }
    }

    @PostMapping("/deleteGroup")
    public String deleteGroup(@RequestParam("groupId") Long groupId, RedirectAttributes redirectAttributes) {
        try {
            Optional<Group> groupOpt = groupRepository.findById(groupId);
            if (groupOpt.isPresent()) {
                Group group = groupOpt.get();
                groupService.sendGroupDeletedNotification(group); // Send notifications before deletion
                
                groupService.deleteGroup(groupId); // Now proceed with deletion
                redirectAttributes.addFlashAttribute("successMessage", "Group deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Group not found.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger in real applications
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting group: " + e.getMessage());
        }
        return "redirect:/Social";
    }

    
    
    @PostMapping("/loadMessages")
    public String loadMessages(@RequestParam(required = false) Long groupId, @RequestParam(required = false) Long friendId, RedirectAttributes redirectAttributes, Principal principal) {
        // Validation and authorization logic here
        
        // Determine whether this is a group or friend message load and add the appropriate query parameter
        if (groupId != null) {
            // This will add "groupId" as a query parameter to the URL for group messages
            redirectAttributes.addAttribute("groupId", groupId);
        } else if (friendId != null) {
            // This will add "friendId" as a query parameter to the URL for friend messages
            redirectAttributes.addAttribute("friendId", friendId);
        } else {
            // Handle cases where neither is provided, possibly redirecting to a default or error page
            return "redirect:/errorPage";
        }
        
        return "redirect:/inbox";
    }



    
    @PostMapping("/api/removeMemberFromGroup")
    public ResponseEntity<?> removeMemberFromGroup(@RequestBody MemberRemovalDto removalDto, Principal principal) {
        logger.info("Removing member ID: {} from group ID: {}", removalDto.getMemberId(), removalDto.getGroupId());
        try {
            boolean removed = groupService.removeUserFromGroup(removalDto.getGroupId(), removalDto.getMemberId(), principal.getName());
            if (removed) {
                return ResponseEntity.ok().body("Member removed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group or member not found");
            }
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error removing member from group", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing member from group");
        }
    }
    
    @PostMapping("/api/addMemberToGroup")
    public ResponseEntity<?> addMemberToGroup(@RequestBody MemberAdditionDto additionDto, Principal principal) {
        logger.info("Adding member ID: {} to group ID: {}", additionDto.getMemberId(), additionDto.getGroupId());
        try {
            boolean added = groupService.addUserToGroup(additionDto.getGroupId(), additionDto.getMemberId());
            if (added) {
                return ResponseEntity.ok().body("Member added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group or member not found");
            }
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error adding member to group", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding member to group");
        }
    }

    
    @PostMapping("/archiveGroup")
    public String archiveGroup(@RequestParam("groupId") Long groupId, RedirectAttributes redirectAttributes) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.setStatus("archived");
            groupRepository.save(group);

            // Use the autowired service to call the method
            groupService.sendGroupArchivedNotification(group);

            redirectAttributes.addFlashAttribute("successMessage", "Group archived successfully.");
            redirectAttributes.addFlashAttribute("archivedGroupName", group.getName());
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Group not found.");
        }
        return "redirect:/Social";
    }
    
    @PostMapping("/unarchiveGroup")
    public String unarchiveGroup(@RequestParam("groupId") Long groupId, RedirectAttributes redirectAttributes) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            
            // Check if the current user is the owner, similar to your other conditional checks
            if (group.getOwner() != null && group.getOwner().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                group.setStatus("active"); // Or whatever your equivalent active status is
                groupRepository.save(group);

                redirectAttributes.addFlashAttribute("successMessage", "Group unarchived successfully.");
                redirectAttributes.addFlashAttribute("unarchivedGroupName", group.getName());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "You do not have permission to unarchive this group.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Group not found.");
        }
        return "redirect:/Social";
    }
  
    @GetMapping("/groups")
    public String showInbox(Model model, Principal principal) {
        String username = principal.getName(); // Get current user's username
        User user = userService.getUserByUsername(username); // Fetch the User entity. Assume this method exists.
        List<Group> groups = groupService.getAllGroupsForUser(user); // Use the User entity to fetch groups
        model.addAttribute("groups", groups);
        return "inbox"; // Return the inbox template
    }
    
    @PostMapping("/leaveGroup")
    public String leaveGroup(@RequestParam("groupId") Long groupId, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName(); // Get the username of the currently logged-in user
        User user = userRepository.findByUsername(username); // Directly get the User entity
        
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
            return "redirect:/Social";
        }

        Optional<Group> groupOpt = groupRepository.findById(groupId); // Find the Group entity
        
        if (!groupOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Group not found.");
            return "redirect:/Social";
        }

        Group group = groupOpt.get();
        
        // Iterate over members to find the user by ID using the '==' operator for primitive long comparison
        boolean isMember = group.getMembers().stream().anyMatch(member -> member.getId() == user.getId());
        
        if (isMember) {
            // Remove the user by filtering out the user from the members
            Set<User> updatedMembers = group.getMembers().stream()
                                             .filter(member -> member.getId() != user.getId())
                                             .collect(Collectors.toSet());
            group.setMembers(updatedMembers);
            groupRepository.save(group); // Save the updated group
            redirectAttributes.addFlashAttribute("successMessage", "You have left the group successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not a member of this group.");
        }

        return "redirect:/Social";
    }
}