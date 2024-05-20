package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.entity.CustomerEntity;

import java.util.List;

public interface CustomerDao extends CrudDao<CustomerEntity> {
    List<CustomerEntity> getAll();
}
