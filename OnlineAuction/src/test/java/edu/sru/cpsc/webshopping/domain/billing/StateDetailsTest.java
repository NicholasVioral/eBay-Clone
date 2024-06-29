package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {StateDetailsTest.class})
public class StateDetailsTest {

    private StateDetails stateDetails;

    @BeforeEach
    public void setUp() {
        stateDetails = new StateDetails();
    }

    @Test
    public void testGetStateName() {
        stateDetails.setStateName("Pennsylvania");
        assertEquals("Pennsylvania", stateDetails.getStateName());
    }

    @Test
    public void testGetSalesTaxRate() {
        stateDetails.setSalesTaxRate(new BigDecimal("6.00"));
        assertEquals(new BigDecimal("6.00"), stateDetails.getSalesTaxRate());
    }

    @Test
    public void testNotNull() {
        assertNotNull(stateDetails);
    }
}