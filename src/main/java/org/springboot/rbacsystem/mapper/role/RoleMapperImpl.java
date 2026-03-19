package org.springboot.rbacsystem.mapper.role;

import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements RoleMapper {
	
	@Override
	public RoleDto toDto(RoleEntity roleEntity) {
		if (roleEntity == null) {
			return null;
		}
		
		RoleDto roleDto = new RoleDto();
		roleDto.setId(roleEntity.getId());
		roleDto.setName(roleEntity.getName());
		
		return roleDto;
	}
	
	@Override
	public RoleEntity toEntity(RoleDto roleDto) {
		if (roleDto == null) {
			return null;
		}
		
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setId(roleDto.getId());
		roleEntity.setName(roleDto.getName());
		
		return roleEntity;
	}
}
