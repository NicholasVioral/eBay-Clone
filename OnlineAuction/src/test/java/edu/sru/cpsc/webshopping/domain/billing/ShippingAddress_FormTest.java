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

public class ShippingAddress_FormTest {
    private static Validator validator;

    @BeforeAll
    static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidShippingAddressForm() {
        ShippingAddress_Form form = new ShippingAddress_Form();
        form.setRecipient("John Doe");
        form.setStreetAddress("1234 Elm Street");
        form.setPostalCode("12345");
        form.setCity("Anytown");
        form.setExtraLocationInfo("Apt 101");

        Set<ConstraintViolation<ShippingAddress_Form>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidShippingAddressForm() {
        ShippingAddress_Form form = new ShippingAddress_Form();
        form.setRecipient("");
        form.setStreetAddress("");
        form.setPostalCode("123");
        form.setCity("");

        Set<ConstraintViolation<ShippingAddress_Form>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());
    }
}
