package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.model.Customer;
import edu.icet.demo.util.DaoType;
import org.modelmapper.ModelMapper;

public class CustomerBoImpl implements CustomerBo {

    private CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);

    @Override
    public boolean addCustomer(Customer customer) {
        return customerDao.save(new ModelMapper().map(customer, CustomerEntity.class));
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDao.update(new ModelMapper().map(customer, CustomerEntity.class));
    }

    @Override
    public boolean deleteCustomer(String id) {
        return customerDao.delete(id);
    }


}
