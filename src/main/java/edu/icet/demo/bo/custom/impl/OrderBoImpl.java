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
        return null;
    }

    @Override
    public Order getOrder(String id) {
        return new ModelMapper().map(orderDao.get(id), Order.class);
    }

    @Override
    public boolean deleteOrder(String id) {
        return false;
    }

    @Override
    public int getTableRowCount() {
        return orderDao.count();
    }

    @Override
    public Order getTableLastId() {
        return new ModelMapper().map(orderDao.findLast(), Order.class);
    }

    @Override
    public void placeOrder(Order order) {
        orderDao.save(new ModelMapper().map(order, OrderEntity.class));
    }
}
