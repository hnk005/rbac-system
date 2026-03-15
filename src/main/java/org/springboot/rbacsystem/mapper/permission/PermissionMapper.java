package org.springboot.rbacsystem.mapper.permission;

import org.mapstruct.Mapper;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.entity.Permission;

@Mapper
public interface PermissionMapper {
	PermissionDto toDto(Permission permissionEntity);
	
	Permission toEntity(PermissionDto permissionDto);
}

