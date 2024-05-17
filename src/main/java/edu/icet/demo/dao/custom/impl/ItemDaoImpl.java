package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.controller.CenterController;
import edu.icet.demo.dao.custom.ItemDao;
import edu.icet.demo.entity.ItemEntity;
import edu.icet.demo.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemEntity entity) {
        return false;
    }

    @Override
    public boolean update(ItemEntity entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ResultSet findById(String id) {
        String sql = "SELECT * FROM item WHERE itemCode='"+id+"'";
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
        String sql = "SELECT * FROM item";
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
