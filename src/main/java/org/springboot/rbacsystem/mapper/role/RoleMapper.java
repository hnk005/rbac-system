package org.springboot.rbacsystem.mapper.role;

import org.mapstruct.Mapper;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.RoleEntity;

@Mapper
public interface RoleMapper {
	RoleDto toDto(RoleEntity roleEntity);
	
	RoleEntity toEntity(RoleDto roleDto);
}

