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
        String sql = "INSERT INTO item VALUES(?,?,?,?,?)";
        try{
            Boolean res = CrudUtil.execute(
                    sql,
                    entity.getItemCode(),
                    entity.getDescription(),
                    entity.getPackSize(),
                    entity.getUnitPrice(),
                    entity.getQtyOnHand()
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
    public boolean update(ItemEntity entity) {
        String sql = "UPDATE item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?";
        try{
            Boolean res = CrudUtil.execute(
                    sql,
                    entity.getItemCode(),
                    entity.getDescription(),
                    entity.getPackSize(),
                    entity.getUnitPrice(),
                    entity.getQtyOnHand()
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
    public boolean delete(String id) {
        String sql = "DELETE FROM item WHERE itemCode=?";
        try{
            return Boolean.TRUE.equals(CrudUtil.execute(sql, id));
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
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

    @Override
    public ResultSet count() {
        String sql = "SELECT COUNT(*) AS row_count FROM item";
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
        String sql = "SELECT * FROM item ORDER BY itemCode DESC LIMIT 1";
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
