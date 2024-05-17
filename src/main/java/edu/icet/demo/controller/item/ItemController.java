package edu.icet.demo.controller.item;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.ItemBo;
import edu.icet.demo.controller.CenterController;
import edu.icet.demo.model.Item;
import edu.icet.demo.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXButton btnCancelForm;
    @FXML
    private Label dspQtyOnHandError;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnAddItem;
    @FXML
    private Label dspUnitPriceError;
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
    private Item searchItem;
    private static final String DESCRIPTION = "description";
    private static final String PACK_SIZE = "packSize";
    private static final String UNIT_PRICE = "unitPrice";
    private static final String QTY_ON_HAND = "qtyOnHand";
    private static final String ITEM_CODE = "itemCode";
    private static final String ITEM_WORD = " item.";

    private final ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    @FXML
    private void backAction() throws IOException {
        Parent parent = new FXMLLoader(getClass().getResource("/view/home-page.fxml")).load();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(parent);
    }

    @FXML
    private void searchAction() {
        try {
            ResultSet resultSet = itemBo.getItem(txtItemCode.getText());
            resultSet.next();
            btnSearch.setDisable(true);
            txtItemCode.setEditable(false);
            btnDelete.setDisable(false);

            txtDescription.setText(resultSet.getString(DESCRIPTION));
            txtPackSize.setText(resultSet.getString(PACK_SIZE));
            txtUnitPrice.setText(resultSet.getString(UNIT_PRICE));
            txtQtyOnHand.setText(resultSet.getString(QTY_ON_HAND));

            txtDescription.setFocusTraversable(false);
            txtPackSize.setFocusTraversable(false);
            txtUnitPrice.setFocusTraversable(false);
            txtQtyOnHand.setFocusTraversable(false);

            searchItem = new Item(
                    resultSet.getString(ITEM_CODE),
                    resultSet.getString(DESCRIPTION),
                    resultSet.getString(PACK_SIZE),
                    resultSet.getString(UNIT_PRICE),
                    resultSet.getString(QTY_ON_HAND)
            );
        } catch (SQLException e) {
            btnSearch.setDisable(false);
            txtItemCode.setEditable(true);
            btnDelete.setDisable(true);

            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    @FXML
    private void cancelAction() {
        clearForm();
        validateInputs();
    }

    @FXML
    private void addItemAction() {
        Item item = new Item(
                dspNextItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                txtUnitPrice.getText(),
                txtQtyOnHand.getText()
        );
        boolean res = itemBo.addItem(item);
        if (res) {
            CenterController.alert.setAlertType(Alert.AlertType.CONFIRMATION);
            CenterController.alert.setContentText(item.getItemCode() + " Item is entered into the system successfully.");
            CenterController.alert.show();
            clearForm();
            tblItem.setItems(getTableData());
            validateInputs();
            dspNextItemCode.setText(getNextItemCode());
        } else {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText("Failed! An error occurred while adding the " + item.getItemCode() + ITEM_WORD);
            CenterController.alert.show();
        }
    }

    private void validateInputs() {
        if (!txtDescription.getText().isEmpty() && !txtPackSize.getText().isEmpty() && !txtUnitPrice.getText().isEmpty() && !txtQtyOnHand.getText().isEmpty() && searchItem == null) {
            btnAddItem.setDisable(false);
        } else {
            btnAddItem.setDisable(true);
        }

        if (!txtItemCode.getText().isEmpty() || !txtDescription.getText().isEmpty() || !txtPackSize.getText().isEmpty() || !txtUnitPrice.getText().isEmpty() || !txtQtyOnHand.getText().isEmpty()) {
            btnCancelForm.setDisable(false);
        } else {
            btnCancelForm.setDisable(true);
        }

        if (searchItem != null) {
            if (!txtDescription.getText().equals(searchItem.getDescription())) {
                btnUpdate.setDisable(false);
            } else if (!txtPackSize.getText().equals(searchItem.getPackSize())) {
                btnUpdate.setDisable(false);
            } else if (!txtUnitPrice.getText().equals(searchItem.getUnitPrice())) {
                btnUpdate.setDisable(false);
            } else if (!txtQtyOnHand.getText().equals(searchItem.getQtyOnHand())) {
                btnUpdate.setDisable(false);
            } else {
                btnUpdate.setDisable(true);
            }
        } else {
            btnUpdate.setDisable(true);
        }
    }

    @FXML
    private void itemCodeKeyTyped() {
        validateInputs();
    }

    @FXML
    private void descriptionKeyPressed(KeyEvent keyEvent) {
        int length = txtDescription.getText().length();
        txtDescription.setEditable(length < 50 || keyEvent.getCode().getCode() == 8);
    }

    @FXML
    private void descriptionKeyTyped() {
        validateInputs();
    }

    @FXML
    private void packSizeKeyPressed(KeyEvent keyEvent) {
        int length = txtPackSize.getText().length();
        txtPackSize.setEditable(length < 10 || keyEvent.getCode().getCode() == 8);
    }

    @FXML
    private void packSizeKeyTyped() {
        validateInputs();
    }

    @FXML
    private void unitPriceKeyPressed(KeyEvent keyEvent) {
        int length = txtUnitPrice.getText().length();
        String ch = keyEvent.getCode().getChar();

        boolean condition = true;
        int count = 0;
        if (length > 0) {
            count++;
        }

        if (count == 0 && ch.equals("0")) {
            condition = false;
        }

        if (length < 8 && condition && (ch.charAt(0) >= '0' && ch.charAt(0) <= '9') || keyEvent.getCode().getCode() == 8) {
            txtUnitPrice.setEditable(true);
            dspUnitPriceError.setText("");
        } else {
            txtUnitPrice.setEditable(false);

            if (!condition) {
                dspUnitPriceError.setText("* Not a UnitPrice is 0");
            } else if (length == 8) {
                dspUnitPriceError.setText("* only 06 digits");
            } else {
                dspUnitPriceError.setText("* only digits(0-9)");
            }
        }
    }

    @FXML
    private void unitPriceKeyTyped() {
        validateInputs();
    }

    @FXML
    private void qtyOnHandKeyPressed(KeyEvent keyEvent) {
        String value = txtQtyOnHand.getText();
        int length = value.length();
        int chInt = keyEvent.getCode().getCode();

        boolean condition = true;
        int count = 0;
        if (length > 0) {
            count++;
        }

        if (count == 0 && chInt == 48) {
            condition = false;
        }

        if (length < 5 && condition && (chInt >= 48 && chInt <= 57) || chInt == 8) {
            txtQtyOnHand.setEditable(true);
            dspQtyOnHandError.setText("");
        } else {
            txtQtyOnHand.setEditable(false);

            if (!condition) {
                dspQtyOnHandError.setText("* Not a QTYOnHand is 0");
            } else if (length == 5) {
                dspQtyOnHandError.setText("* only 05 digits");
            } else {
                dspQtyOnHandError.setText("* only digits(0-9)");
            }
        }
    }

    @FXML
    private void qtyOnHandKeyTyped() {
        validateInputs();
    }

    private void clearForm() {
        txtItemCode.setText("");
        txtDescription.setText("");
        txtPackSize.setText("");
        txtUnitPrice.setText("");
        dspUnitPriceError.setText("");
        txtQtyOnHand.setText("");
        dspQtyOnHandError.setText("");
        btnDelete.setDisable(true);
        btnSearch.setDisable(false);
        txtItemCode.setEditable(true);
        searchItem = null;
    }

    @FXML
    private void updateAction() {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                txtUnitPrice.getText(),
                txtQtyOnHand.getText()
        );
        boolean res = itemBo.updateItem(item);
        if (res) {
            CenterController.alert.setAlertType(Alert.AlertType.CONFIRMATION);
            CenterController.alert.setContentText(item.getItemCode() + " Item update is successfully.");
            CenterController.alert.show();
            clearForm();
            tblItem.setItems(getTableData());
            validateInputs();
        } else {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText("Failed! An error occurred while updating the " + item.getItemCode() + ITEM_WORD);
            CenterController.alert.show();
        }
    }

    @FXML
    private void deleteAction() {
        boolean res = itemBo.deleteItem(txtItemCode.getText());
        if (res) {
            CenterController.alert.setAlertType(Alert.AlertType.CONFIRMATION);
            CenterController.alert.setContentText(txtItemCode.getText() + " Item delete is successfully.");
            CenterController.alert.show();
            clearForm();
            tblItem.setItems(getTableData());
            validateInputs();
            dspNextItemCode.setText(getNextItemCode());
        } else {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText("Failed! An error occurred while deleting the " + txtItemCode.getText() + ITEM_WORD);
            CenterController.alert.show();
        }
    }

    private String getNextItemCode() {
        try {
            ResultSet resultTableRowCount = itemBo.getTableRowCount();
            resultTableRowCount.next();

            int size = resultTableRowCount.getInt("row_count");

            if (size > 0) {
                ResultSet resultSet = itemBo.getTableLastId();
                resultSet.next();

                String lastId = resultSet.getString(ITEM_CODE);

                String[] part = lastId.split("P");
                int num = Integer.parseInt(part[1]);
                num++;

                return String.format("P%03d", num);
            } else {
                return "P001";
            }
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return null;
    }

    private ObservableList<Item> getTableData() {
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = itemBo.getAllItems();
            while (resultSet.next()) {
                Item item = new Item(
                        resultSet.getString(ITEM_CODE),
                        resultSet.getString(DESCRIPTION),
                        resultSet.getString(PACK_SIZE),
                        resultSet.getString(UNIT_PRICE),
                        resultSet.getString(QTY_ON_HAND)
                );
                itemList.add(item);
            }
            return itemList;
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dspNextItemCode.setText(getNextItemCode());
        colItemCode.setCellValueFactory(new PropertyValueFactory<>(ITEM_CODE));
        colDescription.setCellValueFactory(new PropertyValueFactory<>(DESCRIPTION));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>(PACK_SIZE));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>(UNIT_PRICE));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>(QTY_ON_HAND));
        tblItem.setItems(getTableData());
        btnAddItem.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnCancelForm.setDisable(true);
    }
}
