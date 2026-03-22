package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.RoleEnum;
import org.springboot.rbacsystem.dto.*;
import org.springboot.rbacsystem.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserService userService;
	private final RoleService roleService;
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;
	
	public LoginResponseDto login(LoginRequestDto dto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						dto.getUsername(),
						dto.getPassword()
				)
		);
		
		SecurityContextHolder.getContext()
		                     .setAuthentication(authentication);
		
		
		return LoginResponseDto.builder()
		                       .token(jwtUtils.generateJwtToken(authentication))
		                       .type("Bearer")
		                       .username(authentication.getName())
		                       .build();
	}
	
	public String register(RegisterRequestDto dto) {
		RoleDto roleDto = roleService.findByName(RoleEnum.USER.getValue());
		
		return userService.create(
				CreateUserDto.builder()
				             .email(dto.getEmail())
				             .username(dto.getUsername())
				             .password(dto.getPassword())
				             .fullName(dto.getFullName())
				             .roleIds(List.of(roleDto.getId()))
				             .build()
		);
	}
}
