package com.example.blackmarket.service;

import com.example.blackmarket.dto.response.AuctionDto;
import com.example.blackmarket.model.AuctionState;
import com.example.blackmarket.model.User;

import java.util.List;

public interface AuctionService {

    AuctionDto findById(Long id);

    void participate(Long id, User user);
    void immediate(Long id, User user);

    void stateChange(Long postId,AuctionState auctionState);

    List<AuctionDto> findAll();

    List<AuctionDto> findByPostId(Long postId);

    List<AuctionDto> findByUserId(Long userId);

    List<AuctionDto> findByUserIdForMyPage(Long userId);
}
