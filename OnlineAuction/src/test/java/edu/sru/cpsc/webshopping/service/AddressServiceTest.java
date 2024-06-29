package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shippo.model.Address;

import edu.sru.cpsc.webshopping.domain.billing.BankAddress_Form;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress_Form;
import edu.sru.cpsc.webshopping.repository.billing.BankAddressRepository;

class AddressServiceTest {
	
	@Mock
	private ShippingAddress_Form shipping;
	
	@Mock
	private BankAddress_Form bank;
	
	@Mock
	private AddressService addressService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    void testAddressExistsWithShippingAddressForm() throws Exception {
    	addressService.addressExists(shipping);
    	verify(addressService).addressExists(shipping);
    }

    @Test
    void testAddressExistsWithBankAddressForm() throws Exception {
    	addressService.addressExists(bank);
    	verify(addressService).addressExists(bank);
    }
}
