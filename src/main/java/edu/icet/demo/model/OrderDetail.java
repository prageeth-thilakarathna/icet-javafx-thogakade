package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetail {
    private String itemCode;
    private String description;
    private String quantity;
    private String unitPrice;
    private String total;
}
