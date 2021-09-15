package com.jpa.demo.domain;

import com.jpa.demo.domain.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
