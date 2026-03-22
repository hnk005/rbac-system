package org.springboot.rbacsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.JwtResponseDto;
import org.springboot.rbacsystem.dto.LoginDto;
import org.springboot.rbacsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/{version}/auth", version = "v1")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService service;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponseDto> authenticateUser(@RequestBody LoginDto dto) {
		return ResponseEntity.ok(
				service.login(dto)
		);
	}
}