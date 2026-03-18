package org.springboot.rbacsystem.service;

import lombok.AllArgsConstructor;
import org.springboot.rbacsystem.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final UserService userService;
	
	public String register(UserDto userDto) {
		try {
			userService.create(userDto);
			return "Registration successful";
		} catch (Exception e) {
			return "Registration failed: " + e.getMessage();
		}
	}
}
