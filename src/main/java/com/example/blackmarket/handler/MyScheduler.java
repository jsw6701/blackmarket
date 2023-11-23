package com.example.blackmarket.handler;

import com.example.blackmarket.model.Post;
import com.example.blackmarket.repository.CategoryRepository;
import com.example.blackmarket.repository.PostRepository;
import com.example.blackmarket.repository.UserRepository;
import com.example.blackmarket.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Component
public class MyScheduler {

    private final PostService postService;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void updateDbValue() {

        postService.updatePostStatus();

        System.out.println("DB 값이 업데이트되었습니다.");
    }
}
