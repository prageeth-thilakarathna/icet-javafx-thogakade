package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.controller.CenterController;
import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.util.CrudUtil;
import edu.icet.demo.util.HibernateUtil;
import javafx.scene.control.Alert;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.clear();
        return true;
    }

    @Override
    public boolean update(CustomerEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.merge(entity);
        session.getTransaction().commit();
        session.clear();
        return true;
    }

    @Override
    public boolean delete(CustomerEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.remove(entity);
        session.getTransaction().commit();
        session.clear();
        return true;
    }

    @Override
    public CustomerEntity get(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        CustomerEntity customerEntity = session.get(CustomerEntity.class, id);
        session.getTransaction().commit();
        session.clear();
        return customerEntity;
    }

    @Override
    public List<CustomerEntity> getAll() {
        Session session = HibernateUtil.getSession();
        return session.createQuery("SELECT a FROM CustomerEntity a", CustomerEntity.class).getResultList();
    }
}
