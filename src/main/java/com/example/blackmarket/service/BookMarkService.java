package com.example.blackmarket.service;

import com.example.blackmarket.dto.response.BookMarkDto;
import com.example.blackmarket.model.User;

import java.util.List;

public interface BookMarkService {

    BookMarkDto findById(Long id);

    void save(Long postId, User user);

    List<BookMarkDto> findByUser(User user);
}
