package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.model.OrderDetail;

import java.sql.ResultSet;

public interface OrderDetailBo extends SuperBo {
    ResultSet getOrderDetail(String id);

    void addOrderDetail(OrderDetail orderDetail);
}
