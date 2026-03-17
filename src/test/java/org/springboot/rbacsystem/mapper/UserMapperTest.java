package org.springboot.rbacsystem.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springboot.rbacsystem.mapper.user.UserMapperImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
	
	private static final Long USER_ID = 1L;
	private static final String USERNAME = "testuser";
	private static final String EMAIL = "test@email.com";
	private static final String PASSWORD = "password";
	private static final String FULL_NAME = "Test User";
	
	@Mock
	private RoleMapper roleMapper;
	
	@InjectMocks
	private UserMapperImpl userMapper;
	
	@Test
	void testToDto() {
		// arrange
		RoleEntity roleEntity1 = createRole(1L, "ROLE_USER");
		RoleEntity roleEntity2 = createRole(2L, "ROLE_ADMIN");
		Set<RoleEntity> roleEntities = Set.of(roleEntity1, roleEntity2);
		
		RoleDto roleDto1 = createRoleDto(roleEntity1.getId(), roleEntity1.getName());
		RoleDto roleDto2 = createRoleDto(roleEntity2.getId(), roleEntity2.getName());
		
		when(roleMapper.toDto(roleEntity1)).thenReturn(roleDto1);
		when(roleMapper.toDto(roleEntity2)).thenReturn(roleDto2);
		
		UserEntity userEntity = createUser(roleEntities);
		
		UserDto expectedUserDto = createUserDto(roleEntities.stream()
		                                                    .map(roleMapper::toDto)
		                                                    .collect(Collectors.toList()));
		
		// act
		UserDto userDto = userMapper.toDto(userEntity);
		
		// assert
		assertEquals(expectedUserDto, userDto);
	}
	
	@Test
	void testToEntity() {
		// arrange
		RoleDto roleDto1 = createRoleDto(1L, "ROLE_USER");
		RoleDto roleDto2 = createRoleDto(2L, "ROLE_ADMIN");
		List<RoleDto> roleDtos = List.of(roleDto1, roleDto2);
		
		RoleEntity roleEntity1 = createRole(roleDto1.getId(), roleDto1.getName());
		RoleEntity roleEntity2 = createRole(roleDto2.getId(), roleDto2.getName());
		
		when(roleMapper.toEntity(roleDto1)).thenReturn(roleEntity1);
		when(roleMapper.toEntity(roleDto2)).thenReturn(roleEntity2);
		
		UserDto userDto = createUserDto(roleDtos);
		
		UserEntity expectedUserEntityEntity = createUser(roleDtos.stream()
		                                                         .map(roleMapper::toEntity)
		                                                         .collect(Collectors.toSet()));
		
		// act
		UserEntity userEntity = userMapper.toEntity(userDto);
		
		// assert
		assertEquals(expectedUserEntityEntity, userEntity);
	}
	
	private RoleEntity createRole(Long id, String name) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setId(id);
		roleEntity.setName(name);
		return roleEntity;
	}
	
	private RoleDto createRoleDto(Long id, String name) {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(id);
		roleDto.setName(name);
		return roleDto;
	}
	
	private UserEntity createUser(Set<RoleEntity> roleEntities) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(USER_ID);
		userEntity.setUsername(USERNAME);
		userEntity.setEmail(EMAIL);
		userEntity.setPassword(PASSWORD);
		userEntity.setFullName(FULL_NAME);
		userEntity.setRoles(roleEntities);
		return userEntity;
	}
	
	private UserDto createUserDto(List<RoleDto> roleDtos) {
		UserDto userDto = new UserDto();
		userDto.setId(USER_ID);
		userDto.setUsername(USERNAME);
		userDto.setEmail(EMAIL);
		userDto.setPassword(PASSWORD);
		userDto.setFullName(FULL_NAME);
		userDto.setRoles(roleDtos);
		return userDto;
	}
}
