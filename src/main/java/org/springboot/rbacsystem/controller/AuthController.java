package org.springboot.rbacsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.LoginRequestDto;
import org.springboot.rbacsystem.dto.LoginResponseDto;
import org.springboot.rbacsystem.dto.RegisterRequestDto;
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
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
		return ResponseEntity.ok(
				service.login(dto)
		);
	}
	
	@PostMapping("/register")
	public String register(@RequestBody RegisterRequestDto dto) {
		return service.register(dto);
	}
}