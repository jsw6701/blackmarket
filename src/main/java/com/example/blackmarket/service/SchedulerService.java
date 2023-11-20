package com.example.blackmarket.service;

import com.example.blackmarket.repository.AuctionRepository;
import com.example.blackmarket.repository.CategoryRepository;
import com.example.blackmarket.repository.PostRepository;
import com.example.blackmarket.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SchedulerService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    private final AuctionRepository auctionRepository;

    private final PostService postService;


    @Scheduled(cron = "0 0 0 * * *")
    public void runScheduledTask() {



    }
}