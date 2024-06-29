package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAddressTest {
    private BankAddress bankAddress;
    private StateDetails state;

    @BeforeEach
    void setUp() {
        bankAddress = new BankAddress();
        state = new StateDetails(); // Assume StateDetails is a simple class or mocked if complex
        state.setStateName("Pennsylvania"); // Example field/method for state name
        bankAddress.setState(state);
        bankAddress.setBankName("Bank of Springfield");
        bankAddress.setStreetAddress("123 Elm Street");
        bankAddress.setExtraLocationInfo("Near the old oak tree");
        bankAddress.setPostalCode("12345");
        bankAddress.setCity("Springfield");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("Bank of Springfield", bankAddress.getBankName());
        assertEquals("123 Elm Street", bankAddress.getStreetAddress());
        assertEquals("Near the old oak tree", bankAddress.getExtraLocationInfo());
        assertEquals("12345", bankAddress.getPostalCode());
        assertEquals("Springfield", bankAddress.getCity());
        assertEquals(state, bankAddress.getState());
    }

    @Test
    void testTransferFields() {
        BankAddress newAddress = new BankAddress();
        newAddress.transferFields(bankAddress);

        assertEquals(bankAddress.getBankName(), newAddress.getBankName());
        assertEquals(bankAddress.getStreetAddress(), newAddress.getStreetAddress());
        assertEquals(bankAddress.getExtraLocationInfo(), newAddress.getExtraLocationInfo());
        assertEquals(bankAddress.getPostalCode(), newAddress.getPostalCode());
        assertEquals(bankAddress.getCity(), newAddress.getCity());
        assertEquals(bankAddress.getState(), newAddress.getState());
    }

    @Test
    void testBuildFromForm() {
        BankAddress_Form form = new BankAddress_Form();
        form.setBankName("New Bank of Springfield");
        form.setStreetAddress("456 Pine Street");
        form.setExtraLocationInfo("Opposite the library");
        form.setPostalCode("54321");
        form.setCity("New Springfield");
        StateDetails newState = new StateDetails();
        newState.setStateName("Ohio");
        form.setState(newState);

        assertTrue(bankAddress.buildFromForm(form));

        assertEquals("New Bank of Springfield", bankAddress.getBankName());
        assertEquals("456 Pine Street", bankAddress.getStreetAddress());
        assertEquals("Opposite the library", bankAddress.getExtraLocationInfo());
        assertEquals("54321", bankAddress.getPostalCode());
        assertEquals("New Springfield", bankAddress.getCity());
        assertEquals(newState, bankAddress.getState());
    }
}
