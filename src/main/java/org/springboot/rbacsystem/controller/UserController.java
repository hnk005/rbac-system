package org.springboot.rbacsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.CreateUserDto;
import org.springboot.rbacsystem.dto.UpdateUserDto;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService service;
	
	@GetMapping
	public List<UserDto> getAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public UserDto getIds(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	public String create(@Valid @RequestBody CreateUserDto dto) {
		return service.create(dto);
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @RequestBody UpdateUserDto dto) {
		return service.update(id, dto);
	}
	
	@DeleteMapping
	public String delete(@RequestParam List<Long> ids) {
		return service.delete(ids);
	}
}
