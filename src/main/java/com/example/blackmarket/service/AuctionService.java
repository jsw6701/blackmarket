package com.example.blackmarket.service;

import com.example.blackmarket.dto.response.AuctionDto;
import com.example.blackmarket.model.Auction;
import com.example.blackmarket.model.User;

import java.util.List;

public interface AuctionService {

    AuctionDto findById(Long id);

    void participate(Long id, User user);

    List<AuctionDto> findAll();

    List<AuctionDto> findByPostId(Long postId);

    List<AuctionDto> findByUserId(Long userId);
}