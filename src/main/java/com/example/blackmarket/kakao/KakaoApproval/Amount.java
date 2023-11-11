package com.example.blackmarket.kakao.KakaoApproval;

import lombok.Data;

@Data
public class Amount {
    private Integer total;
    private Integer tax_free;
    private Integer vat;
    private Integer point;
    private Integer discount;
    private Integer green_deposit;
}
