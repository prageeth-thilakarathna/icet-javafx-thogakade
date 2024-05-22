package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.model.Order;

import java.sql.ResultSet;

public interface OrderBo extends SuperBo {
    ResultSet getAllOrders();

    Order getOrder(String id);

    boolean deleteOrder(String id);

    int getTableRowCount();

    Order getTableLastId();

    void placeOrder(Order order);
}
