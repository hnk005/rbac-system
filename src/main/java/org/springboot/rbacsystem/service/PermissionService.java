package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springboot.rbacsystem.mapper.permission.PermissionMapper;
import org.springboot.rbacsystem.repository.PermissionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionService {
	
	private final PermissionRepository repository;
	private final PermissionMapper mapper;
	
	public List<PermissionDto> findAll() {
		List<PermissionEntity> permissionEntities = repository.findAll();
		
		return permissionEntities.stream()
		                         .map(mapper::toDto)
		                         .collect(Collectors.toList());
	}
	
	public List<PermissionEntity> findAllById(List<Long> ids) {
		return repository.findAllById(ids);
	}
	
	public List<String> getCurrentUserPermissions() {
		Authentication authentication = SecurityContextHolder.getContext()
		                                                     .getAuthentication();
		
		if (authentication == null) {
			return null;
		}
		
		return authentication.getAuthorities()
		                     .stream()
		                     .map(GrantedAuthority::getAuthority)
		                     .toList();
	}
	
}

