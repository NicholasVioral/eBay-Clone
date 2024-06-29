package edu.sru.cpsc.webshopping.controller.billing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.service.UserService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {SellerRatingControllerTest.class})
public class SellerRatingControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionController transactionController;

	@Mock
	private UserService userService;

    @InjectMocks
    private SellerRatingController sellerRatingController; // Use @InjectMocks to inject the mocked dependencies

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks

    }

    @Test
    public void rateUser() {
        Principal principal = mock(Principal.class);
        
        User user = new User();
        user.setId(1L);
		user.setSellerRating(new SellerRating(user));
        user.setUsername("userTest");

//        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Mock the behavior of the repository
//		Mockito.when(userService.getUserById(1L)).thenReturn(user); 
        
        sellerRatingController.rateUser(1L, 5, principal);
        
        verify(userService).rateUser(user.getId(), 5);
//        assertEquals(5, userService.getUserById(1L).getSellerRating().getRating());
    }
}
