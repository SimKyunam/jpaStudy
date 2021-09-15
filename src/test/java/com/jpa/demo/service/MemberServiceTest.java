package com.jpa.demo.service;

import com.jpa.demo.domain.ShopMember;
import com.jpa.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        ShopMember member = new ShopMember();
        member.setName("Kim");

        //When
        Long saveId = memberService.join(member);

        //Then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        ShopMember member1 = new ShopMember();
        member1.setName("Kim");

        ShopMember member2 = new ShopMember();
        member2.setName("Kim");

        //When, Then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2); //예외가 발생해야 한다
        });
    }
}