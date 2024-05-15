package edu.icet.demo.dao;

import java.sql.ResultSet;

public interface CrudDao <T> extends SuperDao{
    boolean save(T entity);

    boolean update(T entity);

    boolean delete(String id);

    ResultSet findById(String id);

    ResultSet findAll();
}
