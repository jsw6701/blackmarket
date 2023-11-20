package com.example.blackmarket.service;


import com.example.blackmarket.dto.requeset.PostCreateDto;
import com.example.blackmarket.dto.requeset.PostUpdateDto;
import com.example.blackmarket.dto.response.PostDto;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Post findById(Long id);

    PostDto createPost(PostCreateDto postCreateDto, User user);

    Post updatePost(PostUpdateDto postUpdateDto, Long postId, User user);

    void deletePost(Long postId, User user);

    void deleteById(Long id, Long principalId);

    Page<PostDto> findPostList(Pageable pageable);

    Page<PostDto> findPostByCategoryId(Long categoryId, Pageable pageable);

    List<Post> findPostListByUser(User user);

    void updatePostStatus();
}
