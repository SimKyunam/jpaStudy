package com.jpa.demo.querydsl.domain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedNativeQuery(
        name = "Member.memberSQL",
        query = "SELECT MEMBER_ID, AGE, NAME, EMAIL, TEAM_ID " +
                "FROM MEMBER WHERE AGE > ? ",
        resultClass = Member.class
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String name;

    private int age;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}