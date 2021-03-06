package com.jpa.demo.domain;


import com.jpa.demo.domain.embedded.Address;
import com.jpa.demo.domain.enums.DeliveryStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]

    public Delivery() {
    }

    public Delivery(Address address) {
        this.address = address;
        this.status = DeliveryStatus.READY;
    }
}
