package com.devwue.spring.api.repository;

import com.devwue.spring.api.dto.request.PostSearchRequest;
import com.devwue.spring.api.dto.response.PostTypeResponse;
import com.devwue.spring.dto.entity.Post;
import com.devwue.spring.dto.entity.QPost;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.naming.event.ObjectChangeListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

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
        JPQLQuery<Post> query = queryFactory.selectFrom(post)
                .where(eqName(request.getName()))
                .orderBy(post.id.desc())
                ;

        QueryResults<Post> posts= getQuerydsl().applyPagination(pageable, query).fetchResults();
        return new PageImpl<>(posts.getResults(), pageable, posts.getTotal());
    }

    public List<PostTypeResponse> getSummaryType() {
        return queryFactory.selectFrom(post)
                .select(Projections.constructor(PostTypeResponse.class, post.type, Expressions.ONE))
                .groupBy(post.type)
                .fetch();
    }

    private BooleanExpression eqName(String name) {
        if (ObjectUtils.isEmpty(name)) {
            return null;
        }
        return post.name.eq(name);
    }
}
