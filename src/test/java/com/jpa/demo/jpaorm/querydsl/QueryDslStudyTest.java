package com.jpa.demo.jpaorm.querydsl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
public class QueryDslStudyTest {
    @PersistenceContext
    private EntityManager entityManager;

    @DisplayName("1. ")
    @Test
    void test_1(){

    }
}
