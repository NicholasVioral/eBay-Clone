package edu.sru.cpsc.webshopping.secure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

@SpringBootTest(classes = {WebshoppingSecureSpringTest.class})
public class WebshoppingSecureSpringTest {
	
	@TestConfiguration
	static class UserDetailsServiceImplTestContextConfiguration {
	
		@Bean
		public UserDetailsService userDetailsService() {
			return new UserDetailsServiceImpl();
		}
	}
	
	@MockBean
	private UserRepository userRepo;
	
	@MockBean
	UserDetailsServiceImpl impl;

	@MockBean
	UserDetails userDetails;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(impl).isNotNull();
	}
	
	@Test
	void UserDetailsServiceImplTest() {
		
		UserDetailsServiceImpl impl = new UserDetailsServiceImpl();

		User userActual = new User();
		userActual.setUsername("userName");

		when(userRepo.findByUsername("userName")).thenReturn(userActual);

		UserDetails user = impl.loadUserByUsername("userName");
		
		assertSame(userActual.getUsername(), user.getUsername());
	}
	
	@Test
	void UserDetailsServiceImplExceptionTest() {
		UserDetailsServiceImpl impl = new UserDetailsServiceImpl();
		
		try {
		UserDetails user = impl.loadUserByUsername("thisUserDoesntExist");
		
		assertEquals("thisUserDoesntExist", user.getUsername());
		}
		catch(Exception e)
		{
			assertTrue(true);
		}
	}
}
