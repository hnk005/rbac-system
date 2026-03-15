package org.springboot.rbacsystem.mapper.user;


import org.mapstruct.Mapper;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.User;

@Mapper
public interface UserMapper {
	UserDto toDto(User userEntity);
	
	User toEntity(UserDto userDto);
}

