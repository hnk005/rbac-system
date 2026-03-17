package org.springboot.rbacsystem.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PasswordEncoderTest {
	
	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Test
	public void testPasswordEncoder() {
		String rawPassword = "mySecretPassword";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		
		assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
	}
}
