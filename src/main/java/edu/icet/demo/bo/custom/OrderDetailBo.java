package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.model.Order;
import edu.icet.demo.model.OrderDetail;

import java.util.List;

public interface OrderDetailBo extends SuperBo {
    void addOrderDetail(OrderDetail orderDetail);

    List<OrderDetail> getOrderDetail(Order order);
}
