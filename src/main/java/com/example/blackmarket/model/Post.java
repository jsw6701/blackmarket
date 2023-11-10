package com.example.blackmarket.model;


import com.example.blackmarket.dto.response.PostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    //경매 등록일
    private LocalDateTime createDate;

    //경매 종료일
    private LocalDateTime targetDate;

    //조회수
    private Long viewCount;

    //즉시구매가
    private Long immediatePurchasePrice;

    //입찰가
    private Long biddingPrice;

    //입찰단위
    private Long biddingUnit;

    // 게시글 상태
    private State status;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;


    @Builder
    public Post(String title, String content, LocalDateTime createDate, LocalDateTime targetDate, Long viewCount, Long immediatePurchasePrice, Long biddingPrice, Long biddingUnit, State status, Category category, User user) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.targetDate = targetDate;
        this.viewCount = viewCount;
        this.immediatePurchasePrice = immediatePurchasePrice;
        this.biddingPrice = biddingPrice;
        this.biddingUnit = biddingUnit;
        this.status = status;
        this.category = category;
        this.user = user;
    }

    public PostDto toDto() {
        return PostDto.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .createDate(this.createDate)
                .targetDate(this.targetDate)
                .viewCount(this.viewCount)
                .immediatePurchasePrice(this.immediatePurchasePrice)
                .biddingPrice(this.biddingPrice)
                .biddingUnit(this.biddingUnit)
                .state(this.status)
                .category(category.toDto())
                .user(user.toDto())
                .build();
    }
}
