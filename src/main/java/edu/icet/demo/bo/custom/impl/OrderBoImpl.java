package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.OrderBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.OrderDao;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.model.Order;
import edu.icet.demo.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.ResultSet;

public class OrderBoImpl implements OrderBo {

    private final OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    @Override
    public ResultSet getAllOrders() {
        return orderDao.findAll();
    }

    @Override
    public ResultSet getOrder(String id) {
        return orderDao.findById(id);
    }

    @Override
    public boolean deleteOrder(String id) {
        return orderDao.delete(id);
    }

    @Override
    public ResultSet getTableRowCount() {
        return orderDao.count();
    }

    @Override
    public ResultSet getTableLastId() {
        return orderDao.findLast();
    }

    @Override
    public boolean placeOrder(Order order) {
        return orderDao.save(new ModelMapper().map(order, OrderEntity.class));
    }
}
