package edu.icet.demo.model;

import edu.icet.demo.entity.ItemEntity;
import edu.icet.demo.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderDetail {
    private String id;
    private OrderEntity order;
    private ItemEntity item;
    private Integer quantity;
}
