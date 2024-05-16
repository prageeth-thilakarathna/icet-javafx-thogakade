package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private String unitPrice;
    private String qtyOnHand;
}
