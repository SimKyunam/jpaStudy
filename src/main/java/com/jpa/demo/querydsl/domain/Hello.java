package com.jpa.demo.querydsl.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedStoredProcedureQuery(
        name = "multiply",
        procedureName = "proc_multiply",
        parameters = {
                @StoredProcedureParameter(name = "inParam", mode =
                        ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "outParam", mode =
                        ParameterMode.OUT, type = Integer.class)
        }
)
public class Hello {
    @Id
    @GeneratedValue
    private Long id;
}
