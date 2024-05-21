package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.model.Customer;
import edu.icet.demo.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    private final CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);

    @Override
    public void addCustomer(Customer customer) {
        customerDao.save(new ModelMapper().map(customer, CustomerEntity.class));
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDao.update(new ModelMapper().map(customer, CustomerEntity.class));
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerDao.delete(new ModelMapper().map(customer, CustomerEntity.class));
    }

    @Override
    public Customer getCustomer(String id) {
        CustomerEntity customerEntity = customerDao.get(id);
        if(customerEntity!=null){
            return new ModelMapper().map(customerEntity, Customer.class);
        } else {
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ModelMapper().map(customerDao.getAll(), new TypeToken<List<Customer>>() {}.getType());
    }
}
