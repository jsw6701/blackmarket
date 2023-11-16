package com.example.blackmarket.dto.response;

import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime targetDate;

    private Long viewCount;

    private Long immediatePurchasePrice;

    private Long biddingPrice;

    private Long biddingUnit;

    private State state;

    private List<String> fileArray;

    private CategoryDto category;

    private UserDto user;

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
        this.state = post.getStatus();
        this.fileArray = post.getFileArray();
        this.category = new CategoryDto(post.getCategory());
        this.user = new UserDto(post.getUser());
    }
}
