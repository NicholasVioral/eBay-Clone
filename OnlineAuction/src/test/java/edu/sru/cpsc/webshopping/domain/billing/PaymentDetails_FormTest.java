package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PaymentDetails_FormTest {
    private static Validator validator;

    @BeforeAll
    static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validatePaymentDetailsForm_validDetails() {
        PaymentDetails_Form form = new PaymentDetails_Form();
        form.setCardType("Visa");
        form.setCardholderName("John Doe");
        form.setCardNumber("1234567890123456");
        form.setExpirationDate("12/2024");
        form.setSecurityCode("123");
        form.setBillingAddress(1); 

        Set<ConstraintViolation<PaymentDetails_Form>> violations = validator.validate(form);
        assertTrue(violations.isEmpty(), "No violations should be present for valid inputs");
    }

    @Test
    void validatePaymentDetailsForm_invalidSecurityCode() {
        PaymentDetails_Form form = new PaymentDetails_Form();
        form.setCardType("MasterCard");
        form.setCardholderName("Jane Smith");
        form.setCardNumber("9876543210987654");
        form.setExpirationDate("11/2023");
        form.setSecurityCode("12"); // Invalid length

        Set<ConstraintViolation<PaymentDetails_Form>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "Violations should be present for invalid security code");
        assertEquals(1, violations.size(), "Exactly one violation for security code");
        assertEquals("Security Code must be between 3 and 4 characters.", violations.iterator().next().getMessage());
    }

    @Test
    void validatePaymentDetailsForm_invalidBillingAddress() {
        PaymentDetails_Form form = new PaymentDetails_Form();
        form.setCardType("Visa");
        form.setCardholderName("Alice Wonderland");
        form.setCardNumber("1234567890123456");
        form.setExpirationDate("01/2025");
        form.setSecurityCode("1234");
        form.setBillingAddress(0); 


        Set<ConstraintViolation<PaymentDetails_Form>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());    
        }
}
