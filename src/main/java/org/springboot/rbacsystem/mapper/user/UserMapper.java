package org.springboot.rbacsystem.mapper.user;


import org.mapstruct.Mapper;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.UserEntity;

@Mapper
public interface UserMapper {
	UserDto toDto(UserEntity userEntity);
	
	UserEntity toEntity(UserDto userDto);
}

