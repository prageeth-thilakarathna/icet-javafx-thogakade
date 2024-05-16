package edu.icet.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class OrderDetailEntity {
    private String orderId;
    private String itemCode;
    private String quantity;
}
