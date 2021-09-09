package com.jpa.demo.jpaorm.nativesql;

import com.jpa.demo.querydsl.domain.Member;
import com.jpa.demo.querydsl.domain.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NativeSqlTest {

    @PersistenceContext
    private EntityManager em;

    @DisplayName("1. 기본 엔티티 조회")
    @Test
    void test_1(){
        String sql = "SELECT MEMBER_ID, AGE, NAME, EMAIL, TEAM_ID " +
                        "FROM MEMBER WHERE AGE > ? ";

        //기본 엔티티
        Query nativeQuery = em.createNativeQuery(sql, Member.class)
                .setParameter(1, 20);

        List<Member> resultList = nativeQuery.getResultList();
        resultList.forEach(System.out::println);

        //오브젝트 엔티티
        Query objectQuery = em.createNativeQuery(sql)
                .setParameter(1, 20);
        List<Object[]> resultList1 = objectQuery.getResultList();

        for (Object[] row : resultList1){
            System.out.println("id = " + row[0]);
            System.out.println("age = " + row[1]);
            System.out.println("name = " + row[2]);
            System.out.println("team_id = " + row[3]);
        }
    }

    @DisplayName("2. 결과 매핑")
    @Test
    void test_2(){
        String sql = "SELECT T.TEAM_ID, T.TEAM_NAME, MT.MEMBER_COUNT " +
                "FROM TEAM T " +
                "LEFT JOIN " +
                "   (SELECT ST.TEAM_ID, COUNT(*) AS MEMBER_COUNT " +
                "       FROM MEMBER M " +
                "       INNER JOIN TEAM ST ON M.TEAM_ID = ST.TEAM_ID " +
                "       GROUP BY ST.TEAM_ID" +
                "   ) MT " +
                "ON MT.TEAM_ID = T.TEAM_ID";

        Query nativeQuery = em.createNativeQuery(sql, "teamWithMemberCount");
        List<Object[]> resultList = nativeQuery.getResultList();

        for(Object[] row : resultList){
            Team team = (Team) row[0];
            BigInteger memberCount = (BigInteger) row[1];

            System.out.println("team = " + team);
            System.out.println("memberCount = " + memberCount);
        }
    }

    @DisplayName("3. named 네이티브 SQL")
    @Test
    void test_3(){
        //resultClass, Member.class 정의
        TypedQuery<Member> nativeQuery = em.createNamedQuery("Member.memberSQL", Member.class)
                .setParameter(1, 20);

        nativeQuery.getResultList().forEach(System.out::println);


        //resultSetMapping, Team.class 정의
        List<Object[]> resultList = em.createNamedQuery("Team.teamWithMemberCount")
                .getResultList();

        for(Object[] row : resultList){
            Team team = (Team) row[0];
            BigInteger memberCount = (BigInteger) row[1];

            System.out.println("team = " + team);
            System.out.println("memberCount = " + memberCount);
        }
    }

    @DisplayName("4. 네이티브 SQL XML")
    @Test
    void test_4(){
        List<Object[]> resultList = em.createNamedQuery("Team.teamWithMemberCountXml")
                .getResultList();

        for(Object[] row : resultList){
            Team team = (Team) row[0];
            BigInteger memberCount = (BigInteger) row[1];

            System.out.println("team = " + team);
            System.out.println("memberCount = " + memberCount);
        }
    }



}
