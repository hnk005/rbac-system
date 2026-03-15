package org.springboot.rbacsystem.mapper.role;

import org.mapstruct.Mapper;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.Role;

@Mapper
public interface RoleMapper {
	RoleDto toDto(Role roleEntity);
	
	Role toEntity(RoleDto roleDto);
}

