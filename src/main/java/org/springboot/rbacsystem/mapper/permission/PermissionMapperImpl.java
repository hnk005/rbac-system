package org.springboot.rbacsystem.mapper.permission;

import lombok.AllArgsConstructor;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.entity.Permission;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PermissionMapperImpl implements PermissionMapper {
	
	private final RoleMapper roleMapper;
	
	@Override
	public PermissionDto toDto(Permission permissionEntity) {
		if (permissionEntity == null) {
			return null;
		}
		
		PermissionDto permissionDto = new PermissionDto();
		permissionDto.setId(permissionEntity.getId());
		permissionDto.setName(permissionEntity.getName());
		permissionDto.setRoles(permissionEntity.getRoles()
		                                       .stream()
		                                       .map(roleMapper::toDto)
		                                       .toList());
		
		return permissionDto;
	}
	
	@Override
	public Permission toEntity(PermissionDto permissionDto) {
		if (permissionDto == null) {
			return null;
		}
		
		Permission permissionEntity = new Permission();
		permissionEntity.setId(permissionDto.getId());
		permissionEntity.setName(permissionDto.getName());
		
		return permissionEntity;
	}
}

