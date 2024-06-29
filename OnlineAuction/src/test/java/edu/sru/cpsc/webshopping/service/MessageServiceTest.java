package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.misc.MessageSocialRepository;
import edu.sru.cpsc.webshopping.repository.group.GroupRepository;

public class MessageServiceTest {

    @Mock
    private MessageSocialRepository messageRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMessagesForUser() {
        User user = new User();
        user.setId(1L);

        SocialMessage message1 = new SocialMessage();
        message1.setSender(user);
        message1.setReceiver(user);
        SocialMessage message2 = new SocialMessage();
        message2.setSender(user);
        message2.setReceiver(user);

        List<SocialMessage> messages = Arrays.asList(message1, message2);

        when(messageRepository.findBySenderOrReceiver(user, user)).thenReturn(messages);

        List<SocialMessage> result = messageService.getAllMessagesForUser(user);

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllMessagesForUser1AndUser2() {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        SocialMessage message1 = new SocialMessage();
        message1.setSender(user1);
        message1.setReceiver(user2);
        SocialMessage message2 = new SocialMessage();
        message2.setSender(user2);
        message2.setReceiver(user1);

        when(messageRepository.findAllBySenderAndReceiver(user1, user2)).thenReturn(Arrays.asList(message1));
        when(messageRepository.findAllBySenderAndReceiver(user2, user1)).thenReturn(Arrays.asList(message2));

        List<SocialMessage> result = messageService.getAllMessagesForUser(user1, user2);

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllMessagesForGroup() {
        Long groupId = 1L;
        Group group = new Group();
        group.setId(groupId);

        SocialMessage message1 = new SocialMessage();
        message1.setGroup(group);
        SocialMessage message2 = new SocialMessage();
        message2.setGroup(group);

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(messageRepository.findByGroup(group)).thenReturn(Arrays.asList(message1, message2));

        List<SocialMessage> result = messageService.getAllMessagesForGroup(groupId);

        assertEquals(2, result.size());
    }
}
