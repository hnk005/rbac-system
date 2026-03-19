package org.springboot.rbacsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.CreateUserDto;
import org.springboot.rbacsystem.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService service;
	
	@PostMapping()
	public String create(@Valid @RequestBody CreateUserDto dto) {
		return service.create(dto);
	}
}
