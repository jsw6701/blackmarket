package com.example.blackmarket.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String password2;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String account;

    @NotBlank
    private String nickname;

}
