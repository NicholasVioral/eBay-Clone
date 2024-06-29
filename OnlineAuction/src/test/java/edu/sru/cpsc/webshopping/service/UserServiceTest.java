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

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateUserProfile() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = userService.updateUserProfile(userId, existingUser);

        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
    }

    @Test
    void testAddUser() {
        User newUser = new User();
        newUser.setUsername("testUser");

        when(userRepository.save(newUser)).thenReturn(newUser);

        User addedUser = userService.addUser(newUser);

        assertNotNull(addedUser);
        assertEquals("testUser", addedUser.getUsername());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(userId));
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User retrievedUser = userService.getUserById(userId);

        assertNotNull(retrievedUser);
        assertEquals(userId, retrievedUser.getId());
    }

    @Test
    void testGetUserByUsername() {
        String username = "testUser";
        User existingUser = new User();
        existingUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(existingUser);

        User retrievedUser = userService.getUserByUsername(username);

        assertNotNull(retrievedUser);
        assertEquals(username, retrievedUser.getUsername());
    }

    @Test
    void testRateUser() {
        Long userId = 1L;
        float rating = 4.5f;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User ratedUser = userService.rateUser(userId, rating);

        assertNotNull(ratedUser);
        assertEquals(rating, ratedUser.getSellerRating().getRating());
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> allUsers = userService.getAllUsers();

        assertEquals(2, allUsers.size());
    }

    @Test
    void testSearchUsers() {
        String query = "test";
        String filterType = "name";
        User user1 = new User();
        user1.setUsername("testUser1");
        User user2 = new User();
        user2.setUsername("userTest2");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> matchedUsers = userService.searchUsers(query, filterType);

        assertEquals(1, matchedUsers.size());
        assertEquals("testUser1", matchedUsers.get(0).getUsername());
    }

    @Test
    void testFindById() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        Optional<User> foundUser = userService.findById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getId());
    }
}
