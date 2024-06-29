package edu.sru.cpsc.webshopping.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.domain.billing.StateDetails;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

/**
 * Service for handling users
 */

@Service
public class UserService {
    private final UserRepository userRepository;
    private UserController userController;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUserProfile(Long userId, User user) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist"
                ));
        return userRepository.save(userToUpdate);
    }

    /**
     * Adds user to database
     * @param user
     * @return
     */
    public User addUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Removes user from database
     * @param userId
     */
    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    /**
     * Returns the user via the user ID
     * @param userId
     * @return
     */
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist"
                ));
        return user;
    }

    /**
     * Returns the user via the username
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalStateException("User with username " + username + " does not exist");
        }
        return user;
    }

    /**
     * Updates the users ratings
     * @param userId
     * @param rating
     * @return
     */
    public User rateUser(Long userId, float rating) {
        User userToRate = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist"
                ));
        //set rating with new incoming rating from a user. This will update the average rating.
        userToRate.getSellerRating().setRating(rating);
        return userRepository.save(userToRate);
    }
    
    /**
     * Returns a list of all users in the database
     * @return
     */
    public List<User> getAllUsers() {
        Iterable<User> usersIterable = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        for (User user : usersIterable) {
            userList.add(user);
        }
        return userList;
    }
    
    /**
     * Searches users by given query and filter
     * @param query
     * @param filterType
     * @return
     */
    public List<User> searchUsers(String query, String filterType) {
        List<User> allUsers = getAllUsers();
        List<User> matchedUsers = new ArrayList<>();

        for (User user : allUsers) {
            if (!"ROLE_USER".equals(user.getRole())) {
                continue; // Skip users who do not have ROLE_USER as their role
            }

            if ("name".equals(filterType) && user.getUsername().contains(query)) {
                matchedUsers.add(user);
            } else if ("email".equals(filterType) && user.getEmail().contains(query)) {
                matchedUsers.add(user);
            }
        }

        return matchedUsers;
    }
    
    /**
     * Finds if a user exists with given ID
     * @param id
     * @return
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
}
