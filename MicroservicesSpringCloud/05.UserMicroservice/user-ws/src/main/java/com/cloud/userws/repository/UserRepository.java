package com.cloud.userws.repository;

import com.cloud.userws.domain.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
}
