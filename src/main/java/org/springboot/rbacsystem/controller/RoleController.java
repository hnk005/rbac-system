package org.springboot.rbacsystem.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.dto.CreateRoleDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.dto.UpdateRoleDto;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springboot.rbacsystem.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/{version}/roles", version = "v1")
@RequiredArgsConstructor
public class RoleController {
	
	private final RoleService service;
	
	@GetMapping
	public List<RoleDto> getAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public RoleDto getIds(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	@SecurityRequirement(name = "bearerAuth")
	@RequirePermission(owner = ResourceOwner.ROLE, action = Action.CREATE)
	public String create(@Valid @RequestBody CreateRoleDto dto) {
		return service.create(dto);
	}
	
	@PutMapping("/{id}")
	@SecurityRequirement(name = "bearerAuth")
	@RequirePermission(owner = ResourceOwner.ROLE, action = Action.UPDATE)
	public String update(@PathVariable Long id, @Valid @RequestBody UpdateRoleDto dto) {
		return service.update(id, dto);
	}
	
	@DeleteMapping
	@SecurityRequirement(name = "bearerAuth")
	@RequirePermission(owner = ResourceOwner.ROLE, action = Action.DELETE)
	public String delete(@RequestParam List<Long> ids) {
		return service.delete(ids);
	}
}
