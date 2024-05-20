package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.model.Customer;

import java.util.List;

public interface CustomerBo extends SuperBo {
    boolean addCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(Customer customer);

    Customer getCustomer(String id);

    List<Customer> getAllCustomers();
}
