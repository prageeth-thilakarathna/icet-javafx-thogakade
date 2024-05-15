package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.controller.CenterController;
import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity entity) {
        String sql = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            Boolean res = CrudUtil.execute(
                    sql,
                    entity.getCustomerId(),
                    entity.getTitle(),
                    entity.getName(),
                    entity.getDateOfBarth(),
                    entity.getSalary(),
                    entity.getAddress(),
                    entity.getCity(),
                    entity.getProvince(),
                    entity.getPostalCode()
            );

            if(Boolean.TRUE.equals(res)){
                CenterController.alert.setAlertType(Alert.AlertType.CONFIRMATION);
                CenterController.alert.setContentText(entity.getCustomerId() + " Customer is entered into the system successfully.");
                CenterController.alert.show();
            } else {
                CenterController.alert.setAlertType(Alert.AlertType.ERROR);
                CenterController.alert.setContentText("Failed! An error occurred while entering the "+entity.getCustomerId()+" customer.");
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
    public boolean update(CustomerEntity entity) {
        String sql = "UPDATE customer SET title=?, name=?, dateOfBarth=?, salary=?, address=?, city=?, province=?, postalCode=? WHERE customerId=?";
        try {
            Boolean res = CrudUtil.execute(
                    sql,
                    entity.getCustomerId(),
                    entity.getTitle(),
                    entity.getName(),
                    entity.getDateOfBarth(),
                    entity.getSalary(),
                    entity.getAddress(),
                    entity.getCity(),
                    entity.getProvince(),
                    entity.getPostalCode()
            );

            if(Boolean.TRUE.equals(res)){
                CenterController.alert.setAlertType(Alert.AlertType.CONFIRMATION);
                CenterController.alert.setContentText(entity.getCustomerId() + " Customer update is successfully.");
                CenterController.alert.show();
            } else {
                CenterController.alert.setAlertType(Alert.AlertType.ERROR);
                CenterController.alert.setContentText("Failed! An error occurred while updating the "+entity.getCustomerId()+" customer.");
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
    public boolean delete(String id) {
        String sql = "DELETE FROM customer WHERE customerId=?";
        try{
            Boolean res = CrudUtil.execute(sql, id);
            if(Boolean.TRUE.equals(res)){
                CenterController.alert.setAlertType(Alert.AlertType.CONFIRMATION);
                CenterController.alert.setContentText(id + " Customer delete is successfully.");
                CenterController.alert.show();
            } else {
                CenterController.alert.setAlertType(Alert.AlertType.ERROR);
                CenterController.alert.setContentText("Failed! An error occurred while deleting the "+id+" customer.");
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
        String sql = "SELECT * FROM customer WHERE customerId='"+id+"'";
        try{
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return null;
    }

    @Override
    public ResultSet findAll() {
        String sql = "SELECT * FROM customer";
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
