package com.example.blackmarket.dto.response;

import com.example.blackmarket.model.Auction;
import com.example.blackmarket.model.AuctionState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDto {

    private Long id;

    private PostDto post;

    private UserDto user;

    private Long price;

    private AuctionState auctionState;

    private LocalDateTime createdAt;

}
