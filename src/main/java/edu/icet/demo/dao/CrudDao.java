package edu.icet.demo.dao;

import java.util.List;

public interface CrudDao <T> extends SuperDao{
    void save(T entity);

    void update(T entity);

    void delete(T entity);

    T get(String id);

    List<T> getAll();
}
