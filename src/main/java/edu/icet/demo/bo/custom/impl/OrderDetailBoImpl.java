package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.OrderDetailBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.OrderDetailDao;
import edu.icet.demo.util.DaoType;

import java.sql.ResultSet;

public class OrderDetailBoImpl implements OrderDetailBo {

    private final OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);

    @Override
    public ResultSet getOrderDetail(String id) {
        return orderDetailDao.findById(id);
    }
}
