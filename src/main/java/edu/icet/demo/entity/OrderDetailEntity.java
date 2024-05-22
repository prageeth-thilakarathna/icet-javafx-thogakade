package edu.icet.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor
@Setter
@Entity
@Table(name = "orderDetail")
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_orderDetail"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderEntity order;

    @ManyToOne()
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "fk_item_orderDetail"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemEntity item;

    private Integer quantity;
}
