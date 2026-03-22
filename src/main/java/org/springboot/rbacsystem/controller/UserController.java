package org.springboot.rbacsystem.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.dto.ApiResponseDto;
import org.springboot.rbacsystem.dto.CreateUserDto;
import org.springboot.rbacsystem.dto.UpdateUserDto;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springboot.rbacsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springboot.rbacsystem.util.ApiResponseUtils.success;

@RestController
@RequestMapping(value = "/{version}/users", version = "v1")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService service;
	
	@GetMapping
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.READ)
	public ApiResponseDto<List<UserDto>> getAll() {
		return success(service.findAll(), "Users retrieved successfully");
	}
	
	@GetMapping("/{id}")
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.READ)
	public ApiResponseDto<UserDto> getIds(@PathVariable Long id) {
		return success(service.findById(id), "User retrieved successfully");
	}
	
	@GetMapping("/me")
	@RequirePermission(owner = ResourceOwner.USER, action = Action.READ)
	public ApiResponseDto<UserDto> getCurrentUser() {
		return success(service.findByCurrentUser(), "Current user retrieved successfully");
	}
	
	@PostMapping
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.CREATE)
	public ApiResponseDto<Void> create(@Valid @RequestBody CreateUserDto dto) {
		service.create(dto);
		return success(null, "User created successfully");
	}
	
	@PutMapping("/{id}")
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.UPDATE)
	public ApiResponseDto<Void> update(@PathVariable Long id, @Valid @RequestBody UpdateUserDto dto) {
		service.update(id, dto);
		return success(null, "User updated successfully");
	}
	
	@DeleteMapping
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.DELETE)
	public ApiResponseDto<Void> delete(@RequestParam List<Long> ids) {
		service.delete(ids);
		return success(null, "User deleted successfully");
	}
	
}
