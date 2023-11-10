package com.example.blackmarket.dto.requeset;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {

    private String title;

    private String content;

    private String targetDate;

    private Long immediatePurchasePrice;

    private Long biddingPrice;

    private Long biddingUnit;

    private Long categoryId;
}
