package com.jpa.demo.service;

import com.jpa.demo.domain.ShopMember;
import com.jpa.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long join(ShopMember member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(ShopMember member) {
        List<ShopMember> findMembers =
                memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<ShopMember> findMembers() {
        return memberRepository.findAll();
    }

    public ShopMember findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
