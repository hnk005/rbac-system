package org.springboot.rbacsystem.mapper.role;

import org.mapstruct.Mapper;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.mapper.permission.PermissionMapper;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {
	RoleDto toDto(RoleEntity roleEntity);
	
	RoleEntity toEntity(RoleDto roleDto);
}

