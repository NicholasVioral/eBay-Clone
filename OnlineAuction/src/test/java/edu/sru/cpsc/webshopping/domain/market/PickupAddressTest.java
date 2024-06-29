package edu.sru.cpsc.webshopping.domain.market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.billing.StateDetails;

@SpringBootTest(classes = {PickupAddressTest.class})
public class PickupAddressTest {

    @Test
    public void testPickupAddress() {
        PickupAddress address = new PickupAddress();
        assertNotNull(address);
    }

    @Test
    public void testSetAndGetStreetAddress() {
        String streetAddress = "123 Main St";
        PickupAddress address = new PickupAddress();
        address.setStreetAddress(streetAddress);
        assertEquals(streetAddress, address.getStreetAddress());
    }

    @Test
    public void testSetAndGetExtraLocationInfo() {
        String extraLocationInfo = "Apt 2B";
        PickupAddress address = new PickupAddress();
        address.setExtraLocationInfo(extraLocationInfo);
        assertEquals(extraLocationInfo, address.getExtraLocationInfo());
    }

    @Test
    public void testSetAndGetPostalCode() {
        String postalCode = "12345";
        PickupAddress address = new PickupAddress();
        address.setPostalCode(postalCode);
        assertEquals(postalCode, address.getPostalCode());
    }

    @Test
    public void testSetAndGetCity() {
        String city = "New York";
        PickupAddress address = new PickupAddress();
        address.setCity(city);
        assertEquals(city, address.getCity());
    }

    @Test
    public void testSetAndGetState() {
        StateDetails state = new StateDetails();
        PickupAddress address = new PickupAddress();
        address.setState(state);
        assertEquals(state, address.getState());
    }
}