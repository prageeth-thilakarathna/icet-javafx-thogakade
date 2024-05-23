package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.OrderBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.OrderDao;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.model.Order;
import edu.icet.demo.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class OrderBoImpl implements OrderBo {

    private final OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    @Override
    public List<Order> getAllOrders() {
        List<OrderEntity> orderEntityList = orderDao.getAll();
        List<Order> orderList = new ArrayList<>();
        for(OrderEntity orderEntity : orderEntityList){
            Order order = new Order(
                    orderEntity.getId(),
                    orderEntity.getOrderDate(),
                    orderEntity.getCustomer()
            );
            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public Order getOrder(String id) {
        return new ModelMapper().map(orderDao.get(id), Order.class);
    }

    @Override
    public void deleteOrder(Order order) {
        orderDao.delete(new ModelMapper().map(order, OrderEntity.class));
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
