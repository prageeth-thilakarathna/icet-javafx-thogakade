package edu.icet.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ItemEntity {
    private String itemCode;
    private String description;
    private String packSize;
    private String unitPrice;
    private String qtyOnHand;
}
