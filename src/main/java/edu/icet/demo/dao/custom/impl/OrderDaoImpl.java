package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.OrderDao;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void save(OrderEntity entity) {
        Session session = HibernateUtil.getSingletonSession();
        HibernateUtil.singletonBegin();
        session.persist(entity);
    }

    @Override
    public void update(OrderEntity entity) {}

    @Override
    public void delete(OrderEntity entity) {
        Session session = HibernateUtil.getSingletonSession();
        HibernateUtil.singletonBegin();
        session.remove(entity);
    }

    @Override
    public OrderEntity get(String id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        OrderEntity orderEntity;
        try {
            tx = session.beginTransaction();
            orderEntity = session.get(OrderEntity.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return orderEntity;
    }

    @Override
    public List<OrderEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<OrderEntity> orderEntityList;
        try {
            tx = session.beginTransaction();
            orderEntityList = session.createQuery("SELECT a FROM OrderEntity a", OrderEntity.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return orderEntityList;
    }

    @Override
    public int count() {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM orders");
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        return count.get();
    }

    @Override
    public OrderEntity findLast() {
        Session session = HibernateUtil.getSession();
        OrderEntity orderEntity = new OrderEntity();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");
                resultSet.next();
                orderEntity.setId(resultSet.getString("id"));
            }
        });
        return orderEntity;
    }
}
