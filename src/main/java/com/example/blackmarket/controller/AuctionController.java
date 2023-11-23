package com.example.blackmarket.controller;

import com.example.blackmarket.dto.response.AuctionDto;
import com.example.blackmarket.model.*;
import com.example.blackmarket.repository.AuctionRepository;
import com.example.blackmarket.repository.PostRepository;
import com.example.blackmarket.repository.UserRepository;
import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.security.UserPrincipal;
import com.example.blackmarket.service.AuctionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@AllArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    private final UserRepository userRepository;

    private final AuctionRepository auctionRepository;

    private final PostRepository postRepository;

    @PostMapping("/auction/create/{postId}")
    public ResponseEntity<?> createAuction(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long postId){
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        auctionService.participate(postId, user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/auction/immediate/{postId}")
    public ResponseEntity<?> immediateAuction(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long postId){
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        auctionService.immediate(postId, user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/auction/read/{auctionId}")
    public ResponseEntity<AuctionDto> readAuction(@PathVariable Long auctionId){
        AuctionDto auction = auctionService.findById(auctionId);
        return ResponseEntity.ok(auction);
    }

//    @GetMapping("/auction/readByPost/{postId}")
//    public ResponseEntity<List<AuctionDto>> readAuctionByPost(@PathVariable Long postId){
//        List<AuctionDto> auction = auctionService.findByPostId(postId);
//        return ResponseEntity.ok(auction);
//    }
    @GetMapping("/auction/readByPost/{postId}")
    public String readAuctionByPost(@PathVariable Long postId, Model model){
        List<AuctionDto> auction = auctionService.findByPostId(postId);
        model.addAttribute("auctionList", auction);
        return "AJAX/detail_auction";
    }

    @GetMapping("/auction/readByUser/{userId}")
    public ResponseEntity<List<AuctionDto>> readAuctionByUser(@PathVariable Long userId){
        List<AuctionDto> auction = auctionService.findByUserId(userId);
        return ResponseEntity.ok(auction);
    }

    @PostMapping("/auction/pay/{auctionId}")
    public ResponseEntity<?> payOk(@CurrentUser UserPrincipal userPrincipal,@PathVariable Long auctionId){
        try {
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
            Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new IllegalArgumentException("해당 경매가 없습니다."));

            if (auction.getUser().getId() != user.getId()) {
                throw new IllegalArgumentException("입찰한 유저가 아닙니다.");
            }
            if (auction.getAuctionState() != AuctionState.Waiting) {
                throw new IllegalArgumentException("결제 대기중이 아닙니다.");
            }
            if (user.getMoney() < auction.getPrice()) {
                throw new IllegalArgumentException("돈이 부족합니다.");
            }

            auction.setAuctionState(AuctionState.COMPLETED);
            user.setMoney(user.getMoney()-auction.getPrice());

            userRepository.save(user);
            auctionRepository.save(auction);
            Post post = auction.getPost();
            post.setStatus(State.COMPLETED);
            postRepository.save(post);


            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            // 예외가 발생하면 해당 메세지를 포함한 실패 응답을 보냅니다.
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

}
