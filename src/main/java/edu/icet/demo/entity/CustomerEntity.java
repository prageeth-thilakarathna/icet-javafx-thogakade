package edu.icet.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate dateOfBarth;
    private Double salary;
    private String address;
    private String city;
    private String province;
    private String postalCode;
}
