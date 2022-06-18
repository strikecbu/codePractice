package com.cloud.userws.services;

import com.cloud.userws.domain.UserEntity;
import com.cloud.userws.shared.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserEntity> createUser(UserDto userDto);

    Mono<UserEntity> findUserByEmail(String email);
}
