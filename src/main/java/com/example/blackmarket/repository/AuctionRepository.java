package com.example.blackmarket.repository;

import com.example.blackmarket.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    List<Auction> findByPostId(Long postId);

    List<Auction> findByUserId(Long userId);
}
