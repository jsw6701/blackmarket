package com.example.blackmarket.controller;

import com.example.blackmarket.dto.requeset.PostUpdateDto;
import com.example.blackmarket.dto.response.PostDto;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.User;
import com.example.blackmarket.repository.UserRepository;
import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.security.UserPrincipal;
import com.example.blackmarket.service.BookMarkService;
import com.example.blackmarket.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
@AllArgsConstructor
public class HttpController {

    @Autowired
    private HttpSession httpSession;

    private BookMarkService bookMarkService;

    private PostService postService;
    private UserRepository userRepository;

    @GetMapping(value = {"/", "/index", "/main"})
    public String index(Model model, @CurrentUser UserPrincipal userPrincipal) {
        boolean loginflag = false;
        if(userPrincipal != null){
            loginflag = true;
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
            model.addAttribute("user",user);
        }
        model.addAttribute("loginflag",loginflag);
        return "index";
    }

    @GetMapping("/mypage")
    public String mypage(Model model, @CurrentUser UserPrincipal userPrincipal) {
        boolean loginflag = false;
        if(userPrincipal != null){
            loginflag = true;
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
            model.addAttribute("user",user);
        }

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        List<Post> post = postService.findPostListByUser(user);

        model.addAttribute("loginflag",loginflag);
        model.addAttribute("sellCount", post != null ? post.size() : 0 );

        return "mypage";
    }




    @GetMapping("/charge")
    public String charge(Model model, @CurrentUser UserPrincipal userPrincipal) {
        boolean loginflag = false;
        if(userPrincipal != null){

            loginflag = true;
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

            model.addAttribute("user",user);
        }else{
            return "redirect:/index";
        }

        model.addAttribute("loginflag",loginflag);
        return "charge";
    }

    @GetMapping("/ddd")
    public String originalPage() {
        // 다른 페이지로 리다이렉트

        return "redirect:/index";
    }

    @PatchMapping("bookmark/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @CurrentUser User user){
        bookMarkService.save(postId, user);

        return ResponseEntity.ok("success");

    }


}