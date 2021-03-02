package com.devwue.spring.api.repository;

import com.devwue.spring.dto.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostJdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String CREATE = "insert into posts(contents, name, type, post_no, created_at, updated_at) value (?,?,?,?,?,?)";
    private final String FIND_ALL = "select * from posts";

    public Post create(Post post) {
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
                PreparedStatement ps = con.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, post.getContents());
                ps.setString(2, post.getName());
                ps.setString(3, post.getType());
                ps.setString(4, post.getPostNo());
                ps.setTimestamp(5, ts);
                ps.setTimestamp(6, ts);
                return ps;
            }
        }, holder);

        Long id = holder.getKey().longValue();
        post.setId(id);

        return post;
    }

    public List<Post> findAll() {
        return jdbcTemplate.query(FIND_ALL, new RowMapper<Post>() {
            @Override
            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setPostNo(rs.getString("post_no"));
                post.setType(rs.getString("type"));
                post.setName(rs.getString("name"));
                post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                return post;
            }
        });
    }
}
