package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.model.Order;
import edu.icet.demo.model.OrderDetail;

import java.sql.ResultSet;
import java.util.List;

public interface OrderDetailBo extends SuperBo {
    ResultSet getOrderDetail(String id);

    void addOrderDetail(OrderDetail orderDetail);

    List<OrderDetail> getOrderDetailInOrder(Order order);
}
