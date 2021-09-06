package com.jpa.demo.querydsl.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void helloTest() {
        Hello hello = new Hello();
        entityManager.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        QHello qHello = QHello.hello;

        //쿼리와 관련된건 Q타입으로 작성함.
        Hello result = query
                .selectFrom(qHello)
                .fetchOne();

        assertEquals(result, hello);
        assertEquals(result.getId(), hello.getId());
    }
}