package com.example.blackmarket.kakao;

import com.example.blackmarket.kakao.KakaoApproval.KakaoPayApproval;
import com.example.blackmarket.kakao.KakaoApproval.KakaoPayResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoPayService {

    private static final String HOST = "https://kapi.kakao.com";

    private final RestTemplate restTemplates;
    private KakaoPayResponse kakaoPayResponse;
    private KakaoPayApproval kakaoPayApproval;

    private final KakaoTransactionRepository kakaoTransactionRepository;

    @Value("fb3bbaaadb64f13ea12a6d2d6f056617")
    private String kakao_admin_key;

    public String doKakaoPay(String price) {

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakao_admin_key);
        headers.add("Accept", String.valueOf(MediaType.APPLICATION_JSON));
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "gorany");
        params.add("item_name", "코인 충전");
        params.add("quantity", "1");
        params.add("total_amount", price);
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://blackmarket-bm.shop/kakaoPaySuccess?price="+price);
        params.add("cancel_url", "http://blackmarket-bm.shop/kakaoPayCancel");
        params.add("fail_url", "http://blackmarket-bm.shop/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            //RestTemplate을 이용해 카카오페이에 데이터를 보내는 방법.
            kakaoPayResponse = restTemplates.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayResponse.class);
            return kakaoPayResponse.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "/pay";

    }

    public KakaoPayApproval kakaoPayApprove(String pg_token) {

        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakao_admin_key);
        headers.add("Accept", String.valueOf(MediaType.APPLICATION_JSON));
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayResponse.getTid());
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "gorany");
        params.add("pg_token", pg_token);
        params.add("total_amount", "50000");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        KakaoTransaction transaction = new KakaoTransaction();
        transaction.setTransactionId(kakaoPayResponse.getTid());
        transaction.setOrderId("1001"); // Or get it dynamically
        transaction.setUserId("gorany"); // Or get it dynamically
        transaction.setItemName("코인 충전"); // Or get it dynamically
        transaction.setQuantity(1); // Or get it dynamically
        transaction.setTotalAmount(2100.0); // Or get it dynamically

        kakaoTransactionRepository.save(transaction);

        try {
            kakaoPayApproval = restTemplates.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApproval.class);
            log.info("" + kakaoPayApproval);

            return kakaoPayApproval;

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
