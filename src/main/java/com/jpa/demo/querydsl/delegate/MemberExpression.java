package com.jpa.demo.querydsl.delegate;

import com.jpa.demo.querydsl.domain.Member;
import com.jpa.demo.querydsl.domain.QMember;
import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.dsl.BooleanExpression;

public class MemberExpression {
    @QueryDelegate(Member.class)
    public static BooleanExpression isOrder(QMember member, Integer age){
        return member.age.gt(age);
    }
}
