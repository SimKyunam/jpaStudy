package com.jpa.demo.jpaorm.bulk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BulkTest {

    @PersistenceContext
    EntityManager em;

    @DisplayName("1. UPDATE 벌크")
    @Test
    @Transactional
    void test_1(){
        String qlString =
                "update ShopMember m " +
                        "set m.age = m.age * 1.1 " +
                        "where m.age > :age";

        int resultCount = em.createQuery(qlString)
                .setParameter("age", 10)
                .executeUpdate();
    }

    @DisplayName("2. DELETE 벌크")
    @Test
    @Transactional
    void test_2(){
        String qlString =
                "delete from ShopMember m " +
                        "where m.age > :age";

        int resultCount = em.createQuery(qlString)
                .setParameter("age", 10)
                .executeUpdate();
    }

    @DisplayName("3. INSERT 벌크")
    @Test
    @Transactional
    void test_3(){
        String qlString =
                "insert into Member(email, name, age) " +
                        "select m.email, m.name, m.age " +
                        "from Member m " +
                        "where email = :email";

        int resultCount = em.createQuery(qlString)
                .setParameter("email", "test@gmail.com")
                .executeUpdate();
    }

}
