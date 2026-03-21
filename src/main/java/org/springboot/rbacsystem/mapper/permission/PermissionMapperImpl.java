package org.springboot.rbacsystem.mapper.permission;

import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapperImpl implements PermissionMapper {
	
	@Override
	public PermissionDto toDto(PermissionEntity permissionEntity) {
		if (permissionEntity == null) {
			return null;
		}
		
		PermissionDto permissionDto = new PermissionDto();
		permissionDto.setId(permissionEntity.getId());
		permissionDto.setName(permissionEntity.getName());
		permissionDto.setDes(permissionEntity.getDes());
		
		return permissionDto;
	}
	
	@Override
	public PermissionEntity toEntity(PermissionDto permissionDto) {
		if (permissionDto == null) {
			return null;
		}
		
		PermissionEntity permissionEntity = new PermissionEntity();
		permissionEntity.setId(permissionDto.getId());
		permissionEntity.setName(permissionDto.getName());
		permissionEntity.setDes(permissionDto.getDes());
		
		return permissionEntity;
	}
}

