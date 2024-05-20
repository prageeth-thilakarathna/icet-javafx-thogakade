package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.OrderDetailBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.OrderDetailDao;
import edu.icet.demo.entity.OrderDetailEntity;
import edu.icet.demo.model.OrderDetail;
import edu.icet.demo.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.ResultSet;

public class OrderDetailBoImpl implements OrderDetailBo {

    private final OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);

    @Override
    public ResultSet getOrderDetail(String id) {
        return null;
    }

    @Override
    public boolean addOrderDetail(OrderDetail orderDetail) {
        return orderDetailDao.save(new ModelMapper().map(orderDetail, OrderDetailEntity.class));
    }
}
