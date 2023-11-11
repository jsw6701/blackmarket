package com.example.blackmarket.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@Slf4j
public class kakaoController {
    private final KakaoPayService kakaoPayService;
    @GetMapping("kakaopay")
    public String doKakaoPay() {

        return "redirect:" + kakaoPayService.doKakaoPay();
    }

    @PostMapping("/kakaoPay")
    public String kakaoPay() {
        log.info("kakaoPay post............................................");

        return "redirect:" + kakaoPayService.doKakaoPay();

    }

    @GetMapping("kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);

        model.addAttribute("info", kakaoPayService.kakaoPayApprove(pg_token));

    }

}
