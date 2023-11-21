package com.example.blackmarket.controller;

import com.example.blackmarket.dto.requeset.PostCreateDto;
import com.example.blackmarket.dto.requeset.PostUpdateDto;
import com.example.blackmarket.dto.response.AuctionDto;
import com.example.blackmarket.dto.response.PostDto;
import com.example.blackmarket.model.*;
import com.example.blackmarket.repository.AuctionRepository;
import com.example.blackmarket.repository.CategoryRepository;
import com.example.blackmarket.repository.PostRepository;
import com.example.blackmarket.repository.UserRepository;
import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.security.UserPrincipal;
import com.example.blackmarket.service.AuctionService;
import com.example.blackmarket.service.FileService;
import com.example.blackmarket.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class PostController {
    private final PostService postService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final FileService fileService;
    private final AuctionRepository auctionRepository;
    private final AuctionService auctionService;

    @Operation(summary = "게시글 생성")
    @PostMapping("/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateDto postCreateDto, @CurrentUser UserPrincipal userPrincipal){

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        PostDto post = postService.createPost(postCreateDto, user);

        return ResponseEntity.ok(post);
    }

    @Operation(summary = "게시글 생성1")
    @PostMapping("/post1")
    public String createPost1(@ModelAttribute  PostCreateDto postCreateDto, @CurrentUser UserPrincipal userPrincipal){

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n*********************************************************");
        System.out.println("title : "+postCreateDto.getTitle());
        System.out.println("날짜 : " + LocalDate.parse(postCreateDto.getTargetDate()).atStartOfDay());
        System.out.println("content : "+postCreateDto.getContent());

        fileService.uploadFiles(postCreateDto.getFiles());

        System.out.println();

        System.out.println("*********************************************************\n\n\n\n\\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        PostDto post = postService.createPost(postCreateDto, user);
        return "redirect:/index";
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
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "12"))
    })
    @GetMapping("post/readAll")
    public ResponseEntity<Page<PostDto>> findPostList(Pageable pageable){
        Page<PostDto> postList = postService.findPostList(pageable);
        return ResponseEntity.ok(postList);
    }
    @GetMapping("post/readAll2")
    public String findPostList(Pageable pageable, Model model,HttpServletRequest request){
        Page<PostDto> postList = postService.findPostList(pageable);

        model.addAttribute("postList", postList);
        return "AJAX/card_list";
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("post/{postId}")
    public ResponseEntity<PostDto> findPostById(@PathVariable Long postId){
        Post post = postService.findById(postId);
        post.setViewCount(post.getViewCount()+1L);
        postRepository.save(post);

        return ResponseEntity.ok(new PostDto(post));
    }



    @Operation(summary = "카테고리별 게시글 목록 조회")
    @Parameters({
            @Parameter(name = "sort", description = "sort specification",
                    in = ParameterIn.QUERY, schema = @Schema(type = "createDate,desc"), example = "createDate,desc"),
            @Parameter(name = "page", description = "page number",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "page size",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "12"))
    })
    @GetMapping("post/category/{categoryId}")
    public ResponseEntity<Page<PostDto>> findPostByCategoryId(@PathVariable Long categoryId, Pageable pageable){
        Page<PostDto> postList = postService.findPostByCategoryId(categoryId, pageable);
        return ResponseEntity.ok(postList);
    }


    @Operation(summary = "마감임박순 게시글 목록 조회")
    @Parameters({
            @Parameter(name = "sort", description = "sort specification",
                    in = ParameterIn.QUERY, schema = @Schema(type = "targetDate,desc"), example = "targetDate,desc"),
            @Parameter(name = "page", description = "page number",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "page size",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "12"))
    })
    @GetMapping("post/deadline1")
    public ResponseEntity<Page<PostDto>> findPostByDeadline(Pageable pageable){
        Page<PostDto> postList = postService.findPostList(pageable);
        return ResponseEntity.ok(postList);
    }


    @Operation(summary = "마감임박 검색 필터")
    @Parameters({
            @Parameter(name = "sort", description = "sort specification",
                    in = ParameterIn.QUERY, schema = @Schema(type = "targetDate,desc"), example = "targetDate,desc"),
            @Parameter(name = "page", description = "page number",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "page size",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "12"))
    })
    @GetMapping("post/deadline")
    public String deadline(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
//            @RequestParam(value = "targetDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDate,
            @RequestParam(value = "targetDate", required = false) String targetDate,
            @RequestParam(value = "biddingPrice", required = false) Long biddingPrice,
            @RequestParam(value = "MaxBiddingPrice", required = false,defaultValue = "0") Long MaxBiddingPrice,
            @RequestParam(value = "MinBiddingPrice", required = false) Long MinBiddingPrice,
            @RequestParam(value = "immediatePurchasePrice", required = false) Long immediatePurchasePrice,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "12") int size,
            Model model,
            @CurrentUser UserPrincipal userPrincipal) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("targetDate").ascending());

        boolean loginflag = false;
        if(userPrincipal != null){
            loginflag = true;
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
            model.addAttribute("user",user);
        }

        model.addAttribute("loginflag",loginflag);

        Specification<Post> spec = ((root, query, criteriaBuilder) -> null);

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));
            spec = spec.and(PostSpecification.categoryId(category));
        }
        if (title != null) {
            spec = spec.and(PostSpecification.titleContains(title));
        }
        if (content != null) {
            spec = spec.and(PostSpecification.contentContains(content));
        }

        spec = spec.and(PostSpecification.deadlineUpper(LocalDateTime.now()));
        if (targetDate != null && targetDate != "") {
//            LocalDate.parse(targetDate).atStartOfDay();
            spec = spec.and(PostSpecification.deadlineWithin(LocalDate.parse(targetDate).atStartOfDay()));
        }
        if (biddingPrice != null) {
            if (biddingPrice < 10000001L) {
                spec = spec.and(PostSpecification.priceLessThan(biddingPrice));
            } else {
                biddingPrice = 9999999L;
                spec = spec.and(PostSpecification.priceMoreThan(biddingPrice));
            }
        }
        if (MaxBiddingPrice != null && MinBiddingPrice != null) {
            spec = spec.and(PostSpecification.priceRange(MinBiddingPrice, MaxBiddingPrice));
        }

        if (immediatePurchasePrice != null){
            spec = spec.and(PostSpecification.immediateMoreThan(immediatePurchasePrice));
        }

        if (name != null) {
            String userName = userRepository.findByName(name).getName();
            spec = spec.and(PostSpecification.userName(userName));
        }
        Page<Post> boardList = postRepository.findAll(spec,pageable);
        List<PostDto> postList = boardList.stream().map(PostDto::new).collect(Collectors.toList());

        model.addAttribute("currentPage", boardList.getNumber()); // 현재 페이지 번호
        model.addAttribute("totalPages", boardList.getTotalPages()); // 전체 페이지 수
        model.addAttribute("postList", postList);
        return "item";
    }



    @Operation(summary = "검색 필터")
    @GetMapping("post/searchPost")
    public String findSearch(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
//            @RequestParam(value = "targetDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDate,
            @RequestParam(value = "targetDate", required = false) String targetDate,
            @RequestParam(value = "biddingPrice", required = false) Long biddingPrice,
            @RequestParam(value = "MaxBiddingPrice", required = false,defaultValue = "0") Long MaxBiddingPrice,
            @RequestParam(value = "MinBiddingPrice", required = false) Long MinBiddingPrice,
            @RequestParam(value = "immediatePurchasePrice", required = false) Long immediatePurchasePrice,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "12") int size,
            Model model,
            @CurrentUser UserPrincipal userPrincipal) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("viewCount").descending());

        boolean loginflag = false;
        if(userPrincipal != null){
            loginflag = true;
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
            model.addAttribute("user",user);
        }

        model.addAttribute("loginflag",loginflag);

        Specification<Post> spec = ((root, query, criteriaBuilder) -> null);

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));
            spec = spec.and(PostSpecification.categoryId(category));
        }
        if (title != null) {
            spec = spec.and(PostSpecification.titleContains(title));
        }
        if (content != null) {
            spec = spec.and(PostSpecification.contentContains(content));
        }
        if (targetDate != null && targetDate != "") {
//            LocalDate.parse(targetDate).atStartOfDay();
            spec = spec.and(PostSpecification.deadlineWithin(LocalDate.parse(targetDate).atStartOfDay()));
        }
        if (biddingPrice != null) {
            if (biddingPrice < 10000001L) {
                spec = spec.and(PostSpecification.priceLessThan(biddingPrice));
            } else {
                biddingPrice = 9999999L;
                spec = spec.and(PostSpecification.priceMoreThan(biddingPrice));
            }
        }
        if (MaxBiddingPrice != null && MinBiddingPrice != null) {
            spec = spec.and(PostSpecification.priceRange(MinBiddingPrice, MaxBiddingPrice));
        }

        if (immediatePurchasePrice != null){
            spec = spec.and(PostSpecification.immediateMoreThan(immediatePurchasePrice));
        }

        if (name != null) {
            String userName = userRepository.findByName(name).getName();
            spec = spec.and(PostSpecification.userName(userName));
        }
        Page<Post> boardList = postRepository.findAll(spec,pageable);
        List<PostDto> postList = boardList.stream().map(PostDto::new).collect(Collectors.toList());

        model.addAttribute("currentPage", boardList.getNumber()); // 현재 페이지 번호
        model.addAttribute("totalPages", boardList.getTotalPages()); // 전체 페이지 수
        model.addAttribute("postList", postList);
        return "item";
    }


    @Operation(summary = "검색 필터")
    @GetMapping("post/search")
    public String findFilter(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "targetDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDate,
            @RequestParam(value = "biddingPrice", required = false) Long biddingPrice,
            @RequestParam(value = "MaxBiddingPrice", required = false) Long MaxBiddingPrice,
            @RequestParam(value = "MinBiddingPrice", required = false) Long MinBiddingPrice,
            @RequestParam(value = "name", required = false) String name,
            Model model) {

        Specification<Post> spec = ((root, query, criteriaBuilder) ->  null);

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));
            spec = spec.and(PostSpecification.categoryId(category));
        }
        if (title != null) {
            spec = spec.and(PostSpecification.titleContains(title));
        }
        if (content != null) {
            spec = spec.and(PostSpecification.contentContains(content));
        }
        if (targetDate != null) {
            spec = spec.and(PostSpecification.deadlineWithin(targetDate));
        }
        if (biddingPrice != null) {
            spec = spec.and(PostSpecification.priceMoreThan(biddingPrice));
        }
        if (MaxBiddingPrice != null && MinBiddingPrice != null) {
            spec = spec.and(PostSpecification.priceRange(MinBiddingPrice, MaxBiddingPrice));
        }
        if (name != null) {
            String userName = userRepository.findByName(name).getName();
            spec = spec.and(PostSpecification.userName(userName));
        }
        List<Post> boardList = postRepository.findAll(spec);
        List<PostDto> boardList2 = boardList.stream().map(PostDto::new).collect(Collectors.toList());
        model.addAttribute("boardList2", boardList2);
        return "admin/board";
    }

    @GetMapping("/mypage1")
    public String mypage1(Model model, @CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        List<Post> postList = postService.findPostListByUser(user);

        model.addAttribute("postList", postList);

        return "AJAX/mypage1";
    }

    @GetMapping("/mypage2")
    public String mypage2(Model model, @CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
//        List<Post> postList = auctionRepository.findDistinctByUser(user);

        List<AuctionDto> auction = auctionService.findByUserIdForMyPage(user.getId());

        model.addAttribute("auctionList", auction);

        return "AJAX/mypage2";
    }

    @GetMapping("/mypage3")
    public String mypage3(Model model, @CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        List<Auction> auctionList = auctionRepository.findByUserIdAndAuctionState(user.getId(),AuctionState.Waiting);

        model.addAttribute("auctionList", auctionList);

        return "AJAX/mypage3";
    }

    @GetMapping("/mypage4")
    public String mypage4(Model model, @CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        List<Post> postList = auctionRepository.findDistinctByUser(user);

        model.addAttribute("postList", postList);

        return "AJAX/mypage1";
    }

    @GetMapping("/mypage5")
    public String mypage5(Model model, @CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        List<Auction> auctionList = auctionRepository.findByUserIdAndAuctionState(user.getId(),AuctionState.COMPLETED);

        model.addAttribute("auctionList", auctionList);

        return "AJAX/mypage5";
    }


}
