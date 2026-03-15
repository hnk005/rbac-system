package org.springboot.rbacsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.Permission;
import org.springboot.rbacsystem.entity.Role;
import org.springboot.rbacsystem.mapper.permission.PermissionMapper;
import org.springboot.rbacsystem.mapper.permission.PermissionMapperImpl;
import org.springboot.rbacsystem.mapper.role.RoleMapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PermissionMapperTest {
	
	private static final Long PERMISSION_ID = 1L;
	private static final String PERMISSION_NAME = "READ_USER";
	
	private RoleMapper roleMapper;
	private PermissionMapper permissionMapper;
	
	@BeforeEach
	void setUp() {
		roleMapper = Mockito.mock(RoleMapper.class);
		permissionMapper = new PermissionMapperImpl(roleMapper);
	}
	
	@Test
	void testToDto() {
		// arrange
		Role role1 = createRole(1L, "ROLE_USER");
		Role role2 = createRole(2L, "ROLE_ADMIN");
		Set<Role> roles = new LinkedHashSet<>(List.of(role1, role2));
		
		RoleDto roleDto1 = createRoleDto(role1.getId(), role1.getName());
		RoleDto roleDto2 = createRoleDto(role2.getId(), role2.getName());
		
		when(roleMapper.toDto(role1)).thenReturn(roleDto1);
		when(roleMapper.toDto(role2)).thenReturn(roleDto2);
		
		Permission permissionEntity = createPermission(roles);
		
		PermissionDto expectedPermissionDto = new PermissionDto();
		expectedPermissionDto.setId(permissionEntity.getId());
		expectedPermissionDto.setName(permissionEntity.getName());
		expectedPermissionDto.setRoles(permissionEntity.getRoles()
		                                            .stream()
		                                            .map(roleMapper::toDto)
		                                            .collect(Collectors.toList()));
		
		// act
		PermissionDto actualPermissionDto = permissionMapper.toDto(permissionEntity);
		
		// assert
		assertEquals(expectedPermissionDto, actualPermissionDto);
	}
	
	@Test
	void testToEntity() {
		// arrange
		PermissionDto permissionDto = new PermissionDto();
		permissionDto.setId(PERMISSION_ID);
		permissionDto.setName(PERMISSION_NAME);
		permissionDto.setDes("Can read user information");
		permissionDto.setRoles(List.of(createRoleDto(1L, "ROLE_USER")));
		
		Permission expectedPermissionEntity = new Permission();
		expectedPermissionEntity.setId(permissionDto.getId());
		expectedPermissionEntity.setName(permissionDto.getName());
		
		// act
		Permission actualPermissionEntity = permissionMapper.toEntity(permissionDto);
		
		// assert
		assertEquals(expectedPermissionEntity, actualPermissionEntity);
	}
	
	private Permission createPermission(Set<Role> roles) {
		Permission permission = new Permission();
		permission.setId(PERMISSION_ID);
		permission.setName(PERMISSION_NAME);
		permission.setRoles(roles);
		return permission;
	}
	
	private Role createRole(Long id, String name) {
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		return role;
	}
	
	private RoleDto createRoleDto(Long id, String name) {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(id);
		roleDto.setName(name);
		return roleDto;
	}
}

