package com.example.blackmarket.dto.response;

import com.example.blackmarket.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    private String title;

    private String content;

    private String createDate;

    private String targetDate;

    private Long viewCount;

    private Long immediatePurchasePrice;

    private Long biddingPrice;

    private Long biddingUnit;

    private CategoryDto category;

    public PostDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createDate = post.getCreateDate();
        this.targetDate = post.getTargetDate();
        this.viewCount = post.getViewCount();
        this.immediatePurchasePrice = post.getImmediatePurchasePrice();
        this.biddingPrice = post.getBiddingPrice();
        this.biddingUnit = post.getBiddingUnit();
        this.category = new CategoryDto(post.getCategory());
    }
}
