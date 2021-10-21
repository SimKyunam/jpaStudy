package com.jpa.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQuery(
        name = "Member.memberSQL",
        query = "SELECT MEMBER_ID, AGE, NAME, EMAIL, TEAM_ID " +
                "FROM MEMBER WHERE AGE > ? ",
        resultClass = Member.class
)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<Order> orders = new ArrayList<Order>();
}
