package org.springboot.rbacsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.ApiResponseDto;
import org.springboot.rbacsystem.dto.LoginRequestDto;
import org.springboot.rbacsystem.dto.LoginResponseDto;
import org.springboot.rbacsystem.dto.RegisterRequestDto;
import org.springboot.rbacsystem.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/{version}/auth", version = "v1")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService service;
	
	@PostMapping("/login")
	public ApiResponseDto<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
		return success(service.login(dto), "Login successful");
	}
	
	@PostMapping("/register")
	public ApiResponseDto<String> register(@Valid @RequestBody RegisterRequestDto dto) {
		service.register(dto);
		return success(null, "Register successful");
	}
	
	private <T> ApiResponseDto<T> success(T data, String message) {
		return ApiResponseDto.<T>builder()
		                     .status(200)
		                     .message(message)
		                     .data(data)
		                     .timestamp(LocalDateTime.now())
		                     .build();
	}
}