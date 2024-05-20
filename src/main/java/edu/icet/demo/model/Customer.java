package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Customer {
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
