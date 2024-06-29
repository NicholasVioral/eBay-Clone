package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.group.GroupRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGroup() {
        String name = "Test Group";
        String description = "Test Description";
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);

        when(groupRepository.save(any(Group.class))).thenReturn(group);

        Group createdGroup = groupService.createGroup(name, description);

        assertEquals(name, createdGroup.getName());
        assertEquals(description, createdGroup.getDescription());
    }

    @Test
    void testAddUserToGroup() {
        Long groupId = 1L;
        Long userId = 1L;
        Group group = new Group();
        group.setId(groupId);
        User user = new User();
        user.setId(userId);

        when(groupRepository.findByIdWithMembersEagerly(groupId)).thenReturn(Optional.of(group));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertTrue(groupService.addUserToGroup(groupId, userId));
        assertTrue(group.getMembers().contains(user));
    }

    @Test
    void testFindGroupsByName() {
        String name = "Test Group";
        Group group = new Group();
        group.setName(name);
        List<Group> groups = new ArrayList<>();
        groups.add(group);

        when(groupRepository.findByName(name)).thenReturn(groups);

        List<Group> foundGroup = groupService.findGroupsByName(name);

        assertEquals(groups.size(), foundGroup.size());
        assertEquals(groups.get(0).getName(), foundGroup.get(0).getName());
    }
}