package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetail {
    private String orderId;
    private String itemCode;
    private String quantity;
}
