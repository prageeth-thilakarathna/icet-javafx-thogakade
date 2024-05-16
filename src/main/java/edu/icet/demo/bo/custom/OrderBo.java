package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.model.Order;

import java.sql.ResultSet;

public interface OrderBo extends SuperBo {
    ResultSet getAllOrders();

    ResultSet getOrder(String id);

    boolean deleteOrder(String id);

    ResultSet getTableRowCount();

    ResultSet getTableLastId();

    boolean placeOrder(Order order);
}
