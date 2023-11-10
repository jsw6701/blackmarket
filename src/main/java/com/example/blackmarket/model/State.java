package com.example.blackmarket.model;

public enum State {
    WAITING, // 입찰 대기
    BIDDING, // 입찰 중
    BIDDED, // 입찰 완료
    COMPLETED, // 거래 완료
    FINISHED, // 입찰 종료
}
