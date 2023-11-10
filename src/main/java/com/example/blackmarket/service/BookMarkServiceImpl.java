package com.example.blackmarket.service;

import com.example.blackmarket.dto.response.BookMarkDto;
import com.example.blackmarket.model.BookMark;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.User;
import com.example.blackmarket.repository.BookMarkRepository;
import com.example.blackmarket.repository.PostRepository;
import com.example.blackmarket.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookMarkServiceImpl implements BookMarkService{

    private final BookMarkRepository bookMarkRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Override
    public void save(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if(bookMarkRepository.findByPostAndUser(post, user) == null){
            BookMark bookMark = new BookMark(post, user);
            bookMarkRepository.save(bookMark);
        }
        else{
            BookMark bookMark = bookMarkRepository.findByPostAndUser(post, user);
            bookMarkRepository.delete(bookMark);
            bookMarkRepository.save(bookMark);
        }
    }
    @Override
    public BookMarkDto findById(Long id) {
        BookMark bookMark = bookMarkRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 북마크가 없습니다."));

        return BookMarkDto.builder()
                .id(bookMark.getId())
                .post(bookMark.getPost().toDto())
                .user(bookMark.getUser().toDto())
                .build();
    }

    @Override
    public List<BookMarkDto> findByUser(User user){
        List<BookMark> bookMarks = bookMarkRepository.findByUser(user);

        return bookMarks.stream().map(bookMark -> BookMarkDto.builder()
                .id(bookMark.getId())
                .post(bookMark.getPost().toDto())
                .user(bookMark.getUser().toDto())
                .build()).collect(Collectors.toList());
    }
}
