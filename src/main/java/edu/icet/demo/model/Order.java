package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order {
    private String orderId;
    private String orderDate;
    private String customerId;
}
