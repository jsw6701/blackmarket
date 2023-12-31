package com.example.blackmarket.model;


import io.swagger.models.Path;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    private Long price;

    private AuctionState auctionState;

    private LocalDateTime createdAt;


    @Builder
    public Auction(Post post, User user, Long price,AuctionState auctionState ,LocalDateTime createdAt) {
        this.post = post;
        this.user = user;
        this.price = price;
        this.auctionState = auctionState;
        this.createdAt = createdAt;
    }
}
