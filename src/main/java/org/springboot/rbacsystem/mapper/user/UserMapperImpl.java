package org.springboot.rbacsystem.mapper.user;

import lombok.AllArgsConstructor;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.mapper.role.RoleMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {
	
	private final RoleMapper roleMapper;
	
	@Override
	public UserDto toDto(UserEntity userEntity) {
		if (userEntity == null) {
			return null;
		}
		
		UserDto userDto = new UserDto();
		userDto.setId(userEntity.getId());
		userDto.setUsername(userEntity.getUsername());
		userDto.setFullName(userEntity.getFullName());
		userDto.setEmail(userEntity.getEmail());
		userDto.setPassword(userEntity.getPassword());
		
		if (!userEntity.getRoles()
		               .isEmpty()) {
			userDto.setRoles(userEntity
					.getRoles()
					.stream()
					.map(roleMapper::toDto)
					.toList());
		}
		
		return userDto;
	}
	
	@Override
	public UserEntity toEntity(UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		
		UserEntity userEntity = new UserEntity();
		userEntity.setId(userDto.getId());
		userEntity.setUsername(userDto.getUsername());
		userEntity.setFullName(userDto.getFullName());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setPassword(userDto.getPassword());
		
		if (!userDto.getRoles()
		            .isEmpty()) {
			userEntity.setRoles(userDto.getRoles()
			                           .stream()
			                           .map(roleMapper::toEntity)
			                           .collect(Collectors.toSet()));
		}
		
		return userEntity;
	}
}
