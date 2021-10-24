package com.jpa.demo.domain;

import com.jpa.demo.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LoginToken extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    private String refreshToken;

}
