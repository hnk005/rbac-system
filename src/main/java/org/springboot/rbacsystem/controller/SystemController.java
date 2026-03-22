package org.springboot.rbacsystem.controller;


import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.dto.ApiRequestDto;
import org.springboot.rbacsystem.dto.ApiResponseDto;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springboot.rbacsystem.service.SystemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/{version}/system", version = "1.0")
@RequiredArgsConstructor
public class SystemController {
	
	private final SystemService service;
	
	@GetMapping("/apis")
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.READ)
	public List<ApiResponseDto> scanAllPermissions() {
		return service.getApiAll();
	}
	
	@GetMapping("/apis/search")
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.READ)
	public List<ApiResponseDto> scanPermissionsByMethod(@ModelAttribute ApiRequestDto dto) {
		return service.searchApi(dto);
	}
}
