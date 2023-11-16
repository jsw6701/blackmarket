package com.example.blackmarket.repository;


import com.example.blackmarket.dto.response.AdminResp;
import com.example.blackmarket.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Page<Post> findByCategoryId(Long categoryId, Pageable pageable);

    List<AdminResp.AdminBoardSearchResqDto> findBoardByTitleAndContentAndUserName(String title, String content, String userName);

    void deleteById(Long id);
}
