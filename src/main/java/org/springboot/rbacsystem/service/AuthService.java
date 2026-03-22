package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.JwtResponseDto;
import org.springboot.rbacsystem.dto.LoginDto;
import org.springboot.rbacsystem.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserService userService;
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;
	
	public JwtResponseDto login(LoginDto dto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						dto.getUsername(),
						dto.getPassword()
				)
		);
		
		SecurityContextHolder.getContext()
		                     .setAuthentication(authentication);
		
		
		return new JwtResponseDto(
				jwtUtils.generateJwtToken(authentication),
				dto.getUsername()
		);
	}
}
