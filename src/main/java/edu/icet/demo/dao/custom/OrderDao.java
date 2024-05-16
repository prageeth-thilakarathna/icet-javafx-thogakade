package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.entity.OrderEntity;

import java.sql.ResultSet;

public interface OrderDao extends CrudDao<OrderEntity> {
    ResultSet count();

    ResultSet findLast();
}
