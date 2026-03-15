package org.springboot.rbacsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.Role;
import org.springboot.rbacsystem.entity.User;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springboot.rbacsystem.mapper.user.UserMapper;
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
	
	private RoleMapper roleMapper;
	
	private UserMapper userMapper;
	
	@BeforeEach
	void setUp() {
		roleMapper = Mockito.mock(RoleMapper.class);
		userMapper = new UserMapperImpl(roleMapper);
	}
	
	@Test
	void testToDto() {
		// arrange
		Role role1 = createRole(1L, "ROLE_USER");
		Role role2 = createRole(2L, "ROLE_ADMIN");
		Set<Role> roles = Set.of(role1, role2);
		
		RoleDto roleDto1 = createRoleDto(role1.getId(), role1.getName());
		RoleDto roleDto2 = createRoleDto(role2.getId(), role2.getName());
		
		when(roleMapper.toDto(role1)).thenReturn(roleDto1);
		when(roleMapper.toDto(role2)).thenReturn(roleDto2);
		
		User userEntity = createUser(roles);
		
		UserDto expectedUserDto = createUserDto(roles.stream()
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
		
		Role role1 = createRole(roleDto1.getId(), roleDto1.getName());
		Role role2 = createRole(roleDto2.getId(), roleDto2.getName());
		
		when(roleMapper.toEntity(roleDto1)).thenReturn(role1);
		when(roleMapper.toEntity(roleDto2)).thenReturn(role2);
		
		UserDto userDto = createUserDto(roleDtos);
		
		User expectedUserEntity = createUser(roleDtos.stream()
		                                    .map(roleMapper::toEntity)
		                                    .collect(Collectors.toSet()));
		
		// act
		User userEntity = userMapper.toEntity(userDto);
		
		// assert
		assertEquals(expectedUserEntity, userEntity);
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
	
	private User createUser(Set<Role> roles) {
		User user = new User();
		user.setId(USER_ID);
		user.setUsername(USERNAME);
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
		user.setFullName(FULL_NAME);
		user.setRoles(roles);
		return user;
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
