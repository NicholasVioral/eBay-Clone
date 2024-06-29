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

public class Paypal_FormTest {
    private static Validator validator;

    @BeforeAll
    static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validatePaypalForm_validDetails() {
        Paypal_Form form = new Paypal_Form();
        form.setPaypalLogin("user@example.com");
        form.setPaypalPassword("securepassword123");

        Set<ConstraintViolation<Paypal_Form>> violations = validator.validate(form);
        assertTrue(violations.isEmpty(), "No violations should be present for valid inputs");
    }

    @Test
    void validatePaypalForm_emptyLogin() {
        Paypal_Form form = new Paypal_Form();
        form.setPaypalLogin("");
        form.setPaypalPassword("securepassword123");

        Set<ConstraintViolation<Paypal_Form>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "Violations should be present for empty login");
        assertEquals(1, violations.size());
        assertEquals("Paypal login cannot be empty.", violations.iterator().next().getMessage());
    }

    @Test
    void validatePaypalForm_emptyPassword() {
        Paypal_Form form = new Paypal_Form();
        form.setPaypalLogin("user@example.com");
        form.setPaypalPassword("");

        Set<ConstraintViolation<Paypal_Form>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "Violations should be present for empty password");
        assertEquals(1, violations.size());
        assertEquals("Paypal password cannot be empty.", violations.iterator().next().getMessage());
    }
}
