package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.CreatePermissionDto;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.dto.UpdatePermissionDto;
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
	
	public String create(CreatePermissionDto dto) {
		PermissionEntity existingPermission = repository.findByName(dto.getName());
		
		if (existingPermission != null) {
			throw new IllegalArgumentException("Permission with name '" + dto.getName() + "' already exists");
		}
		
		PermissionEntity permissionEntity = new PermissionEntity();
		permissionEntity.setName(dto.getName());
		permissionEntity.setDes(dto.getDes());
		
		repository.save(permissionEntity);
		return "Success";
	}
	
	public String update(Long id, UpdatePermissionDto dto) {
		PermissionEntity permissionEntity = repository.findById(id)
		                                              .orElseThrow(() -> new IllegalArgumentException("Permission " +
				                                              "not" +
				                                              " " +
				                                              "found with id: " + id));
		
		if (dto.getName() != null && !dto.getName()
		                                 .isBlank()) {
			permissionEntity.setName(dto.getName());
		}
		
		if (dto.getDes() != null) {
			permissionEntity.setDes(dto.getDes());
		}
		
		repository.save(permissionEntity);
		return "Success";
	}
	
	public String delete(List<Long> ids) {
		List<PermissionEntity> permissionEntities = repository.findAllById(ids);
		
		if (permissionEntities.isEmpty()) {
			throw new IllegalArgumentException("No valid permissions found for the provided IDs");
		}
		
		permissionEntities.forEach(permission -> {
			permission.removeRoles(permission.getRoles()
			                                 .stream()
			                                 .toList());
		});
		
		repository.deleteAll(permissionEntities);
		return "Success";
	}
}

