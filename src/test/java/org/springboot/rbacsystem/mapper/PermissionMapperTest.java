package org.springboot.rbacsystem.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springboot.rbacsystem.dto.PermissionDto;
import org.springboot.rbacsystem.dto.RoleDto;
import org.springboot.rbacsystem.entity.PermissionEntity;
import org.springboot.rbacsystem.entity.RoleEntity;
import org.springboot.rbacsystem.mapper.permission.PermissionMapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PermissionMapperTest {
	
	private static final Long PERMISSION_ID = 1L;
	private static final String PERMISSION_NAME = "READ_USER";
	
	@InjectMocks
	private PermissionMapperImpl permissionMapper;
	
	@Test
	void testToDto() {
		// arrange
		PermissionEntity permissionEntity = createPermission();
		
		PermissionDto expectedPermissionDto = new PermissionDto();
		expectedPermissionDto.setId(permissionEntity.getId());
		expectedPermissionDto.setName(permissionEntity.getName());
		
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
		
		PermissionEntity expectedPermissionEntityEntity = new PermissionEntity();
		expectedPermissionEntityEntity.setId(permissionDto.getId());
		expectedPermissionEntityEntity.setName(permissionDto.getName());
		
		// act
		PermissionEntity actualPermissionEntityEntity = permissionMapper.toEntity(permissionDto);
		
		// assert
		assertEquals(expectedPermissionEntityEntity, actualPermissionEntityEntity);
	}
	
	private PermissionEntity createPermission() {
		PermissionEntity permissionEntity = new PermissionEntity();
		permissionEntity.setId(PERMISSION_ID);
		permissionEntity.setName(PERMISSION_NAME);
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

