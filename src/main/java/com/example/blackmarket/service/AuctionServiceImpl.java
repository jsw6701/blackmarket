package com.example.blackmarket.service;

import com.example.blackmarket.dto.response.AuctionDto;
import com.example.blackmarket.model.Auction;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.State;
import com.example.blackmarket.model.User;
import com.example.blackmarket.repository.AuctionRepository;
import com.example.blackmarket.repository.PostRepository;
import com.example.blackmarket.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final AuctionRepository auctionRepository;

    @Override
    public AuctionDto findById(Long id) {
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 경매가 없습니다."));

        return AuctionDto.builder()
                .id(auction.getId())
                .post(auction.getPost().toDto())
                .user(auction.getUser().toDto())
                .price(auction.getPrice())
                .createdAt(auction.getCreatedAt())
                .build();
    }

    @Override
    public void participate(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if(post.getStatus() != State.FINISHED){
            throw new IllegalStateException("경매가 종료되었습니다.");
        }

        if(post.getUser().getId().equals(user.getId())){
            throw new IllegalStateException("자신의 글에는 입찰할 수 없습니다.");
        }

        Long price = post.getBiddingPrice() + post.getBiddingUnit();

        post.setBiddingPrice(price);

        Auction auction = Auction.builder()
                .post(post)
                .user(user)
                .price(price)
                .build();

        auction.setCreatedAt(LocalDateTime.now());

        auctionRepository.save(auction);
        postRepository.save(post);
    }

    @Override
    public List<AuctionDto> findAll() {
        List<Auction> list = auctionRepository.findAll();

        return list.stream()
                .map(auction -> AuctionDto.builder()
                        .id(auction.getId())
                        .post(auction.getPost().toDto())
                        .user(auction.getUser().toDto())
                        .price(auction.getPrice())
                        .createdAt(auction.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionDto> findByPostId(Long postId) {
        List<Auction> list = auctionRepository.findByPostId(postId);

        return list.stream()
                .map(auction -> AuctionDto.builder()
                        .id(auction.getId())
                        .post(auction.getPost().toDto())
                        .user(auction.getUser().toDto())
                        .price(auction.getPrice())
                        .createdAt(auction.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionDto> findByUserId(Long userId) {
        List<Auction> list = auctionRepository.findByUserId(userId);

        return list.stream()
                .map(auction -> AuctionDto.builder()
                        .id(auction.getId())
                        .post(auction.getPost().toDto())
                        .user(auction.getUser().toDto())
                        .price(auction.getPrice())
                        .createdAt(auction.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
