package edu.icet.demo.model;

import edu.icet.demo.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Order {
    private String id;
    private Date orderDate;
    private CustomerEntity customer;
}
