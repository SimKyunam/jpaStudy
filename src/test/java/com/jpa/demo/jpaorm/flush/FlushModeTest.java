package com.jpa.demo.jpaorm.flush;

import com.jpa.demo.querydsl.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class FlushModeTest {

    @PersistenceContext
    EntityManager em;

    @DisplayName("1. JPQL flush")
    @Test
    void test_1(){
        Member member = em.find(Member.class, 1L);
        member.setAge(33);

        Member member2 =
                em.createQuery("select m from Member m where m.age = 33", Member.class)
                .getSingleResult();
    }

    @DisplayName("2. JPQL Flush COMMIT mode")
    @Test
    void test_2(){
        em.setFlushMode(FlushModeType.COMMIT);

        Member member = em.find(Member.class, 1L);
        member.setAge(33);

        //em.flush() //1. 직접 호출

        Member member2 =
                em.createQuery("select m from Member m where m.age = 33", Member.class)
                        .setFlushMode(FlushModeType.AUTO) //2. setFlushMode() 설정
                        .getSingleResult();
    }


}
