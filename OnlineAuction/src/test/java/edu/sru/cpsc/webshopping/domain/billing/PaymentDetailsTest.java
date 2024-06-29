package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.user.User;

@SpringBootTest(classes = {PaymentDetailsTest.class})
public class PaymentDetailsTest {

    private PaymentDetails paymentDetails;

    @BeforeEach
    public void setUp() {
        paymentDetails = new PaymentDetails();
    }

    @Test
    public void testGettersAndSetters() {
        paymentDetails.setId(1L);
        assertEquals(1L, paymentDetails.getId());

        paymentDetails.setCardType("Visa");
        assertEquals("Visa", paymentDetails.getCardType());

        paymentDetails.setCardholderName("John Doe");
        assertEquals("John Doe", paymentDetails.getCardholderName());

        paymentDetails.setCardNumber("1234567890123456");
        assertEquals("1234567890123456", paymentDetails.getCardNumber());

        paymentDetails.setLast4Digits("3456");
        assertEquals("3456", paymentDetails.getLast4Digits());

        paymentDetails.setExpirationDate("12/23");
        assertEquals("12/23", paymentDetails.getExpirationDate());

        paymentDetails.setSecurityCode("123");
        assertEquals("123", paymentDetails.getSecurityCode());

        ShippingAddress billingAddress = new ShippingAddress();
        paymentDetails.setBillingAddress(billingAddress);
        assertEquals(billingAddress, paymentDetails.getBillingAddress());

        User user = new User();
        paymentDetails.setUser(user);
        assertEquals(user, paymentDetails.getUser());
    }

    @Test
    public void testTransferFields() {
        PaymentDetails other = new PaymentDetails();
        other.setCardType("Mastercard");
        other.setCardholderName("Jane Doe");
        other.setCardNumber("9876543210987654");
        other.setLast4Digits("7654");
        other.setExpirationDate("01/24");
        other.setSecurityCode("456");
        ShippingAddress billingAddress = new ShippingAddress();
        other.setBillingAddress(billingAddress);

        paymentDetails.transferFields(other);

        assertEquals("Mastercard", paymentDetails.getCardType());
        assertEquals("Jane Doe", paymentDetails.getCardholderName());
        assertEquals("9876543210987654", paymentDetails.getCardNumber());
        assertEquals("7654", paymentDetails.getLast4Digits());
        assertEquals("01/24", paymentDetails.getExpirationDate());
        assertEquals("456", paymentDetails.getSecurityCode());
        assertEquals(billingAddress, paymentDetails.getBillingAddress());
    }

    @Test
    public void testBuildFromForm() {
        PaymentDetails_Form form = new PaymentDetails_Form();
        form.setCardType("Discover");
        form.setCardholderName("Bob Smith");
        form.setCardNumber("1111222233334444");
        form.setExpirationDate("05/25");
        form.setSecurityCode("789");

        ShippingAddress billingAddress = new ShippingAddress();

        paymentDetails.buildFromForm(form, billingAddress);

        assertEquals("Discover", paymentDetails.getCardType());
        assertEquals("Bob Smith", paymentDetails.getCardholderName());
        assertEquals("1111222233334444", paymentDetails.getCardNumber());
        assertEquals("4444", paymentDetails.getLast4Digits());
        assertEquals("05/25", paymentDetails.getExpirationDate());
        assertEquals("789", paymentDetails.getSecurityCode());
        assertEquals(billingAddress, paymentDetails.getBillingAddress());
    }

    @Test
    public void testIdIsNull() {
        assertNotNull(paymentDetails.getId());
    }
}