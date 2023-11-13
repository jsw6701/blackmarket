package com.example.blackmarket.dto.requeset;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String phoneNumber;

    private String nickname;

    private String account;
}
