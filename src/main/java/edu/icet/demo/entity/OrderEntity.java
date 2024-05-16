package edu.icet.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class OrderEntity {
    private String orderId;
    private String orderDate;
    private String customerId;
}
