package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {
    private String id;
    private String description;
    private String packSize;
    private String unitPrice;
    private String qtyOnHand;
}
