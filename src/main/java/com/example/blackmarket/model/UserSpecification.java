package com.example.blackmarket.model;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> nameContains(String keyword) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + keyword + "%");
    }

    public static Specification<User> emailContains(String keyword) {
        return (root, query, builder) -> builder.like(root.get("email"), "%" + keyword + "%");
    }

    public static Specification<User> roleContains(String keyword) {
        return (root, query, builder) -> builder.like(root.get("role"), "%" + keyword + "%");
    }
}
