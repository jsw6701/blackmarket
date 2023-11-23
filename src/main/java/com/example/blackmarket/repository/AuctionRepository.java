package com.example.blackmarket.repository;

import com.example.blackmarket.model.Auction;
import com.example.blackmarket.model.AuctionState;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    List<Auction> findByPostId(Long postId);

    List<Auction> findByUserId(Long userId);

    List<Auction> findByUserIdAndAuctionState(Long userId, AuctionState auctionState);

//    @Query("SELECT DISTINCT a FROM Post p JOIN auction a WHERE p.user = :user")
//    @Query("SELECT p from auction a JOIN post p where a.post_id= p.id")
    @Query("SELECT p FROM Auction a JOIN a.post p WHERE p.id = a.post.id and a.user = :user")
    List<Post> findDistinctByUser(@Param("user") User user);

    @Query("SELECT p FROM Auction a JOIN a.post p WHERE p.id = a.post.id GROUP BY p.id ORDER BY count(a) desc")
    List<Post> findTop5();



}
