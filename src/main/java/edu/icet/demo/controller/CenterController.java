package edu.icet.demo.controller;

import edu.icet.demo.db.LoadDBDriver;
import javafx.scene.control.Alert;
import lombok.Getter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class CenterController {
    @Getter
    private static final CenterController instance = new CenterController();

    private CenterController(){}

    public static final Alert alert = new Alert(Alert.AlertType.NONE);

    public static final DecimalFormat df = new DecimalFormat("0.00");

    public ResultSet getCustomer(String customerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customerId='"+customerId+"'";
        Connection connection = LoadDBDriver.getLoadDBDriverInstance().getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public ResultSet getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM customer";
        Connection connection = LoadDBDriver.getLoadDBDriverInstance().getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public ResultSet getAllItem() throws SQLException {
        String sql = "SELECT * FROM item";
        Connection connection = LoadDBDriver.getLoadDBDriverInstance().getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public ResultSet getItem(String itemCode) throws SQLException {
        String sql = "SELECT * FROM item WHERE itemCode='"+itemCode+"'";
        Connection connection = LoadDBDriver.getLoadDBDriverInstance().getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }
}
