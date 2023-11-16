package com.example.blackmarket.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import com.example.blackmarket.dto.ResponseDto;
import com.example.blackmarket.dto.requeset.AdminReq;
import com.example.blackmarket.dto.requeset.AdminReq.AdminReqDto;
import com.example.blackmarket.dto.response.AdminResp;
import com.example.blackmarket.dto.response.PostDto;
import com.example.blackmarket.dto.response.UserDto;
import com.example.blackmarket.exception.CustomApiException;
import com.example.blackmarket.exception.CustomException;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.repository.PostRepository;
import com.example.blackmarket.repository.UserRepository;
import com.example.blackmarket.security.TokenProvider;
import com.example.blackmarket.service.PostService;
import com.example.blackmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.blackmarket.model.User;


@Controller
public class AdminController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;
    @GetMapping("/admin/loginForm")
    public String loginForm() {
        return "admin/loginForm";
    }

    @GetMapping("/admin")
    public String admin() {
        User principal = (User) session.getAttribute("principal");

        if (principal == null) {
            return "redirect:/admin/loginForm";
        }
        if (!principal.getRole().equals("ADMIN")) {
            return "redirect:/admin/loginForm";
        }
        return "admin/user";
    }

    @GetMapping("/admin/user")
    public String manageUser(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/admin/loginForm";
        }
        if (!principal.getRole().equals("ADMIN")) {
            return "redirect:/admin/loginForm";
        }
        List<User> userList = userRepository.findAll();
        List<UserDto> userList2 = userList.stream().map(UserDto::new).collect(Collectors.toList());

        model.addAttribute("userList2", userList2);
        return "admin/user";
    }

    @GetMapping("/admin/board")
    public String manageBoard(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/admin/loginForm";
        }
        if (!principal.getRole().equals("ADMIN")) {
            return "redirect:/admin/loginForm";
        }
        List<Post> boardList = postRepository.findAll();
        List<PostDto> boardList2 = boardList.stream().map(PostDto::new).collect(Collectors.toList());
        model.addAttribute("boardList2", boardList2);
        return "admin/board";
    }

    @PostMapping("/admin/login")
    public String loginAdmin(@Valid AdminReqDto adminReqDto, Error error, Model model) {
        User admin = userRepository.findBynameAndPassword(adminReqDto.getName(), adminReqDto.getPassword());
        if (admin == null) {
            throw new CustomException("아이디 또는 비밀번호가 다릅니다.");
        }
        if (!admin.getRole().equals("ADMIN")) {
            throw new CustomException("관리자 계정이 아닙니다.");
        }
        session.setAttribute("principal", admin);
        // 회원정보 가져오기
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "redirect:/admin/user";
    }
    @GetMapping("/admin/board/search")
    public ResponseEntity<?> searchBoard(AdminReq.AdminReqSearchAjaxDto aDto) {
        User principal = (User) session.getAttribute("principal");

        if (!principal.getRole().equals("ADMIN")) {
            throw new CustomApiException("관리자 계정이 아닙니다.");
        }
        if (aDto.getTitle() == null || aDto.getTitle().isEmpty()) {
            aDto.setTitle("");
        }
        if (aDto.getContent() == null || aDto.getContent().isEmpty()) {
            aDto.setContent("");
        }
        if (aDto.getName() == null || aDto.getName().isEmpty()) {
            aDto.setName("");
        } else {
            Long num = userRepository.findIdByName(aDto.getName());
            System.out.println("테스트 : "+num);
            if (num == null) {
                aDto.setName("결과없음");
            } else {
                aDto.setName(String.valueOf(num));
            }
        }
        List<AdminResp.AdminBoardSearchResqDto> boardSeartList = postRepository.findBoardByTitleAndContentAndUserName(
                aDto.getTitle(),
                aDto.getContent(),
                aDto.getName());
        // json 에 데이터 넣어줘야함
        return new ResponseEntity<>(new ResponseDto<>(1, "", boardSeartList),
                HttpStatus.OK);
    }

    @GetMapping("/admin/delete/user")
    public String delateUser(Long id) {
        if (id == null) {
            throw new CustomApiException("삭제할 회원 아이디가 비었습니다.");
        }
        User admin = (User) session.getAttribute("principal");
        if (!admin.getRole().equals("ADMIN")) {
            throw new CustomApiException("관리자 계정이 아닙니다.");
        }
        userService.deleteById(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/delete")
    public String delateBoard(Long id) {
        User principal = (User) session.getAttribute("principal");
        if (!principal.getRole().equals("ADMIN")) {
            throw new CustomApiException("관리자 계정이 아닙니다.");
        }
        if (id == null) {
            throw new CustomApiException("삭제할 게시글 아이디가 비었습니다.");
        }
        postService.deleteById(id, principal.getId());
        return "redirect:/admin/board";
    }
}

