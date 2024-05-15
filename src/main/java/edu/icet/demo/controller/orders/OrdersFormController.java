package edu.icet.demo.controller.orders;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class OrdersFormController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView tblOrders;
    @FXML
    private TableColumn colOrderId;
    @FXML
    private TableColumn colDateAndTime;
    @FXML
    private TableColumn colCustomerId;
    @FXML
    private TableView tblOrderDetail;
    @FXML
    private TableColumn colItemCode;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colQuantity;
    @FXML
    private TableColumn colUnitPrice;
    @FXML
    private TableColumn colTotal;
    @FXML
    private JFXTextField txtOrderId;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private Label dspCustomerId;
    @FXML
    private Label dspDateAndTime;
    @FXML
    private Label dspName;
    @FXML
    private Label dspAddress;
    @FXML
    private Label dspCity;
    @FXML
    private Label dspProvince;
    @FXML
    private Label dspPostalCode;

    @FXML
    private void searchAction(ActionEvent actionEvent) {
    }

    @FXML
    private void cancelAction(ActionEvent actionEvent) {
    }

    @FXML
    private void deleteAction(ActionEvent actionEvent) {
    }

    @FXML
    private void backAction(ActionEvent actionEvent) {
    }
}
