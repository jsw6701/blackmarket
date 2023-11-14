package com.example.blackmarket.model;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PostSpecification {


    public static Specification<Post> titleContains(String keyword) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + keyword + "%");
    }

    // 게시글 내용 검색
    public static Specification<Post> contentContains(String keyword) {
        return (root, query, builder) -> builder.like(root.get("content"), "%" + keyword + "%");
    }

    // 범위 내 가격 게시글 검색
    public static Specification<Post> priceRange(Long min, Long max) {
        return (root, query, builder) -> builder.between(root.get("biddingPrice"), min, max);
    }

    // 가격 price 이상 게시글 검색
    public static Specification<Post> priceMoreThan(Long min) {
        return (root, query, builder) -> builder.greaterThan(root.get("biddingPrice"), min);
    }

    // 마감 날짜 이내 게시글 검색
    public static Specification<Post> deadlineWithin(LocalDateTime deadline) {
        return (root, query, builder) -> builder.lessThan(root.get("targetDate"), deadline);
    }

    public static Specification<Post> categoryId(Category category) {
        return (root, query, builder) -> builder.equal(root.get("category").get("id"), category);
    }

    public static Specification<Post> userName(String name) {
        return (root, query, builder) -> builder.equal(root.get("user").get("name"), name);
    }
}
