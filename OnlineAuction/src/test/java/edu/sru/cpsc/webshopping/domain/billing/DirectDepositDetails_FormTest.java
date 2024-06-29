package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DirectDepositDetails_FormTest.class})
public class DirectDepositDetails_FormTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidDirectDepositDetails_Form() {
        DirectDepositDetails_Form form = new DirectDepositDetails_Form();
        form.setAccountholderName("John Doe");
        form.setRoutingNumber("123456789");
        form.setAccountNumber("12345678901234567");
        form.setBankName("Bank of America");
        form.setStreetAddress("123 Main St");
        form.setCity("Anytown");
        form.setPostalCode("12345");
        form.setState(new StateDetails()); // Assume this is properly initialized

        assertEquals(0, validator.validate(form).size());
    }

    @Test
    public void testInvalidDirectDepositDetails_Form() {
        DirectDepositDetails_Form form = new DirectDepositDetails_Form();
        form.setAccountholderName(""); // Invalid: empty
        form.setRoutingNumber("12345678"); // Invalid: length < 9
        form.setAccountNumber("123456789012345678"); // Invalid: length > 17
        form.setBankName(""); // Invalid: empty
        form.setStreetAddress(""); // Invalid: empty
        form.setCity(""); // Invalid: empty
        form.setPostalCode("123"); // Invalid: length != 5

        assertEquals(7, validator.validate(form).size());
    }
}
