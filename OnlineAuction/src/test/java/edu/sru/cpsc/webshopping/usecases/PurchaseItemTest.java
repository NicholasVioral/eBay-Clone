package edu.sru.cpsc.webshopping.usecases;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import edu.sru.cpsc.webshopping.controller.ShippingAddressDomainController;
import edu.sru.cpsc.webshopping.controller.billing.PaymentDetailsController;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails_Form;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseItemTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ShippingAddressDomainController shippingAddressController;

    @MockBean
    private PaymentDetailsController payDetController;

    @Test
    @WithMockUser(username = "testUser")
    public void testSubmitPurchase() throws Exception {
        // Arrange
        PaymentDetails_Form paymentDetails = new PaymentDetails_Form();

        User user = new User(); // Mock user
        when(userService.getUserByUsername("testUser")).thenReturn(user);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/confirmPurchase/submitPurchase")
                .param("submit", "value")
                .flashAttr("paymentDetails", paymentDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/confirmPurchase"));

    }

}
