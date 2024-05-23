package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.entity.OrderDetailEntity;
import edu.icet.demo.entity.OrderEntity;

import java.util.List;

public interface OrderDetailDao extends CrudDao<OrderDetailEntity> {
    List<OrderDetailEntity> getAllInOrder(OrderEntity orderEntity);
}
