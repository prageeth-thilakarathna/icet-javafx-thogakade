package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.OrderDetailBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.OrderDetailDao;
import edu.icet.demo.entity.OrderDetailEntity;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.model.Order;
import edu.icet.demo.model.OrderDetail;
import edu.icet.demo.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {

    private final OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);

    @Override
    public ResultSet getOrderDetail(String id) {
        return null;
    }

    @Override
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetailDao.save(new ModelMapper().map(orderDetail, OrderDetailEntity.class));
    }

    @Override
    public List<OrderDetail> getOrderDetailInOrder(Order order) {
        List<OrderDetailEntity> orderDetailEntityList = orderDetailDao.getAllInOrder(new ModelMapper().map(order, OrderEntity.class));
        return new ModelMapper().map(orderDetailEntityList, new TypeToken<List<OrderDetail>>() {}.getType());
    }
}
