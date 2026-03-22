package org.springboot.rbacsystem.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.dto.ApiResponseDto;
import org.springboot.rbacsystem.dto.CreateRoleDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.dto.UpdateRoleDto;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springboot.rbacsystem.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/{version}/roles", version = "v1")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class RoleController {
	
	private final RoleService service;
	
	@GetMapping
	@RequirePermission(owner = ResourceOwner.ROLE, action = Action.READ)
	public ApiResponseDto<List<RoleDto>> getAll() {
		return success(service.findAll(), "Roles retrieved successfully");
	}
	
	@GetMapping("/{id}")
	@RequirePermission(owner = ResourceOwner.ROLE, action = Action.READ)
	public ApiResponseDto<RoleDto> getIds(@PathVariable Long id) {
		return success(service.findById(id), "Role retrieved successfully");
	}
	
	@PostMapping
	@RequirePermission(owner = ResourceOwner.ROLE, action = Action.CREATE)
	public ApiResponseDto<String> create(@Valid @RequestBody CreateRoleDto dto) {
		return success(service.create(dto), "Role created successfully");
	}
	
	@PutMapping("/{id}")
	@RequirePermission(owner = ResourceOwner.ROLE, action = Action.UPDATE)
	public ApiResponseDto<String> update(@PathVariable Long id, @Valid @RequestBody UpdateRoleDto dto) {
		return success(service.update(id, dto), "Role updated successfully");
	}
	
	@DeleteMapping
	@RequirePermission(owner = ResourceOwner.ROLE, action = Action.DELETE)
	public ApiResponseDto<String> delete(@RequestParam List<Long> ids) {
		return success(service.delete(ids), "Role deleted successfully");
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
