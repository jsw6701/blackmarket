package com.example.blackmarket.kakao;

import com.example.blackmarket.model.User;
import com.example.blackmarket.repository.UserRepository;
import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
//@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class kakaoController {
    private final KakaoPayService kakaoPayService;
    private UserRepository userRepository;
    @GetMapping("kakaopay")
    public String doKakaoPay(@RequestParam(name = "price", required = false) String price) {

        return "redirect:" + kakaoPayService.doKakaoPay(price);
    }

    @PostMapping("/kakaoPay")
    public String kakaoPay() {
        log.info("kakaoPay post............................................");

        return "redirect:" + kakaoPayService.doKakaoPay("");

    }

//    @GetMapping("kakaoPaySuccess")
//    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
//        log.info("kakaoPaySuccess get............................................");
//        log.info("kakaoPaySuccess pg_token : " + pg_token);
//
//        model.addAttribute("info", kakaoPayService.kakaoPayApprove(pg_token));
//    }
    @GetMapping("kakaoPaySuccess")
    public ResponseEntity<String> kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, @CurrentUser UserPrincipal userPrincipal,@RequestParam(name = "price", required = false) String price) {
        String closePopupScript = "<script>window.close();</script>";
        if(userPrincipal != null){
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
            user.setMoney(user.getMoney()+ Long.parseLong(price));
            userRepository.save(user);
        }

        // 응답에 스크립트 추가
        return new ResponseEntity<>(closePopupScript, HttpStatus.OK);
    }


}
