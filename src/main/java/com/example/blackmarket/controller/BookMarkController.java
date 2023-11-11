package com.example.blackmarket.controller;

import com.example.blackmarket.dto.response.BookMarkDto;
import com.example.blackmarket.model.User;
import com.example.blackmarket.repository.UserRepository;
import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.security.UserPrincipal;
import com.example.blackmarket.service.BookMarkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class BookMarkController {
    private final BookMarkService bookMarkService;

    private final UserRepository userRepository;

    @Operation(summary = "북마크 생성")
    @PostMapping("/bookmark/create/{postId}")
    public ResponseEntity<?> createBookMark(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long postId){
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        bookMarkService.save(postId, user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 북마크 조회")
    @GetMapping("/bookmark/readByUser")
    public ResponseEntity<List<BookMarkDto>> readBookMarkByUser(@CurrentUser UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        List<BookMarkDto> bookMarkDtoList = bookMarkService.findByUser(user);
        return ResponseEntity.ok(bookMarkDtoList);
    }

    @Operation(summary = "북마크 조회")
    @GetMapping("/bookmark/read/{bookmarkId}")
    public ResponseEntity<BookMarkDto> readBookMark(@PathVariable Long bookmarkId){
        BookMarkDto bookMarkDto = bookMarkService.findById(bookmarkId);
        return ResponseEntity.ok(bookMarkDto);
    }
}
