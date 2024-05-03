package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Customer {
    private String customerId;
    private String title;
    private String name;
    private String dateOfBarth;
    private String salary;
    private String address;
    private String city;
    private String province;
    private String postalCode;
}
