package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.model.Customer;

import java.sql.ResultSet;

public interface CustomerBo extends SuperBo {
    boolean addCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String id);

    ResultSet getCustomer(String id);

    ResultSet getAllCustomers();
}
