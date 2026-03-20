package org.springboot.rbacsystem.service;

import lombok.RequiredArgsConstructor;
import org.springboot.rbacsystem.dto.CreateRoleDto;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.dto.UpdateRoleDto;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.mapper.permission.PermissionMapper;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springboot.rbacsystem.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository repository;
	private final RoleMapper mapper;
	private final PermissionService permissionService;
	private final PermissionMapper permissionMapper;
	
	public List<RoleDto> findAllById(List<Long> ids) {
		
		List<RoleEntity> roleEntities = repository.findAllById(ids);
		
		return roleEntities.stream()
		                   .map(mapper::toDto)
		                   .collect(Collectors.toList());
	}
	
	public List<RoleDto> findAll() {
		List<RoleEntity> roleEntities = repository.findAll();
		
		return roleEntities.stream()
		                   .map(role -> {
			                   List<PermissionDto> permissionDtos = role.getPermissions()
			                                                            .stream()
			                                                            .map(permissionMapper::toDto)
			                                                            .toList();
			                   RoleDto roleDto = mapper.toDto(role);
			                   roleDto.setPermissions(permissionDtos);
			                   return roleDto;
		                   })
		                   .toList();
	}
	
	public RoleDto findById(Long id) {
		RoleEntity roleEntity = repository.findById(id)
		                                  .orElseThrow(() -> new RuntimeException("Role not found"));
		
		List<PermissionDto> permissionDtos = roleEntity.getPermissions()
		                                               .stream()
		                                               .map(permissionMapper::toDto)
		                                               .toList();
		
		RoleDto roleDto = mapper.toDto(roleEntity);
		roleDto.setPermissions(permissionDtos);
		
		return roleDto;
	}
	
	public String create(CreateRoleDto dto) {
		RoleEntity roleEntity = repository.findByName(dto.getName());
		
		if (roleEntity != null) {
			throw new IllegalArgumentException("Role with name '" + dto.getName() + "' already exists");
		}
		
		roleEntity = new RoleEntity();
		roleEntity.setName(dto.getName());
		roleEntity.setDes(dto.getDes());
		
		if (dto.getPermissionIds() != null && !dto.getPermissionIds()
		                                          .isEmpty()) {
			List<Long> permissionIds = dto.getPermissionIds();
			List<PermissionDto> permissions = permissionService.findAllById(permissionIds);
			
			if (permissions.isEmpty() || permissions.size() != permissionIds.size()) {
				throw new IllegalArgumentException("No valid permissions found for the provided role IDs");
			}
			
			roleEntity.addPermissions(permissions.stream()
			                                     .map(permissionMapper::toEntity)
			                                     .collect(Collectors.toSet()));
		}
		
		repository.save(roleEntity);
		return "Success";
	}
	
	public String update(Long id, UpdateRoleDto dto) {
		final RoleEntity roleEntity = repository.findById(id)
		                                        .orElseThrow(() -> new IllegalArgumentException("Role not found with" +
				                                        " " +
				                                        "id: " + id));
		
		if (dto.getName() != null && !dto.getName()
		                                 .isBlank()) {
			roleEntity.setName(dto.getName());
		}
		
		if (dto.getDes() != null) {
			roleEntity.setDes(dto.getDes());
		}
		
		if (dto.getPermissionIds() != null) {
			List<Long> permissionIds = dto.getPermissionIds();
			
			List<PermissionDto> permissions = permissionService.findAllById(permissionIds);
			
			if (permissions.size() < permissionIds.size()) {
				throw new IllegalArgumentException("No valid permissions found for the provided role IDs");
			}
			
			roleEntity.removePermissions(roleEntity.getPermissions()
			                                       .stream()
			                                       .toList());
			roleEntity.addPermissions(permissions.stream()
			                                     .map(permissionMapper::toEntity)
			                                     .collect(Collectors.toSet()));
		}
		
		repository.save(roleEntity);
		return "Success";
	}
	
	public String delete(List<Long> ids) {
		List<RoleEntity> roleEntities = repository.findAllById(ids);
		
		if (roleEntities.isEmpty()) {
			throw new IllegalArgumentException("No valid roles found for the provided IDs");
		}
		
		List<RoleEntity> roleEntities1 = roleEntities.stream()
		                                             .peek(roleEntity -> {
			                                             roleEntity.removeUsers(roleEntity.getUsers());
		                                             })
		                                             .toList();
		
		repository.deleteAll(roleEntities1);
		return "Success";
	}
}
