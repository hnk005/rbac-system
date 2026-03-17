package org.springboot.rbacsystem.mapper.role;

import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.mapper.permission.PermissionMapper;
import org.springboot.rbacsystem.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapperImpl implements RoleMapper {
	
	@Lazy
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Lazy
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public RoleDto toDto(RoleEntity roleEntity) {
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
	public RoleEntity toEntity(RoleDto roleDto) {
		if (roleDto == null) {
			return null;
		}
		
		RoleEntity roleEntity = new RoleEntity();
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
