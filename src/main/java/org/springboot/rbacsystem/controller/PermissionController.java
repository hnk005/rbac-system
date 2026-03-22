package org.springboot.rbacsystem.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springboot.rbacsystem.service.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/{version}/permissions", version = "v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {
	
	private final PermissionService service;
	
	@GetMapping
	@RequirePermission(owner = ResourceOwner.ROLE, action = Action.READ)
	public List<PermissionDto> getAll() {
		return service.findAll();
	}
}
