package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetail {
    private String id;
    private String itemId;
    private String quantity;
}
