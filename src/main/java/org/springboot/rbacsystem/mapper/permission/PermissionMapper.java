package org.springboot.rbacsystem.mapper.permission;

import org.mapstruct.Mapper;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.entity.PermissionEntity;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
	PermissionDto toDto(PermissionEntity permissionEntity);
	
	PermissionEntity toEntity(PermissionDto permissionDto);
}

