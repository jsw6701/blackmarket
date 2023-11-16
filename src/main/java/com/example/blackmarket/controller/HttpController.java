package com.example.blackmarket.controller;

import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
public class HttpController {

    @Autowired
    private HttpSession httpSession;

    @GetMapping(value = {"/", "/index", "/main"})
    public String index(Model model, @CurrentUser UserPrincipal userPrincipal) {
        boolean loginflag = false;
        if(userPrincipal != null){
            loginflag = true;
        }
        model.addAttribute("loginflag",loginflag);
        return "index";
    }


    @GetMapping("/ddd")
    public String originalPage() {
        // 다른 페이지로 리다이렉트

        return "redirect:/index";
    }


}