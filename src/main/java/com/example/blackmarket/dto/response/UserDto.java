package com.example.blackmarket.dto.response;

import com.example.blackmarket.model.AuthProvider;
import com.example.blackmarket.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private String imageUrl;

    private String role;

    private Boolean emailVerified;

    private AuthProvider provider;

    private String phoneNumber;

    private String nickname;

    private String account;

    private Long money;

    public UserDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.imageUrl = user.getImageUrl();
        this.role = user.getRole();
        this.emailVerified = user.getEmailVerified();
        this.provider = user.getProvider();
        this.phoneNumber = user.getPhoneNumber();
        this.nickname = user.getNickname();
        this.account = user.getAccount();
        this.money = user.getMoney();
    }
}
