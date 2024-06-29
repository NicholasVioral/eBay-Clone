package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BankAddress_FormTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidBankAddressForm() {
        BankAddress_Form form = new BankAddress_Form();
        form.setBankName("Bank of Springfield");
        form.setStreetAddress("123 Elm Street");
        form.setCity("Springfield");
        form.setPostalCode("12345");
        form.setExtraLocationInfo("Near the old oak tree");
        form.setState(new StateDetails()); // Assuming StateDetails is correctly instantiated

        Set<ConstraintViolation<BankAddress_Form>> violations = validator.validate(form);
        assertTrue(violations.isEmpty(), "Expected no violations, but got some.");
    }

    @Test
    public void testInvalidBankAddressForm() {
        BankAddress_Form form = new BankAddress_Form();
        form.setBankName(""); // Invalid: empty
        form.setStreetAddress(""); // Invalid: empty
        form.setCity(""); // Invalid: empty
        form.setPostalCode("1234"); // Invalid: incorrect length

        Set<ConstraintViolation<BankAddress_Form>> violations = validator.validate(form);
        assertEquals(4, violations.size(), "Expected four violations for empty fields and incorrect postal code length.");
    }

    // Add more tests for boundary cases and other validation rules if necessary.
}
