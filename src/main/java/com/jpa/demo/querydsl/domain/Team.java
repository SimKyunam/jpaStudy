package com.jpa.demo.querydsl.domain;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SqlResultSetMapping(name = "teamWithMemberCount",
    entities = {@EntityResult(entityClass = Team.class)},

//    entities = {@EntityResult(entityClass = Team.class, fields = {
//            @FieldResult(name = "id", column ="team_id"),
//            @FieldResult(name = "teamName", column ="team_name")
//    })},
    columns = {@ColumnResult(name = "MEMBER_COUNT")}
)
@NamedNativeQuery(
        name ="Team.teamWithMemberCount1",
        query = "SELECT T.TEAM_ID, T.TEAM_NAME, MT.MEMBER_COUNT " +
                "FROM TEAM T " +
                "LEFT JOIN " +
                "   (SELECT ST.TEAM_ID, COUNT(*) AS MEMBER_COUNT " +
                "       FROM MEMBER M " +
                "       INNER JOIN TEAM ST ON M.TEAM_ID = ST.TEAM_ID " +
                "       GROUP BY ST.TEAM_ID" +
                "   ) MT " +
                "ON MT.TEAM_ID = T.TEAM_ID",
        resultSetMapping = "teamWithMemberCount"
)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String teamName;

    @OneToMany(mappedBy = "team")
    @ToString.Exclude
    private List<Member> members;
}
