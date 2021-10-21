package com.jpa.demo.domain;

import com.jpa.demo.domain.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private String memberName;      //회원 이름
    private OrderStatus orderStatus;//주문 상태
}
