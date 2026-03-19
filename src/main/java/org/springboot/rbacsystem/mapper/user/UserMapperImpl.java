package org.springboot.rbacsystem.mapper.user;

import org.springboot.rbacsystem.dto.CreateUserDto;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
	
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
		
		return userEntity;
	}
	
	@Override
	public UserEntity fromCreate(CreateUserDto createUserDto) {
		if (createUserDto == null) {
			return null;
		}
		
		UserEntity userEntity = new UserEntity();
		
		userEntity.setFullName(createUserDto.getFullName());
		userEntity.setEmail(createUserDto.getEmail());
		userEntity.setPassword(createUserDto.getPassword());
		
		return userEntity;
	}
	
	
}
