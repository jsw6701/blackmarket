package com.example.blackmarket.controller;

import com.example.blackmarket.dto.response.UserDto;
import com.example.blackmarket.exception.ResourceNotFoundException;
import com.example.blackmarket.model.*;
import com.example.blackmarket.repository.UserRepository;
import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @Operation(summary = "검색 필터")
    @GetMapping("user/search")
    public String findFilter(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "role", required = false) String role,
            Model model) {

        Specification<User> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(UserSpecification.nameContains(name));
        }
        if (email != null) {
            spec = spec.and(UserSpecification.emailContains(email));
        }
        if (role != null) {
            spec = spec.and(UserSpecification.roleContains(role));
        }

        List<User> userList = userRepository.findAll(spec);
        List<UserDto> userList2 = userList.stream().map(UserDto::new).collect(Collectors.toList());
        model.addAttribute("userList2", userList2);
        return "admin/user";
    }

    @GetMapping("/user/modify")
    public String someMethod(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal != null) {
            model.addAttribute("isUserLoggedIn", true);
            User user = userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
            model.addAttribute("currentUser", user);
        } else {
            model.addAttribute("isUserLoggedIn", false);
        }
        return "yourTemplate";
    }
}
