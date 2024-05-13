package edu.icet.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private void customerAction() throws IOException {
        Parent parent = new FXMLLoader(getClass().getResource("/view/customer-form.fxml")).load();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(parent);
    }

    @FXML
    private void ordersAction(ActionEvent actionEvent) {
    }

    @FXML
    private void itemAction(ActionEvent actionEvent) {
    }
}
