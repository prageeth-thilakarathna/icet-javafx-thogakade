package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.ItemDao;
import edu.icet.demo.entity.ItemEntity;
import edu.icet.demo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ItemDaoImpl implements ItemDao {
    @Override
    public void save(ItemEntity entity) {
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
    public void update(ItemEntity entity) {
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
    public void delete(ItemEntity entity) {
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
    public ItemEntity get(String id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        ItemEntity itemEntity;
        try {
            tx = session.beginTransaction();
            itemEntity = session.get(ItemEntity.class, id);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return itemEntity;
    }

    @Override
    public List<ItemEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<ItemEntity> itemEntityList;
        try {
            tx = session.beginTransaction();
            itemEntityList = session.createQuery("SELECT a FROM ItemEntity a", ItemEntity.class).getResultList();
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return itemEntityList;
    }

    @Override
    public int count() {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM item");
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        return count.get();
    }

    @Override
    public ItemEntity findLast() {
        Session session = HibernateUtil.getSession();
        ItemEntity item = new ItemEntity();
        session.doWork(connection -> {
            try(Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM item ORDER BY id DESC LIMIT 1");
                resultSet.next();
                item.setId(resultSet.getString("id"));
            }
        });
        return item;
    }

    @Override
    public void modifyItem(ItemEntity itemEntity) {
        Session session = HibernateUtil.getSingletonSession();
        session.merge(itemEntity);
    }
}
