package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.OrderDetailDao;
import edu.icet.demo.entity.OrderDetailEntity;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public void save(OrderDetailEntity entity) {
        Session session = HibernateUtil.getSingletonSession();
        session.persist(entity);
    }

    @Override
    public void update(OrderDetailEntity entity) {
    }

    @Override
    public void delete(OrderDetailEntity entity) {
    }

    @Override
    public OrderDetailEntity get(String id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        OrderDetailEntity orderDetailEntity;
        try {
            tx = session.beginTransaction();
            orderDetailEntity = session.get(OrderDetailEntity.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return orderDetailEntity;
    }

    @Override
    public List<OrderDetailEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<OrderDetailEntity> orderDetailEntityList;
        try {
            tx = session.beginTransaction();
            orderDetailEntityList = session.createQuery("SELECT a FROM OrderDetailEntity a", OrderDetailEntity.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return orderDetailEntityList;
    }

    @Override
    public List<OrderDetailEntity> getAllInOrder(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<OrderDetailEntity> orderDetailEntityList;
        try {
            tx = session.beginTransaction();
            String sql = "FROM OrderDetailEntity O WHERE O.order = :orderEntity";
            orderDetailEntityList = session.createQuery(sql, OrderDetailEntity.class)
                    .setParameter("orderEntity", orderEntity)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return orderDetailEntityList;
    }
}
