package com.jpa.demo.jpaorm.querydsl;

import com.jpa.demo.querydsl.domain.Academy;
import com.jpa.demo.querydsl.domain.Member;
import com.jpa.demo.querydsl.domain.QMember;
import com.jpa.demo.querydsl.domain.Team;
import com.jpa.demo.querydsl.dto.MemberDTO;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.jpa.demo.querydsl.domain.QAcademy.academy;
import static com.jpa.demo.querydsl.domain.QHello.hello;
import static com.jpa.demo.querydsl.domain.QTeam.team;
import static com.jpa.demo.querydsl.domain.QMember.member;
import static com.querydsl.core.types.ExpressionUtils.count;

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
        List<Team> teamResult = queryFactory.selectFrom(team)
                .leftJoin(team.members, member)
                .groupBy(team.teamName)
                .having(team.teamName.startsWith("좋은"))
                .fetch();

        System.out.println(teamResult);

        //세타 조인
//        List<?> result = queryFactory.from(academy, hello)
//                .where(academy.id.eq(hello.id))
//                .fetch();
//
//        System.out.println(result);
    }

    @DisplayName("4. 서브쿼리리")
    @Test
    void test_4(){
        //select
        queryFactory.select(team.teamName.as("test"),
                    ExpressionUtils.as(
                        JPAExpressions.select(count(member.id))
                        .from(member)
                        .where(member.team.eq(team)), "memberCount"))
        .from(team)
        .fetch();


        //조건
        QMember m = new QMember("m");
        queryFactory.selectFrom(member)
                .where(member.in(
                        JPAExpressions.selectFrom(m)
                        .where(m.id.eq(1L))
                ))
                .fetch();
    }

    @DisplayName("5. 프로젝션과 결과 반환")
    @Test
    void test_5() {
        //기본 결과 반환
//        List<Tuple> result = queryFactory.select(member.name, member.age)
//                .from(member)
//                .fetch();
//
//        for (Tuple tuple : result){
//            System.out.println("name = " + tuple.get(member.name));
//            System.out.println("age = " + tuple.get(member.age));
//        }

        //프로젝션 사용
        List<MemberDTO> resultMember = queryFactory
//                .select(Projections.bean(MemberDTO.class, member.name.as("username"), member.age))
//                .select(Projections.fields(MemberDTO.class, member.name.as("username"), member.age))
                .selectDistinct(Projections.constructor(MemberDTO.class, member.name.as("username"), member.age))

                .from(member).fetch();

        resultMember.forEach(memberDTO ->
                System.out.printf("name = %s, age = %d %n", memberDTO.getUsername(), memberDTO.getAge()));
    }

    @DisplayName("6. 수정, 삭제 배치 쿼리")
    @Test
    @Transactional
    void test_6(){
        //수정
        Long updateCnt = queryFactory.update(member).where(member.name.eq("강감찬"))
                .set(member.name, "강감찬수정")
                .execute();

        queryFactory.selectFrom(member).fetch().forEach(System.out::println);
        System.out.println("updateCnt = " + updateCnt);

        //삭제
        Long deleteCnt = queryFactory.delete(member).where(member.name.eq("강감찬수정"))
                .execute();

        queryFactory.selectFrom(member).fetch().forEach(System.out::println);
        System.out.println("deleteCnt = " + deleteCnt);
    }

    @DisplayName("7. 동적쿼리")
    @Test
    void test_7(){

    }


}
