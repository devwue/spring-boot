package com.devwue.spring.api.repository;

import com.devwue.spring.api.dto.request.PostSearchRequest;
import com.devwue.spring.dto.entity.Post;
import com.devwue.spring.dto.entity.QPost;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.devwue.spring.dto.entity.QPost.post;

@Slf4j
@Repository
public class PostSearchRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public PostSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    @Override
    @PersistenceContext(unitName = "apiPU")
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    public Page<Post> findByName(PostSearchRequest request, Pageable pageable) {
        QPost qPost = post;
        JPQLQuery<Post> query = queryFactory.selectFrom(qPost)
                .where(eqName(request.getName()));

        QueryResults<Post> posts= getQuerydsl().applyPagination(pageable, query).fetchResults();
        return new PageImpl<>(posts.getResults(), pageable, posts.getTotal());
    }

    private BooleanExpression eqName(String name) {
        if (ObjectUtils.isEmpty(name)) {
            return null;
        }
        return post.name.eq(name);
    }
}
