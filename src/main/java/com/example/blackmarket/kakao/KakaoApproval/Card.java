package com.example.blackmarket.kakao.KakaoApproval;

import lombok.Data;

@Data
public class Card {

    private String purchase_corp;
    private String purchase_corp_code;
    private String issuer_corp;
    private String issuer_corp_code;
    private String bin, card_type, install_month, approved_id, card_mid;
    private String interest_free_install, card_item_code;
}
