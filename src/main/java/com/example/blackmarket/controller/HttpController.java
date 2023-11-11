package com.example.blackmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HttpController {

    @Autowired
    private HttpSession httpSession;
    @GetMapping(value = {"/", "/index", "/main"})
    public String index(Model model) {
        String email = (String) httpSession.getAttribute("email");

        boolean loginflag = false;
        if(email !=null && !email.isEmpty()){
            loginflag = true;
        }
        model.addAttribute("loginflag",loginflag);
        return "index";
    }


}