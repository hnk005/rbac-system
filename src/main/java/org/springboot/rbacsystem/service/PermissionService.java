package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springboot.rbacsystem.mapper.permission.PermissionMapper;
import org.springboot.rbacsystem.repository.PermissionRepository;
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
	
	public PermissionDto findById(Long id) {
		PermissionEntity permissionEntity = repository.findById(id)
		                                              .orElseThrow(() -> new RuntimeException("Permission not found " +
				                                              "with id: " + id));
		
		return mapper.toDto(permissionEntity);
	}
	
	public List<PermissionDto> findAllById(List<Long> ids) {
		List<PermissionEntity> roleEntities = repository.findAllById(ids);
		
		return roleEntities.stream()
		                   .map(mapper::toDto)
		                   .collect(Collectors.toList());
		
	}
}

