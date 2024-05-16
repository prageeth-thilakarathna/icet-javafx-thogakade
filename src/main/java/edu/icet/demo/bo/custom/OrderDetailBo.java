package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;

import java.sql.ResultSet;

public interface OrderDetailBo extends SuperBo {
    ResultSet getOrderDetail(String id);
}
