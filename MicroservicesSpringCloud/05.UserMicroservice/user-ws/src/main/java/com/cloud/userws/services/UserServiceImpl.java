package com.cloud.userws.services;

import com.cloud.userws.domain.UserEntity;
import com.cloud.userws.mapper.UserMapper;
import com.cloud.userws.repository.UserRepository;
import com.cloud.userws.shared.UserDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository repository;

    public UserServiceImpl(UserMapper userMapper, UserRepository repository) {
        this.userMapper = userMapper;
        this.repository = repository;
    }


    @Override
    public Mono<UserEntity> createUser(UserDto userDto) {

        return Mono.just(userDto)
                .map(userMapper::dtoToEntity)
                .doOnNext(data -> data.setPublicId(UUID.randomUUID()
                        .toString()))
                .flatMap(repository::save);
    }
}
