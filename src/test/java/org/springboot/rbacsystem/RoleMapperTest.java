package org.springboot.rbacsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.Permission;
import org.springboot.rbacsystem.entity.Role;
import org.springboot.rbacsystem.entity.User;
import org.springboot.rbacsystem.mapper.permission.PermissionMapper;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springboot.rbacsystem.mapper.role.RoleMapperImpl;
import org.springboot.rbacsystem.mapper.user.UserMapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleMapperTest {
	
	private static final Long ROLE_ID = 1L;
	private static final String ROLE_NAME = "ROLE_ADMIN";
	
	private PermissionMapper permissionMapper;
	private UserMapper userMapper;
	private RoleMapper roleMapper;
	
	@BeforeEach
	void setUp() {
		permissionMapper = Mockito.mock(PermissionMapper.class);
		userMapper = Mockito.mock(UserMapper.class);
		roleMapper = new RoleMapperImpl(permissionMapper, userMapper);
	}
	
	@Test
	void testToDto() {
		// arrange
		Permission permission1 = createPermission(1L, "READ_USER");
		Permission permission2 = createPermission(2L, "WRITE_USER");
		Set<Permission> permissions = new LinkedHashSet<>(List.of(permission1, permission2));
		
		User user1 = createUser(1L, "admin");
		User user2 = createUser(2L, "manager");
		Set<User> users = new LinkedHashSet<>(List.of(user1, user2));
		
		PermissionDto permissionDto1 = createPermissionDto(permission1.getId(), permission1.getName());
		PermissionDto permissionDto2 = createPermissionDto(permission2.getId(), permission2.getName());
		UserDto userDto1 = createUserDto(user1.getId(), user1.getUsername());
		UserDto userDto2 = createUserDto(user2.getId(), user2.getUsername());
		
		when(permissionMapper.toDto(permission1)).thenReturn(permissionDto1);
		when(permissionMapper.toDto(permission2)).thenReturn(permissionDto2);
		when(userMapper.toDto(user1)).thenReturn(userDto1);
		when(userMapper.toDto(user2)).thenReturn(userDto2);
		
		Role roleEntity = createRole(permissions, users);
		
		RoleDto expectedRoleDto = new RoleDto();
		expectedRoleDto.setId(roleEntity.getId());
		expectedRoleDto.setName(roleEntity.getName());
		expectedRoleDto.setPermissions(roleEntity.getPermissions()
		                                      .stream()
		                                      .map(permissionMapper::toDto)
		                                      .collect(Collectors.toList()));
		expectedRoleDto.setUsers(roleEntity.getUsers()
		                               .stream()
		                               .map(userMapper::toDto)
		                               .collect(Collectors.toList()));
		
		// act
		RoleDto actualRoleDto = roleMapper.toDto(roleEntity);
		
		// assert
		assertEquals(expectedRoleDto, actualRoleDto);
	}
	
	@Test
	void testToEntity() {
		// arrange
		PermissionDto permissionDto1 = createPermissionDto(1L, "READ_USER");
		PermissionDto permissionDto2 = createPermissionDto(2L, "WRITE_USER");
		List<PermissionDto> permissionDtos = List.of(permissionDto1, permissionDto2);
		
		Permission permission1 = createPermission(permissionDto1.getId(), permissionDto1.getName());
		Permission permission2 = createPermission(permissionDto2.getId(), permissionDto2.getName());
		
		when(permissionMapper.toEntity(permissionDto1)).thenReturn(permission1);
		when(permissionMapper.toEntity(permissionDto2)).thenReturn(permission2);
		
		RoleDto roleDto = new RoleDto();
		roleDto.setId(ROLE_ID);
		roleDto.setName(ROLE_NAME);
		roleDto.setPermissions(permissionDtos);
		
		Role expectedRoleEntity = new Role();
		expectedRoleEntity.setId(roleDto.getId());
		expectedRoleEntity.setName(roleDto.getName());
		expectedRoleEntity.setPermissions(roleDto.getPermissions()
		                                        .stream()
		                                        .map(permissionMapper::toEntity)
		                                        .collect(Collectors.toCollection(LinkedHashSet::new)));
		
		// act
		Role actualRoleEntity = roleMapper.toEntity(roleDto);
		
		// assert
		assertEquals(expectedRoleEntity, actualRoleEntity);
	}
	
	private Role createRole(Set<Permission> permissions, Set<User> users) {
		Role role = new Role();
		role.setId(ROLE_ID);
		role.setName(ROLE_NAME);
		role.setPermissions(permissions);
		role.setUsers(users);
		return role;
	}
	
	private Permission createPermission(Long id, String name) {
		Permission permission = new Permission();
		permission.setId(id);
		permission.setName(name);
		return permission;
	}
	
	private PermissionDto createPermissionDto(Long id, String name) {
		PermissionDto permissionDto = new PermissionDto();
		permissionDto.setId(id);
		permissionDto.setName(name);
		return permissionDto;
	}
	
	private User createUser(Long id, String username) {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		return user;
	}
	
	private UserDto createUserDto(Long id, String username) {
		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setUsername(username);
		return userDto;
	}
}

