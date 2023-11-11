package com.example.blackmarket.model;


import com.example.blackmarket.dto.response.BookMarkDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    public BookMark(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public BookMarkDto toDto() {
        return BookMarkDto.builder()
                .id(id)
                .post(post.toDto())
                .user(user.toDto())
                .build();
    }
}
