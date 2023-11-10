package com.example.blackmarket.controller;

import com.example.blackmarket.dto.response.AuctionDto;
import com.example.blackmarket.model.Auction;
import com.example.blackmarket.model.User;
import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.service.AuctionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/auction/create/{postId}")
    public ResponseEntity<?> createAuction(@CurrentUser User user, @PathVariable Long postId){
        auctionService.participate(postId, user);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/auction/read/{auctionId}")
    public ResponseEntity<AuctionDto> readAuction(@PathVariable Long auctionId){
        AuctionDto auction = auctionService.findById(auctionId);
        return ResponseEntity.ok(auction);
    }

    @GetMapping("/auction/readByPost/{postId}")
    public ResponseEntity<List<AuctionDto>> readAuctionByPost(@PathVariable Long postId){
        List<AuctionDto> auction = auctionService.findByPostId(postId);
        return ResponseEntity.ok(auction);
    }

    @GetMapping("/auction/readByUser/{userId}")
    public ResponseEntity<List<AuctionDto>> readAuctionByUser(@PathVariable Long userId){
        List<AuctionDto> auction = auctionService.findByUserId(userId);
        return ResponseEntity.ok(auction);
    }
}
