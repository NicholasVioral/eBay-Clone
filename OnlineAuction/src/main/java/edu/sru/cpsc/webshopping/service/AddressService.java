package edu.sru.cpsc.webshopping.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shippo.Shippo;
import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.shippo.model.Address;

import edu.sru.cpsc.webshopping.domain.billing.BankAddress;
import edu.sru.cpsc.webshopping.domain.billing.BankAddress_Form;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress_Form;
import edu.sru.cpsc.webshopping.repository.billing.BankAddressRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Service for handling Addresses
 */
@Service
public class AddressService {

    private final String shippoApiKey; // Retrieve this from a secure source

    @Autowired
    private BankAddressRepository bankAddressRepository;
    @PersistenceContext
	private EntityManager entityManager;

    @Autowired
    public AddressService(@Value("${shippo.api.key}") String apiKey) {
        this.shippoApiKey = apiKey;
        Shippo.setApiKey(shippoApiKey);
    }
    
    /**
     * Checks if given address is real
     * @param shipping
     * @return
     */
    public boolean addressExists(ShippingAddress_Form shipping) {

        Shippo.setApiKey(shippoApiKey);

        HashMap<String, Object> addressMap = new HashMap<String, Object>();
        addressMap.put("name", shipping.getRecipient());
        addressMap.put("street1", shipping.getStreetAddress());
        addressMap.put("city", shipping.getCity());
        addressMap.put("state", shipping.getState().getStateName());
        addressMap.put("zip", shipping.getPostalCode());
        addressMap.put("country", "US");
        addressMap.put("validate", "true");

        try {
            Address shippoAddress = Address.create(addressMap);
            boolean isValid = shippoAddress.getValidationResults().getIsValid();
            if (!isValid) {
                System.out.println("Address: " + shippoAddress.toString() + " is invalid.");
                System.out.println("Address is invalid because: ");
                for (Address.ValidationMessage message : shippoAddress.getValidationResults().getValidationMessages()) {
                    System.out.println(message.getText());
                }
            }
            return isValid;

        } catch (InvalidRequestException | AuthenticationException | APIConnectionException | APIException e) {
            e.printStackTrace();
            return false;
        }
	}

    /**
     * Checks if given address is real
     * @param shipping
     * @return
     */
    public boolean addressExists(BankAddress_Form shipping) {

        Shippo.setApiKey(shippoApiKey);

        HashMap<String, Object> addressMap = new HashMap<String, Object>();
        addressMap.put("name", shipping.getBankName());
        addressMap.put("street1", shipping.getStreetAddress());
        addressMap.put("city", shipping.getCity());
        addressMap.put("state", shipping.getState().getStateName());
        addressMap.put("zip", shipping.getPostalCode());
        addressMap.put("country", "US");
        addressMap.put("validate", "true");

        try {
            Address shippoAddress = Address.create(addressMap);
            boolean isValid = shippoAddress.getValidationResults().getIsValid();
            if (!isValid) {
                System.out.println("Address: " + shippoAddress.toString() + " is invalid.");
                System.out.println("Address is invalid because: ");
                for (Address.ValidationMessage message : shippoAddress.getValidationResults().getValidationMessages()) {
                    System.out.println(message.getText());
                }
            }
            return isValid;

        } catch (InvalidRequestException | AuthenticationException | APIConnectionException | APIException e) {
            e.printStackTrace();
            return false;
        }
	}
    
    /**
     * Saves bank address to the database
     * @param bankAddress
     * @return
     */
    public BankAddress addBankAddress(BankAddress bankAddress) {
        bankAddressRepository.save(bankAddress);
	    entityManager.persist(bankAddress);
        return bankAddress;
    }
    
}
