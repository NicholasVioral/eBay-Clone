package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {PaypalTest.class})
public class PaypalTest {

    @Test
    public void testGettersAndSetters() {
        Paypal paypal = new Paypal();
        paypal.setId(1L);
        paypal.setPaypalLogin("test_login");
        paypal.setPaypalPassword("test_password");

        assertEquals(1L, paypal.getId());
        assertEquals("test_login", paypal.getPaypalLogin());
        assertEquals("test_password", paypal.getPaypalPassword());
    }

    @Test
    public void testTransferFields() {
        Paypal paypal1 = new Paypal();
        paypal1.setPaypalLogin("test_login_1");
        paypal1.setPaypalPassword("test_password_1");

        Paypal paypal2 = new Paypal();
        paypal2.setPaypalLogin("test_login_2");
        paypal2.setPaypalPassword("test_password_2");

        paypal1.transferFields(paypal2);

        assertEquals("test_login_2", paypal1.getPaypalLogin());
        assertEquals("test_password_2", paypal1.getPaypalPassword());
    }

    @Test
    public void testBuildFromForm() {
        Paypal paypal = new Paypal();
        Paypal_Form form = new Paypal_Form();
        form.setPaypalLogin("test_login");
        form.setPaypalPassword("test_password");

        paypal.buildFromForm(form);

        assertEquals("test_login", paypal.getPaypalLogin());
        assertEquals("test_password", paypal.getPaypalPassword());
    }
}