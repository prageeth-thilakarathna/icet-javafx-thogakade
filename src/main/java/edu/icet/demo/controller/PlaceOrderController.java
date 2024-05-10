package edu.icet.demo.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {
    @FXML
    private Label dateDisplay;
    @FXML
    private Label timeDisplay;
    @FXML
    private Label orderIdDisplay;
    @FXML
    private Label descriptionDisplay;
    @FXML
    private Label packSizeDisplay;
    @FXML
    private Label unitPriceDisplay;
    @FXML
    private Label qtyOnHandDisplay;
    @FXML
    private Label nameDisplay;
    @FXML
    private Label addressDisplay;
    @FXML
    private Label salaryDisplay;
    @FXML
    private Label cityDisplay;
    @FXML
    private JFXComboBox<String> itemCODEs;
    @FXML
    private JFXComboBox<String> customerIDs;

    @FXML
    private void customerIdSelectAction(ActionEvent actionEvent) {
        try {
            ResultSet resultSet = CenterController.getInstance().getCustomer(customerIDs.getValue());
            if(resultSet.next()){
                if (resultSet.getString("name").length() < 10) {
                    nameDisplay.setText(resultSet.getString("name").substring(0, resultSet.getString("name").length()));
                } else {
                    nameDisplay.setText(resultSet.getString("name").substring(0, 10) + "...");
                    nameDisplay.setTooltip(new Tooltip(resultSet.getString("name")));
                }

                if (resultSet.getString("address").length() < 10) {
                    addressDisplay.setText(resultSet.getString("address").substring(0, resultSet.getString("address").length()));
                } else {
                    addressDisplay.setText(resultSet.getString("address").substring(0, 10) + "...");
                    addressDisplay.setTooltip(new Tooltip(resultSet.getString("address")));
                }

                salaryDisplay.setText(resultSet.getString("salary"));

                if (resultSet.getString("city").length() < 10) {
                    cityDisplay.setText(resultSet.getString("city").substring(0, resultSet.getString("city").length()));
                } else {
                    cityDisplay.setText(resultSet.getString("city").substring(0, 10) + "...");
                    cityDisplay.setTooltip(new Tooltip(resultSet.getString("city")));
                }
            }
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    @FXML
    private void itemCodeSelectAction(ActionEvent actionEvent) {
        try {
            ResultSet resultSet = CenterController.getInstance().getItem(itemCODEs.getValue());
            if(resultSet.next()){
                if (resultSet.getString("description").length() < 10) {
                    descriptionDisplay.setText(resultSet.getString("description").substring(0, resultSet.getString("dscription").length()));
                } else {
                    descriptionDisplay.setText(resultSet.getString("description").substring(0, 10) + "...");
                    descriptionDisplay.setTooltip(new Tooltip(resultSet.getString("description")));
                }

                packSizeDisplay.setText(resultSet.getString("packSize"));
                unitPriceDisplay.setText(resultSet.getString("unitPrice"));
                qtyOnHandDisplay.setText(resultSet.getString("qtyOnHand"));
            }
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    @FXML
    private void selectionCancelAction(ActionEvent actionEvent) {
        customerIDs.setValue(null);
        nameDisplay.setText("");
        addressDisplay.setText("");
        salaryDisplay.setText("");
        cityDisplay.setText("");
        itemCODEs.setValue(null);
        descriptionDisplay.setText("");
        packSizeDisplay.setText("");
        unitPriceDisplay.setText("");
        qtyOnHandDisplay.setText("");
    }

    private ObservableList<String> getCustomerIDs() {
        ObservableList<String> customerIDs = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = CenterController.getInstance().getAllCustomers();

            while (resultSet.next()) {
                customerIDs.add("0" + resultSet.getString("customerId"));
            }

        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }

        return customerIDs;
    }

    private ObservableList<String> getItemCODEs() {
        ObservableList<String> itemCODEs = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = CenterController.getInstance().getAllItem();

            while (resultSet.next()) {
                itemCODEs.add(resultSet.getString("itemCode"));
            }

        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }

        return itemCODEs;
    }

    private void loadDateAndTime(){

    }

    // id generate
    /*public String getId(String tableName, String PKColName, String idChar, String firstId){
        try {
            ResultSet resultTableRowCount = getTableRowCount(tableName);
            resultTableRowCount.next();

            int size = resultTableRowCount.getInt("row_count");

            if (size > 0) {
                ResultSet resultSet = getTableLastId(tableName, PKColName);
                resultSet.next();

                String lastId = resultSet.getString(PKColName);

                String[] part = lastId.split(idChar);
                int num = Integer.parseInt(part[1]);
                num++;

                return String.format(idChar+"%03d", num);
            } else {
                return firstId;
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showOptionDialog(null, e.getMessage(), "Error", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, centralController.errorIcon, centralController.getInstance().getOkButton(), centralController.getInstance().getOkButton()[0]);
        }
        return null;
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDs.setItems(getCustomerIDs());
        customerIDs.setVisibleRowCount(5);
        itemCODEs.setItems(getItemCODEs());
        itemCODEs.setVisibleRowCount(5);
    }
}
