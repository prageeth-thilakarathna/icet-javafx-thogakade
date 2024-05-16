package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;

import java.sql.ResultSet;

public interface OrderBo extends SuperBo {
    ResultSet getAllOrders();

    ResultSet getOrder(String id);
}
