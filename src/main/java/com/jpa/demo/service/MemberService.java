package com.jpa.demo.service;

import com.jpa.demo.domain.ShopMember;
import com.jpa.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public Long join(ShopMember member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(ShopMember member) {
        List<ShopMember> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<ShopMember> findMembers() {
        return memberRepository.findAll();
    }

    public ShopMember findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
