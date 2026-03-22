package org.springboot.rbacsystem.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.dto.CreateUserDto;
import org.springboot.rbacsystem.dto.UpdateUserDto;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springboot.rbacsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/{version}/users", version = "v1")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService service;
	
	@GetMapping
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.READ)
	public List<UserDto> getAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.READ)
	public UserDto getIds(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.CREATE)
	public String create(@Valid @RequestBody CreateUserDto dto) {
		return service.create(dto);
	}
	
	@PutMapping("/{id}")
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.UPDATE)
	public String update(@PathVariable Long id, @Valid @RequestBody UpdateUserDto dto) {
		return service.update(id, dto);
	}
	
	@DeleteMapping
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.DELETE)
	public String delete(@RequestParam List<Long> ids) {
		return service.delete(ids);
	}
}
