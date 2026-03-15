package org.springboot.rbacsystem.mapper.role;

import lombok.AllArgsConstructor;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.Role;
import org.springboot.rbacsystem.mapper.permission.PermissionMapper;
import org.springboot.rbacsystem.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleMapperImpl implements RoleMapper {
	
	private final PermissionMapper permissionMapper;
	private final UserMapper userMapper;
	
	@Override
	public RoleDto toDto(Role roleEntity) {
		if (roleEntity == null) {
			return null;
		}
		
		RoleDto roleDto = new RoleDto();
		roleDto.setId(roleEntity.getId());
		roleDto.setName(roleEntity.getName());
		roleDto.setUsers(roleEntity
				.getUsers()
				.stream()
				.map(userMapper::toDto)
				.toList());
		roleDto.setPermissions(roleEntity
				.getPermissions()
				.stream()
				.map(permissionMapper::toDto)
				.toList());
		
		return roleDto;
	}
	
	@Override
	public Role toEntity(RoleDto roleDto) {
		if (roleDto == null) {
			return null;
		}
		
		Role roleEntity = new Role();
		roleEntity.setId(roleDto.getId());
		roleEntity.setName(roleDto.getName());
		roleEntity.setPermissions(roleDto
				.getPermissions()
				.stream()
				.map(permissionMapper::toEntity)
				.collect(Collectors.toSet()));
		
		return roleEntity;
	}
}
