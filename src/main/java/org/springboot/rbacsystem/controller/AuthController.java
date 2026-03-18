package org.springboot.rbacsystem.controller;

import lombok.AllArgsConstructor;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService service;
	
	@GetMapping
	public String welcome() {
		return "Welcome to the RBAC System!";
	}
	
	@PostMapping("/register")
	public String register(@Validated(UserDto.onRegister.class) @RequestBody UserDto userDto) {
		return service.register(userDto);
	}
}
