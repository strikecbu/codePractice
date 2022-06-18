package com.cloud.userws.services;

import com.cloud.userws.domain.UserEntity;
import com.cloud.userws.mapper.UserMapper;
import com.cloud.userws.repository.UserRepository;
import com.cloud.userws.shared.UserDto;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository repository;

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public UserServiceImpl(UserMapper userMapper, UserRepository repository, R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.userMapper = userMapper;
        this.repository = repository;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }


    @Override
    public Mono<UserEntity> createUser(UserDto userDto) {

        return Mono.just(userDto).map(userMapper::dtoToEntity)
                .doOnNext(data -> {
                    data.setPublicId(UUID.randomUUID().toString());
                })
                .flatMap(r2dbcEntityTemplate::insert);
    }
}
