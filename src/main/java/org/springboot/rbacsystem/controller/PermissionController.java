package org.springboot.rbacsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.CreatePermissionDto;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.dto.UpdatePermissionDto;
import org.springboot.rbacsystem.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {
	
	private final PermissionService service;
	
	@GetMapping
	public List<PermissionDto> getAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public PermissionDto getById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	public String create(@Valid @RequestBody CreatePermissionDto dto) {
		return service.create(dto);
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @RequestBody UpdatePermissionDto dto) {
		return service.update(id, dto);
	}
	
	@DeleteMapping
	public String delete(@RequestParam List<Long> ids) {
		return service.delete(ids);
	}
}

