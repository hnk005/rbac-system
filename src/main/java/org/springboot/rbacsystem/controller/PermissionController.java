package org.springboot.rbacsystem.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.dto.ApiResponseDto;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springboot.rbacsystem.service.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springboot.rbacsystem.util.ApiResponseUtils.success;

@RestController
@RequestMapping(value = "/{version}/permissions", version = "v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {
	
	private final PermissionService service;
	
	@GetMapping
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.READ)
	public ApiResponseDto<List<PermissionDto>> getAll() {
		return success(service.findAll(), "Permissions retrieved successfully");
	}
	
	@GetMapping("/me")
	@RequirePermission(owner = ResourceOwner.USER, action = Action.READ)
	public ApiResponseDto<List<String>> getCurrentUserPermissions() {
		return success(service.getCurrentUserPermissions(), "Current user permissions retrieved successfully");
	}
	
}
