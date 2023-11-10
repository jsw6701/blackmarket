package com.example.blackmarket.repository;

import com.example.blackmarket.model.BookMark;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    BookMark findByPostAndUser(Post post, User user);

    List<BookMark> findByUser(User user);
}
