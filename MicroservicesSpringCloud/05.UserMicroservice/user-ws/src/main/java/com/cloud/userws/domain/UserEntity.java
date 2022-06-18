package com.cloud.userws.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "cloud_users")
@Data
public class UserEntity {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String publicId;
    private String encryptPassword;

    @CreatedDate
    private LocalDateTime createDateTime;


}
