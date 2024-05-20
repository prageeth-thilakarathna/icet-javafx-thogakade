package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.controller.CenterController;
import edu.icet.demo.dao.custom.OrderDao;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDaoImpl {
    /*@Override
    public boolean save(OrderEntity entity) {
        String sql = "INSERT INTO `order` VALUES(?,?,?)";
        try{
            Boolean res = CrudUtil.execute(
                    sql,
                    entity.getOrderId(),
                    entity.getOrderDate(),
                    entity.getCustomerId()
            );
            return Boolean.TRUE.equals(res);
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return false;
    }

    @Override
    public boolean update(OrderEntity entity) {
        return false;
    }

    @Override
    public boolean delete(OrderEntity entity) {
        return false;
    }

    @Override
    public ResultSet findById(String id) {
        String sql = "SELECT * FROM `order` WHERE orderId='"+id+"'";
        try{
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return null;
    }

    @Override
    public ResultSet findAll() {
        String sql = "SELECT * FROM `order`";
        try{
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return null;
    }

    @Override
    public ResultSet count() {
        String sql = "SELECT COUNT(*) AS row_count FROM `order`";
        try{
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return null;
    }

    @Override
    public ResultSet findLast() {
        String sql = "SELECT * FROM `order` ORDER BY orderId DESC LIMIT 1";
        try{
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return null;
    }*/
}
