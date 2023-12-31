package com.example.blackmarket.model;
import com.example.blackmarket.dto.response.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    private String role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String phoneNumber;

    private String nickname;

    private String account;

    private String providerId;

    private Long money;

    public UserDto toDto(){
        return UserDto.builder()
                .id(id)
                .email(email)
                .name(name)
                .money(money)
                .imageUrl(imageUrl)
                .role(role)
                .emailVerified(emailVerified)
                .provider(provider)
                .build();
    }
}
