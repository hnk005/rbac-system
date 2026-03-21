package org.springboot.rbacsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.JwtResponseDto;
import org.springboot.rbacsystem.dto.LoginDto;
import org.springboot.rbacsystem.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/{version}/auth", version = "1.0")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponseDto> authenticateUser(@RequestBody LoginDto dto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						dto.getUsername(),
						dto.getPassword()
				)
		);
		
		SecurityContextHolder.getContext()
		                     .setAuthentication(authentication);
		
		JwtResponseDto jwtResponseDto = new JwtResponseDto(
				jwtUtils.generateJwtToken(authentication),
				dto.getUsername()
		);
		
		
		return ResponseEntity.ok(jwtResponseDto);
	}
}