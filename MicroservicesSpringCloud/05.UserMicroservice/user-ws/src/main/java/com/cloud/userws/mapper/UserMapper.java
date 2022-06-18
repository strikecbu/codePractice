package com.cloud.userws.mapper;

import com.cloud.userws.domain.UserEntity;
import com.cloud.userws.shared.UserDto;
import com.cloud.userws.ui.model.UserRequestModel;
import com.cloud.userws.ui.model.UserResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    abstract public UserDto requestToDto(UserRequestModel requestModel);


    abstract UserEntity _dtoToEntity(UserDto userDto);

    public UserEntity dtoToEntity(UserDto userDto) {
        UserEntity userEntity = _dtoToEntity(userDto);
        userEntity.setEncryptPassword(passwordEncoder.encode(userDto.getPassword()));
        return userEntity;
    }

    @Mapping(target = "public_id", source = "publicId")
    abstract public UserResponseModel entityToResponse(UserEntity userEntity);
}
