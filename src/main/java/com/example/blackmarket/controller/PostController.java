package com.example.blackmarket.controller;

import com.example.blackmarket.dto.requeset.PostCreateDto;
import com.example.blackmarket.dto.requeset.PostUpdateDto;
import com.example.blackmarket.dto.response.PostDto;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.User;
import com.example.blackmarket.repository.UserRepository;
import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.security.UserPrincipal;
import com.example.blackmarket.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
public class PostController {
    private final PostService postService;
    private final UserRepository userRepository;

    @Operation(summary = "게시글 생성")
    @PostMapping("/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateDto postCreateDto, @CurrentUser UserPrincipal userPrincipal){

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        Post post = postService.createPost(postCreateDto, user);
        return ResponseEntity.ok(new PostDto(post));
    }

    @Operation(summary = "게시글 수정")
    @PatchMapping("post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostUpdateDto postUpdateDto, @PathVariable Long postId, @CurrentUser User user){
        Post post = postService.updatePost(postUpdateDto, postId, user);
        return ResponseEntity.ok(new PostDto(post));
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, User user){
        postService.deletePost(postId, user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 목록 조회")
    @Parameters({
            @Parameter(name = "sort", description = "sort specification",
                    in = ParameterIn.QUERY, schema = @Schema(type = "createDate,desc"), example = "createDate,desc"),
            @Parameter(name = "page", description = "page number",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "page size",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "5"))
    })
    @GetMapping("post/readAll")
    public ResponseEntity<Page<PostDto>> findPostList(Pageable pageable){
        Page<Post> postList = postService.findPostList(pageable);
        Page<PostDto> postDtoList = postList.map(PostDto::new);
        return ResponseEntity.ok(postDtoList);
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("post/{postId}")
    public ResponseEntity<PostDto> findPostById(@PathVariable Long postId){
        Post post = postService.findById(postId);
        return ResponseEntity.ok(new PostDto(post));
    }


    @Operation(summary = "카테고리별 게시글 목록 조회")
    @Parameters({
            @Parameter(name = "sort", description = "sort specification",
                    in = ParameterIn.QUERY, schema = @Schema(type = "createDate,desc"), example = "createDate,desc"),
            @Parameter(name = "page", description = "page number",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "page size",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "5"))
    })
    @GetMapping("post/category/{categoryId}")
    public ResponseEntity<Page<PostDto>> findPostByCategoryId(@PathVariable Long categoryId, Pageable pageable){
        Page<Post> postList = postService.findPostByCategoryId(categoryId, pageable);
        Page<PostDto> postDtoList = postList.map(PostDto::new);
        return ResponseEntity.ok(postDtoList);
    }
}
