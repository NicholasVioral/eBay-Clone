package edu.sru.cpsc.webshopping.controller.billing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.repository.billing.PaymentDetailsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@SpringBootTest(classes = {PaymentDetailsController.class})
public class PaymentDetailsControllerTest {
	
	@MockBean
	private PaymentDetailsRepository paymentDetailsRepository;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private EntityManager entityManager;
	
	@MockBean
	private EntityManagerFactory EMF;
	
	@Autowired
	PaymentDetailsController controller;
	
	/*@Before
	public void setUp()
	{
		details.setCardholderName("tyler");
		details.setId(0L);
		when(paymentDetailsRepository.findById(0L).orElseThrow(() -> new IllegalArgumentException("Invalid ID passed to find a user"))).thenReturn(details);
		when(passwordEncoder.encode(details.getCardholderName())).thenReturn(details.getCardholderName());
		when(passwordEncoder.encode(details.getCardNumber())).thenReturn(details.getCardNumber());
		when((passwordEncoder.encode(details.getExpirationDate()))).thenReturn(details.getExpirationDate());
		when(passwordEncoder.encode(details.getPostalCode())).thenReturn(details.getPostalCode());
		when(passwordEncoder.encode(details.getSecurityCode())).thenReturn(details.getSecurityCode());
	}*/
	
	/*@Test
	public void contextLoads() throws Exception
	{
		mvc.perform(get("/get-all-payment-details"))
		.andExpect(status().isOk());
	}*/

@Test
public void getPaymentDetails() throws Exception
{
	PaymentDetails testDetails = new PaymentDetails();
	testDetails.setCardholderName("tyler");
	testDetails.setId(0L);
	System.out.println(testDetails.getId());
	when(paymentDetailsRepository.findById(testDetails.getId())).thenReturn(Optional.of(testDetails));
	assertThat(controller.getPaymentDetail(0L, null)).isEqualTo(testDetails);
}

/*@Test
@DisplayName("delete Payment Details")
public void deletePaymentDetails() {
	try {
		log.info("Starting Execution");
		EntityManager entityManager = Mockito.mock(EntityManager.class);
		when(entityManager.find(PaymentDetails.class, details.getId())).thenReturn(details);
		
		paymentDetailsController.deletePaymentDetails(details);
		Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error("exception");
		exception.printStackTrace();
		Assertions.assertTrue(false);
	}
}*/


}
