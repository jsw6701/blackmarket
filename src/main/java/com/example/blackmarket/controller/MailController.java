package com.example.blackmarket.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import com.example.blackmarket.dto.response.MailDto;
import com.example.blackmarket.exception.CustomException;
import com.example.blackmarket.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.blackmarket.model.User;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService;

    @Autowired
    private HttpSession session;

    @PostMapping("/admin/mail")
    public String execMail(MailDto mailDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        if( mailDto.getAddress() == null || mailDto.getAddress() == "" || mailDto.getAddress().isEmpty()){
            throw new CustomException("이메일을 입력해 주세요");
        }
        try {
            mailService.mailSend(mailDto);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/user";
    }
}
