package org.springboot.rbacsystem.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.mapper.permission.PermissionMapper;
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
	
	@Mock
	private PermissionMapper permissionMapper;
	
	@Mock
	private UserMapper userMapper;
	
	@InjectMocks
	private RoleMapperImpl roleMapper;
	
	
	@Test
	void testToDto() {
		// arrange
		PermissionEntity permissionEntity1 = createPermission(1L, "READ_USER");
		PermissionEntity permissionEntity2 = createPermission(2L, "WRITE_USER");
		Set<PermissionEntity> permissionEntities = new LinkedHashSet<>(List.of(permissionEntity1, permissionEntity2));
		
		UserEntity userEntity1 = createUser(1L, "admin");
		UserEntity userEntity2 = createUser(2L, "manager");
		Set<UserEntity> userEntities = new LinkedHashSet<>(List.of(userEntity1, userEntity2));
		
		PermissionDto permissionDto1 = createPermissionDto(permissionEntity1.getId(), permissionEntity1.getName());
		PermissionDto permissionDto2 = createPermissionDto(permissionEntity2.getId(), permissionEntity2.getName());
		UserDto userDto1 = createUserDto(userEntity1.getId(), userEntity1.getUsername());
		UserDto userDto2 = createUserDto(userEntity2.getId(), userEntity2.getUsername());
		
		when(permissionMapper.toDto(permissionEntity1)).thenReturn(permissionDto1);
		when(permissionMapper.toDto(permissionEntity2)).thenReturn(permissionDto2);
		when(userMapper.toDto(userEntity1)).thenReturn(userDto1);
		when(userMapper.toDto(userEntity2)).thenReturn(userDto2);
		
		RoleEntity roleEntity = createRole(permissionEntities, userEntities);
		
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
		
		PermissionEntity permissionEntity1 = createPermission(permissionDto1.getId(), permissionDto1.getName());
		PermissionEntity permissionEntity2 = createPermission(permissionDto2.getId(), permissionDto2.getName());
		
		when(permissionMapper.toEntity(permissionDto1)).thenReturn(permissionEntity1);
		when(permissionMapper.toEntity(permissionDto2)).thenReturn(permissionEntity2);
		
		RoleDto roleDto = new RoleDto();
		roleDto.setId(ROLE_ID);
		roleDto.setName(ROLE_NAME);
		roleDto.setPermissions(permissionDtos);
		
		RoleEntity expectedRoleEntityEntity = new RoleEntity();
		expectedRoleEntityEntity.setId(roleDto.getId());
		expectedRoleEntityEntity.setName(roleDto.getName());
		expectedRoleEntityEntity.setPermissions(roleDto.getPermissions()
		                                               .stream()
		                                               .map(permissionMapper::toEntity)
		                                               .collect(Collectors.toCollection(LinkedHashSet::new)));
		
		// act
		RoleEntity actualRoleEntityEntity = roleMapper.toEntity(roleDto);
		
		// assert
		assertEquals(expectedRoleEntityEntity, actualRoleEntityEntity);
	}
	
	private RoleEntity createRole(Set<PermissionEntity> permissionEntities, Set<UserEntity> userEntities) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setId(ROLE_ID);
		roleEntity.setName(ROLE_NAME);
		roleEntity.setPermissions(permissionEntities);
		roleEntity.setUsers(userEntities);
		return roleEntity;
	}
	
	private PermissionEntity createPermission(Long id, String name) {
		PermissionEntity permissionEntity = new PermissionEntity();
		permissionEntity.setId(id);
		permissionEntity.setName(name);
		return permissionEntity;
	}
	
	private PermissionDto createPermissionDto(Long id, String name) {
		PermissionDto permissionDto = new PermissionDto();
		permissionDto.setId(id);
		permissionDto.setName(name);
		return permissionDto;
	}
	
	private UserEntity createUser(Long id, String username) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(id);
		userEntity.setUsername(username);
		return userEntity;
	}
	
	private UserDto createUserDto(Long id, String username) {
		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setUsername(username);
		return userDto;
	}
}

