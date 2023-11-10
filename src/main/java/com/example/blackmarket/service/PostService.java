package com.example.blackmarket.service;


import com.example.blackmarket.dto.requeset.PostCreateDto;
import com.example.blackmarket.dto.requeset.PostUpdateDto;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post findById(Long id);

    Post createPost(PostCreateDto postCreateDto, User user);

    Post updatePost(PostUpdateDto postUpdateDto, Long postId, User user);

    void deletePost(Long postId, User user);

    Page<Post> findPostList(Pageable pageable);

    Page<Post> findPostByCategoryId(Long categoryId, Pageable pageable);

    Post updatePostBiddingPrice(Long postId, User user);
}
