package org.springboot.rbacsystem.mapper.permission;

import org.mapstruct.Mapper;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springboot.rbacsystem.mapper.role.RoleMapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface PermissionMapper {
	PermissionDto toDto(PermissionEntity permissionEntity);
	
	PermissionEntity toEntity(PermissionDto permissionDto);
}

