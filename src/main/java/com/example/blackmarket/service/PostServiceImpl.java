package com.example.blackmarket.service;


import com.example.blackmarket.dto.requeset.PostCreateDto;
import com.example.blackmarket.dto.requeset.PostUpdateDto;
import com.example.blackmarket.model.Category;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.model.State;
import com.example.blackmarket.model.User;
import com.example.blackmarket.repository.CategoryRepository;
import com.example.blackmarket.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    }

    @Override
    public Post createPost(PostCreateDto postCreateDto, User user) {

        Category category = categoryRepository.findById(postCreateDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        Post post = Post.builder()
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .targetDate(postCreateDto.getTargetDate())
                .immediatePurchasePrice(postCreateDto.getImmediatePurchasePrice())
                .biddingPrice(postCreateDto.getBiddingPrice())
                .biddingUnit(postCreateDto.getBiddingUnit())
                .category(category)
                .user(user)
                .build();

        post.setStatus(State.WAITING);

        postRepository.save(post);

        return post;
    }

    @Override
    public Post updatePost(PostUpdateDto postUpdateDto, Long postId, User user) {

        Category category = categoryRepository.findById(postUpdateDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if(!post.getUser().getId().equals(user.getId())){
            throw new IllegalStateException("해당 글을 수정할 권한이 없습니다.");
        }

        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());
        post.setTargetDate(postUpdateDto.getTargetDate());
        post.setImmediatePurchasePrice(postUpdateDto.getImmediatePurchasePrice());
        post.setBiddingPrice(postUpdateDto.getBiddingPrice());
        post.setBiddingUnit(postUpdateDto.getBiddingUnit());
        post.setCategory(category);

        postRepository.save(post);

        return post;
    }

    @Override
    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if(!post.getUser().getId().equals(user.getId())){
            throw new IllegalStateException("해당 글을 삭제할 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    @Override
    public Page<Post> findPostList(Pageable pageable) {
        Page<Post> postList = postRepository.findAll(pageable);
        return postList;
    }

    @Override
    public Page<Post> findPostByCategoryId(Long categoryId, Pageable pageable) {
        Page<Post> postList = postRepository.findByCategoryId(categoryId, pageable);
        return postList;
    }
}
