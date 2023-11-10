package com.example.blackmarket.kakao;


import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoTransactionRepository extends JpaRepository<KakaoTransaction, Long> {
}
