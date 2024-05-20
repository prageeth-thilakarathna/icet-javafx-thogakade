package edu.icet.demo.dao;

import edu.icet.demo.entity.CustomerEntity;

public interface CrudDao <T> extends SuperDao{
    boolean save(T entity);

    boolean update(T entity);

    boolean delete(T entity);

    CustomerEntity get(String id);
}
