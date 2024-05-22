package edu.icet.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id
    private String id;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer qtyOnHand;

    @OneToMany(mappedBy = "item")
    private List<OrderDetailEntity> orderDetail;
}
