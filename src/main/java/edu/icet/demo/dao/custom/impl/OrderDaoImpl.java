package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.controller.CenterController;
import edu.icet.demo.dao.custom.OrderDao;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(OrderEntity entity) {
        return false;
    }

    @Override
    public boolean update(OrderEntity entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM `order` WHERE orderId=?";
        try{
            Boolean res = CrudUtil.execute(sql, id);
            if(Boolean.TRUE.equals(res)){
                CenterController.alert.setAlertType(Alert.AlertType.CONFIRMATION);
                CenterController.alert.setContentText(id + " Order delete is successfully.");
                CenterController.alert.show();
            } else {
                CenterController.alert.setAlertType(Alert.AlertType.ERROR);
                CenterController.alert.setContentText("Failed! An error occurred while deleting the "+id+" order.");
                CenterController.alert.show();
            }
            return Boolean.TRUE.equals(res);
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
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
}
