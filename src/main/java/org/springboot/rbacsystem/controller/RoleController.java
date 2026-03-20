package org.springboot.rbacsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.CreateRoleDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.dto.UpdateRoleDto;
import org.springboot.rbacsystem.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
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
	public String create(@RequestBody CreateRoleDto dto) {
		return service.create(dto);
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, @RequestBody UpdateRoleDto dto) {
		return service.update(id, dto);
	}
	
	@DeleteMapping
	public String delete(@RequestParam List<Long> ids) {
		return service.delete(ids);
	}
}
