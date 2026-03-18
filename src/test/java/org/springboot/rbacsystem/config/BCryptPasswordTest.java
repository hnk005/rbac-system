package org.springboot.rbacsystem.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BCryptPasswordTest {
	
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(SecurityConfig.strength);
	
	@Test
	public void testBCryptPassword() {
		String rawPassword = "password123";
		String encodedPassword = encoder.encode(rawPassword);
		
		assertTrue(encoder.matches(rawPassword, encodedPassword));
	}
}
