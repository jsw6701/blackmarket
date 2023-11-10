package com.example.blackmarket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookMarkDto {

    private Long id;
    private UserDto user;
    private PostDto post;
}
