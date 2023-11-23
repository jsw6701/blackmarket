package com.example.blackmarket.model;

public enum AuctionState {
    BIDDING, // 입찰중 ( 제일 높은 입찰 )
    COMPLETED, // 거래 완료
    LOWER, // 하위 입찰

    immediate, // 즉시 구매
    failed, //즉시 구매로 인한 실패
    Waiting, //결제 대기

}
