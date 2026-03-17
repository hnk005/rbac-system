package org.springboot.rbacsystem.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springboot.rbacsystem.entity.RoleEntity;
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
	
	@Mock
	private RoleMapper roleMapper;
	
	@InjectMocks
	private PermissionMapperImpl permissionMapper;
	
	
	@Test
	void testToDto() {
		// arrange
		RoleEntity roleEntity1 = createRole(1L, "ROLE_USER");
		RoleEntity roleEntity2 = createRole(2L, "ROLE_ADMIN");
		Set<RoleEntity> roleEntities = new LinkedHashSet<>(List.of(roleEntity1, roleEntity2));
		
		RoleDto roleDto1 = createRoleDto(roleEntity1.getId(), roleEntity1.getName());
		RoleDto roleDto2 = createRoleDto(roleEntity2.getId(), roleEntity2.getName());
		
		when(roleMapper.toDto(roleEntity1)).thenReturn(roleDto1);
		when(roleMapper.toDto(roleEntity2)).thenReturn(roleDto2);
		
		PermissionEntity permissionEntity = createPermission(roleEntities);
		
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
		
		PermissionEntity expectedPermissionEntityEntity = new PermissionEntity();
		expectedPermissionEntityEntity.setId(permissionDto.getId());
		expectedPermissionEntityEntity.setName(permissionDto.getName());
		
		// act
		PermissionEntity actualPermissionEntityEntity = permissionMapper.toEntity(permissionDto);
		
		// assert
		assertEquals(expectedPermissionEntityEntity, actualPermissionEntityEntity);
	}
	
	private PermissionEntity createPermission(Set<RoleEntity> roleEntities) {
		PermissionEntity permissionEntity = new PermissionEntity();
		permissionEntity.setId(PERMISSION_ID);
		permissionEntity.setName(PERMISSION_NAME);
		permissionEntity.setRoles(roleEntities);
		return permissionEntity;
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
}

