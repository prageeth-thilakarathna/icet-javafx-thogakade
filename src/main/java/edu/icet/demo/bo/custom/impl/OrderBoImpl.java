package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.OrderBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.OrderDao;
import edu.icet.demo.util.DaoType;

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
}
