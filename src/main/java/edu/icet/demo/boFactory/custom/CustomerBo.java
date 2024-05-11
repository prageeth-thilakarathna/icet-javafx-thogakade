package edu.icet.demo.boFactory.custom;

import edu.icet.demo.boFactory.SuperBo;
import edu.icet.demo.model.Customer;

public interface CustomerBo extends SuperBo {
    boolean saveCustomer(Customer customer);

    boolean deleteById(String id);
}
