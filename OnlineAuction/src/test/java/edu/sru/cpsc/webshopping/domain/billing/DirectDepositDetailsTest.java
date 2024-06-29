package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import edu.sru.cpsc.webshopping.domain.user.User;

@SpringBootTest(classes = {DirectDepositDetailsTest.class})
public class DirectDepositDetailsTest {

    private DirectDepositDetails directDepositDetails;
    private User user;
    private BankAddress bankAddress;

    @BeforeEach
    public void setUp() {
        user = new User(); // Assume a proper User constructor or setup method
        bankAddress = new BankAddress(); // Assume a proper BankAddress constructor or setup method
        directDepositDetails = new DirectDepositDetails(user);
        directDepositDetails.setBankAddress(bankAddress);
    }

    @Test
    public void testInitialization() {
        assertNotNull(directDepositDetails.getUser());
        assertEquals(user, directDepositDetails.getUser());
        assertNotNull(directDepositDetails.getBankAddress());
    }

    @Test
    public void testSetAndGetDetails() {
        directDepositDetails.setAccountholderName("John Doe");
        directDepositDetails.setRoutingNumber("123456789");
        directDepositDetails.setAccountNumber("987654321");
        directDepositDetails.setBankName("Bank of America");

        assertEquals("John Doe", directDepositDetails.getAccountholderName());
        assertEquals("123456789", directDepositDetails.getRoutingNumber());
        assertEquals("987654321", directDepositDetails.getAccountNumber());
        assertEquals("Bank of America", directDepositDetails.getBankName());
    }

    @Test
    public void testTransferFields() {
        DirectDepositDetails other = new DirectDepositDetails(new User());
        other.setAccountholderName("Jane Doe");
        other.setRoutingNumber("111000025");
        other.setAccountNumber("123456789");
        other.setBankName("Wells Fargo");
        other.setBankAddress(new BankAddress());
        other.setUser(new User());

        directDepositDetails.transferFields(other);

        assertEquals("Jane Doe", directDepositDetails.getAccountholderName());
        assertEquals("111000025", directDepositDetails.getRoutingNumber());
        assertEquals("123456789", directDepositDetails.getAccountNumber());
        assertEquals("Wells Fargo", directDepositDetails.getBankName());
        assertEquals(other.getBankAddress(), directDepositDetails.getBankAddress());
        assertEquals(other.getUser(), directDepositDetails.getUser());
    }

    @Test
    public void testBuildFromForm() {
        DirectDepositDetails_Form form = new DirectDepositDetails_Form();
        form.setAccountholderName("Jane Doe");
        form.setRoutingNumber("111000025");
        form.setAccountNumber("123456789");
        form.setBankName("Wells Fargo");
        form.setStreetAddress("123 Main St");
        form.setCity("Anytown");
        form.setPostalCode("54321");
        form.setState(new StateDetails()); // Properly initialized StateDetails assumed

        directDepositDetails.buildFromForm(form);

        assertEquals("Jane Doe", directDepositDetails.getAccountholderName());
        assertEquals("111000025", directDepositDetails.getRoutingNumber());
        assertEquals("123456789", directDepositDetails.getAccountNumber());
        assertEquals("Wells Fargo", directDepositDetails.getBankName());
        assertEquals("123 Main St", directDepositDetails.getBankAddress().getStreetAddress());
        assertEquals("Anytown", directDepositDetails.getBankAddress().getCity());
        assertEquals("54321", directDepositDetails.getBankAddress().getPostalCode());
        assertEquals(form.getState(), directDepositDetails.getBankAddress().getState());
    }
}
