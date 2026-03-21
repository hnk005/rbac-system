package org.springboot.rbacsystem.config;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BCryptPasswordTest {
	
	private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@Test
	public void testBCryptPassword() {
		String rawPassword = "password123";
		String encodedPassword = encoder.encode(rawPassword);
		
		assertTrue(encoder.matches(rawPassword, encodedPassword));
	}
}
