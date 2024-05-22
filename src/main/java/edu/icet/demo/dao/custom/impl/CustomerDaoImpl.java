package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public void save(CustomerEntity entity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(CustomerEntity entity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(CustomerEntity entity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public CustomerEntity get(String id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        CustomerEntity customerEntity;
        try {
            tx = session.beginTransaction();
            customerEntity = session.get(CustomerEntity.class, id);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.clear();
        }
        return customerEntity;
    }

    @Override
    public List<CustomerEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<CustomerEntity> customerEntityList;
        try {
            tx = session.beginTransaction();
            customerEntityList = session.createQuery("SELECT a FROM CustomerEntity a", CustomerEntity.class).getResultList();
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return customerEntityList;
    }
}
