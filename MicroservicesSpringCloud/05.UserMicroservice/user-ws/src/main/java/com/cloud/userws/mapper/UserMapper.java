package com.cloud.userws.mapper;

import com.cloud.userws.domain.UserEntity;
import com.cloud.userws.shared.UserDto;
import com.cloud.userws.ui.model.UserRequestModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto requestToDto(UserRequestModel requestModel);

    UserEntity dtoToEntity(UserDto userDto);
}
