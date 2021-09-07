package com.jpa.demo.jpaorm.querydsl;

import com.jpa.demo.querydsl.domain.Academy;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.jpa.demo.querydsl.domain.QAcademy.academy;

@SpringBootTest
public class QueryDslStudyTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JPAQueryFactory queryFactory;

    @DisplayName("1. 검색 테스트")
    @Test
    void test_1(){
        QueryModifiers queryModifiers = new QueryModifiers(20L, 10L);

        List<Academy> list = queryFactory.selectFrom(academy)
                .where(academy.name.eq("좋은장소").and(academy.age.gt(10000)))
                .orderBy(academy.age.asc(), academy.name.desc())
                .restrict(queryModifiers)
                //.offset(10).limit(10)
                .fetch();
    }

    @DisplayName("2. 페이징 처리")
    @Test
    void test_2(){
        QueryResults<Academy> result = queryFactory.selectFrom(academy)
                .where(academy.age.gt(10))
                .offset(0).limit(10)
                .fetchResults();

        long total = result.getTotal();
        long limit = result.getLimit();
        long offset = result.getOffset();
        List<Academy> results = result.getResults();

        System.out.println(total);
        System.out.println(limit);
        System.out.println(offset);
        System.out.println(results);
    }

    @DisplayName("3. 그룹, 조인")
    @Test
    void test_3(){

    }

}
