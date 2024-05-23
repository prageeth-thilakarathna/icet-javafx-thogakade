package edu.icet.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private String id;
    private String title;
    private String name;
    private LocalDate dateOfBirth;
    private Double salary;
    private String address;
    private String city;
    private String province;
    private String postalCode;

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;
}
