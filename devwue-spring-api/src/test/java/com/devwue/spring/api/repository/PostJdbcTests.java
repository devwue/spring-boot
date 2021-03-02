package com.devwue.spring.api.repository;

import com.devwue.spring.api.config.JpaConfiguration;
import com.devwue.spring.dto.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest
public class PostJdbcTests {
    @Autowired
    private PostJdbc postJdbc;

    @Test
    public void 생성() {
        Post post = new Post();
        post.setName("test");
        post.setContents("test test");
        post.setType("test");
        post.setPostNo(post.getType() + "10");
        post = postJdbc.create(post);
        assertThat(post).isInstanceOf(Post.class);
    }

    @Test
    public void 조회() {
        List<Post> list= postJdbc.findAll();
        assertThat(list).hasSizeBetween(0, list.size());
    }
}
