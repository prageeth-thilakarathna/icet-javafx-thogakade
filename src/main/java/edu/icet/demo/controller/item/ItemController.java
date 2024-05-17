package edu.icet.demo.controller.item;

import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField txtItemCode;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXTextField txtPackSize;
    @FXML
    private JFXTextField txtUnitPrice;
    @FXML
    private JFXTextField txtQtyOnHand;
    @FXML
    private TableView<Item> tblItem;
    @FXML
    private TableColumn<Item, String> colItemCode;
    @FXML
    private TableColumn<Item, String> colDescription;
    @FXML
    private TableColumn<Item, String> colPackSize;
    @FXML
    private TableColumn<Item, String> colUnitPrice;
    @FXML
    private TableColumn<Item, String> colQtyOnHand;
    @FXML
    private Label dspNextItemCode;

    @FXML
    private void backAction() throws IOException {
        Parent parent = new FXMLLoader(getClass().getResource("/view/home-page.fxml")).load();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(parent);
    }

    @FXML
    private void searchAction(ActionEvent actionEvent) {
    }

    @FXML
    private void cancelAction(ActionEvent actionEvent) {
    }

    @FXML
    private void addItemAction(ActionEvent actionEvent) {
    }

    @FXML
    private void updateAction(ActionEvent actionEvent) {
    }

    @FXML
    private void deleteAction(ActionEvent actionEvent) {
    }

    private void getNextItemCode(){
        
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
