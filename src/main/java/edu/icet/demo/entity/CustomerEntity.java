package edu.icet.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CustomerEntity {
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
