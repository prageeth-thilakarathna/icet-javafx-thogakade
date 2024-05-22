package edu.icet.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private String id;
    private Date orderDate;

    @ManyToOne()
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer_order"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "order")
    private List<OrderDetailEntity> orderDetail;
}
