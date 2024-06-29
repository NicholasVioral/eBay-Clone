package edu.sru.cpsc.webshopping.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import edu.sru.cpsc.webshopping.domain.user.Applicant;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.applicant.ApplicantRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.service.UserService;

class IndexControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserController userController;

    @Mock
    private ApplicantRepository appRepo;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserService userService;

    private IndexController indexController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        indexController = new IndexController();
    }

//    @Test
//    void testShowUserList() {
//        // Arrange
//        List<User> users = new ArrayList<>();
//        users.add(new User());
//        users.add(new User());
//		User singleUser = new User();
//        when(userRepository.findAll()).thenReturn(users);
//        Principal principal = mock(Principal.class);
//        when(principal.getName()).thenReturn("testuser");
//        User user = new User();
//        user.setUsername("testuser");
//        when(userService.getUserByUsername("testuser")).thenReturn(user);
//
//        // Act
//        String viewName = indexController.showUserList(model);
//
//        // Assert
//        assertEquals("index", viewName);
//        verify(model, times(1)).addAttribute("users", users);
//        verify(model, times(1)).addAttribute("user", singleUser);
//    }

//    @Test
//    void testShowIndex() {
//        // Arrange
//		Principal principal = mock(Principal.class);
//        when(principal.getName()).thenReturn("testuser");
//        User user = new User();
//        user.setUsername("testuser");
//        when(userService.getUserByUsername("testuser")).thenReturn(user);
//
//        // Act
//        String viewName = indexController.showIndex(model);
//
//        // Assert
//        assertEquals("index", viewName);
//        verify(model, times(1)).addAttribute("user", user);
//    }

    @Test
    void testShowLoginPage() {
        // Act
        String viewName = indexController.showLoginPage();

        // Assert
        assertEquals("login", viewName);
    }

    @Test
    void testShowMission() {
        // Act
        String viewName = indexController.showMission();

        // Assert
        assertEquals("missionStatement", viewName);
    }

    @Test
    void testShowFAQ() {
        // Act
        String viewName = indexController.showFAQ();

        // Assert
        assertEquals("FAQ", viewName);
    }

    @Test
    void testShowApplication() {
        // Act
        String viewName = indexController.showApplication(model);

        // Assert
        assertEquals("application", viewName);
        verify(model, times(1)).addAttribute(eq("applicant"), any(Applicant.class));
    }

    @Test
    void testAddApplicationWithErrors() {
        // Arrange
        Applicant applicant = new Applicant();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = indexController.addApplication(applicant, bindingResult, model);

        // Assert
        assertEquals("application", viewName);
    }

    @Test
    void testAddApplicationWithoutErrors() {
        // Arrange
        Applicant applicant = new Applicant();
        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        String viewName = indexController.addApplication(applicant, bindingResult, model);

        // Assert
        assertEquals("redirect:index", viewName);
        verify(appRepo, times(1)).save(applicant);
    }
}