package com.example.blackmarket.service;

import com.example.blackmarket.dto.response.AuctionDto;
import com.example.blackmarket.model.*;
import com.example.blackmarket.repository.AuctionRepository;
import com.example.blackmarket.repository.PostRepository;
import com.example.blackmarket.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        if(post.getStatus() == State.FINISHED){
            throw new IllegalStateException("경매가 종료되었습니다.");
        }

        if(post.getUser().getId().equals(user.getId())){
            throw new IllegalStateException("자신의 글에는 입찰할 수 없습니다.");
        }

        Long price = post.getBiddingPrice() + post.getBiddingUnit();
        user.setMoney(user.getMoney() - (post.getBiddingPrice() + post.getBiddingUnit()));

        post.setBiddingPrice(price);

        Auction auction = Auction.builder()
                .post(post)
                .user(user)
                .price(price)
                .build();

        auction.setCreatedAt(LocalDateTime.now());

        auction.setAuctionState(AuctionState.BIDDING);

        stateChange(postId,AuctionState.LOWER);

        userRepository.save(user);
        auctionRepository.save(auction);
        postRepository.save(post);
    }

    @Override
    public void immediate(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if(post.getStatus() == State.FINISHED){
            throw new IllegalStateException("경매가 종료되었습니다.");
        }

        if(post.getUser().getId().equals(user.getId())){
            throw new IllegalStateException("자신의 글에는 입찰할 수 없습니다.");
        }

        Long price = post.getImmediatePurchasePrice();

        Auction auction = Auction.builder()
                .post(post)
                .user(user)
                .price(price)
                .auctionState(AuctionState.immediate)
                .build();

        auction.setCreatedAt(LocalDateTime.now());

        auction.setAuctionState(AuctionState.immediate);

        stateChange(postId,AuctionState.failed);
        post.setStatus(State.FINISHED);

        auctionRepository.save(auction);
        postRepository.save(post);
    }


    @Override
    public void stateChange(Long postId, AuctionState auctionState){
        List<Auction> auctionList = auctionRepository.findByPostId(postId);
        for(Auction auction : auctionList){
            if(auction.getAuctionState() == AuctionState.BIDDING){
                auction.getUser().setMoney(auction.getUser().getMoney() + auction.getPrice());
            }
            auction.setAuctionState(auctionState);
            auctionRepository.save(auction);
        }
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
                        .auctionState(auction.getAuctionState())
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
                        .auctionState(auction.getAuctionState())
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
                        .auctionState(auction.getAuctionState())
                        .createdAt(auction.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionDto> findByUserIdForMyPage(Long userId) {
        List<Auction> userAuctions  = auctionRepository.findByUserId(userId);

        Map<Long, Optional<Auction>> highestAuctionMap = userAuctions.stream()
                .collect(Collectors.groupingBy(auction -> auction.getPost().getId(),
                        Collectors.maxBy(Comparator.comparing(Auction::getCreatedAt))));

        List<Auction> highestAuctions = highestAuctionMap.values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return highestAuctions.stream()
                .map(auction -> AuctionDto.builder()
                        .id(auction.getId())
                        .post(auction.getPost().toDto())
                        .user(auction.getUser().toDto())
                        .price(auction.getPrice())
                        .auctionState(auction.getAuctionState())
                        .createdAt(auction.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

    }
}
