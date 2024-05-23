package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.model.Order;

import java.util.List;

public interface OrderBo extends SuperBo {
    List<Order> getAllOrders();

    Order getOrder(String id);

    void deleteOrder(Order order);

    int getTableRowCount();

    Order getTableLastId();

    void placeOrder(Order order);
}
