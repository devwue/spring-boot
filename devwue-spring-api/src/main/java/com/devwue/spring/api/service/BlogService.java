package com.devwue.spring.api.service;

import com.devwue.spring.api.dto.request.PostCreateRequest;
import com.devwue.spring.api.dto.request.PostSearchRequest;
import com.devwue.spring.api.dto.response.PostTypeResponse;
import com.devwue.spring.api.repository.PostSearchRepositoryImpl;
import com.devwue.spring.api.repository.PostJpaRepository;
import com.devwue.spring.dto.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BlogService {
    private final PostJpaRepository postJpaRepository;
    private final PostSearchRepositoryImpl postSearchRepository;

    @Autowired
    public BlogService(PostJpaRepository postJpaRepository, PostSearchRepositoryImpl postSearchRepository) {
        this.postJpaRepository = postJpaRepository;
        this.postSearchRepository = postSearchRepository;
    }

    public List<Post> findByName(String name) {
        return postJpaRepository.findAllByName(name);
    }

    public Post findPostByPostNo(String postNo) throws Exception {
        Optional<Post> post = postJpaRepository.findAllByPostNo(postNo);
        post.orElseThrow(() -> new Exception(postNo));
        return post.get();
    }

    @Transactional
    public Object createPost(PostCreateRequest request) {
        Optional<Post> postList = postJpaRepository.findTopByNameOrderByIdDesc(request.getName());

        Post post = new Post();
        post.setPostNo(request.getName() + postList.map(p -> p.getId() + 1).orElse(1L));
        post.setName(request.getName());
        post.setContents(request.getContents());
        postJpaRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public Page<Post> searchPost(PostSearchRequest request, Pageable pageable) {
        return postSearchRepository.findByName(request, pageable);
    }

    @Transactional(readOnly = true)
    public List<PostTypeResponse> getSummaryType() {
        return postSearchRepository.getSummaryType();
    }
}
