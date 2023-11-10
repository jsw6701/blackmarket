package com.example.blackmarket.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private String createDate;

    //경매 종료일
    private String targetDate;

    //조회수
    private Long viewCount;

    //즉시구매가
    private Long immediatePurchasePrice;

    //입찰가
    private Long biddingPrice;

    //입찰단위
    private Long biddingUnit;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;


    @Builder
    public Post(String title, String content, String createDate, String targetDate, Long viewCount, Long immediatePurchasePrice, Long biddingPrice, Long biddingUnit, Category category, User user) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.targetDate = targetDate;
        this.viewCount = viewCount;
        this.immediatePurchasePrice = immediatePurchasePrice;
        this.biddingPrice = biddingPrice;
        this.biddingUnit = biddingUnit;
        this.category = category;
        this.user = user;
    }
}
