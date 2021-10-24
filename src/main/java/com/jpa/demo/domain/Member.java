package com.jpa.demo.domain;

import com.jpa.demo.domain.base.BaseEntity;
import com.jpa.demo.domain.embedded.Address;
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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity {

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
