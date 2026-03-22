package org.springboot.rbacsystem.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.constrant.Action;
import org.springboot.rbacsystem.constrant.ResourceOwner;
import org.springboot.rbacsystem.dto.ApiResponseDto;
import org.springboot.rbacsystem.dto.SystemApiResponseDto;
import org.springboot.rbacsystem.dto.SystemApiSearchRequestDto;
import org.springboot.rbacsystem.security.RequirePermission;
import org.springboot.rbacsystem.service.SystemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springboot.rbacsystem.util.ApiResponseUtils.success;

@RestController
@RequestMapping(value = "/{version}/system", version = "v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class SystemController {
	
	private final SystemService service;
	
	@GetMapping("/apis")
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.READ)
	public ApiResponseDto<List<SystemApiResponseDto>> scanAllPermissions() {
		return success(service.getApiAll(), "APIs retrieved successfully");
	}
	
	@GetMapping("/apis/search")
	@RequirePermission(owner = ResourceOwner.SYS, action = Action.READ)
	public ApiResponseDto<List<SystemApiResponseDto>> scanPermissionsByMethod(@ModelAttribute SystemApiSearchRequestDto dto) {
		return success(service.searchApi(dto), "APIs retrieved successfully");
		
	}
	
}
