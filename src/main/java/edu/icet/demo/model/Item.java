package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Item {
    private String id;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer qtyOnHand;
}
