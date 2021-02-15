package com.devwue.spring.api.repository;

import com.devwue.spring.dto.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long> {
    Optional<Post> findAllByPostNo(String postNo);
    Optional<Post> findTopByNameOrderByIdDesc(String name);
    List<Post> findAllByName(String name);
}
