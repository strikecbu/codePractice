package com.cloud.userws.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    private String email;

    @Size(min = 8, max = 12)
    private String password;
}
