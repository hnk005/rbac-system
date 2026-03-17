package org.springboot.rbacsystem.mapper.user;


import org.mapstruct.Mapper;
import org.springboot.rbacsystem.dto.UserDto;
import org.springboot.rbacsystem.entity.UserEntity;
import org.springboot.rbacsystem.mapper.role.RoleMapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
	UserDto toDto(UserEntity userEntity);
	
	UserEntity toEntity(UserDto userDto);
}

