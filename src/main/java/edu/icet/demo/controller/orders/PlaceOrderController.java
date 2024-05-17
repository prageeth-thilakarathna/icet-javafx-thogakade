package edu.icet.demo.controller.orders;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.bo.custom.ItemBo;
import edu.icet.demo.bo.custom.OrderBo;
import edu.icet.demo.bo.custom.OrderDetailBo;
import edu.icet.demo.controller.CenterController;
import edu.icet.demo.model.Order;
import edu.icet.demo.model.OrderDetail;
import edu.icet.demo.model.TblOrderDetail;
import edu.icet.demo.util.BoType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {
    @FXML
    private Label dspCustomerId;
    @FXML
    private Button btnCancel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnPlaceOrder;
    @FXML
    private Label quantityError;
    @FXML
    private Button btnAddToCart;
    @FXML
    private TextField inputQuantity;
    @FXML
    private TableView<TblOrderDetail> tblOrderDetail;
    @FXML
    private TableColumn<TblOrderDetail, String> colItemCode;
    @FXML
    private TableColumn<TblOrderDetail, String> colDescription;
    @FXML
    private TableColumn<TblOrderDetail, String> colQuantity;
    @FXML
    private TableColumn<TblOrderDetail, String> colUnitPrice;
    @FXML
    private TableColumn<TblOrderDetail, String> colTotal;
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
    private static final String ADDRESS = "address";
    private static final String DESCRIPTION = "description";
    private static final String UNIT_PRICE = "unitPrice";
    private static final String ITEM_CODE = "itemCode";

    private ObservableList<TblOrderDetail> tblOrderDetails = FXCollections.observableArrayList();
    private final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private final OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private final CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private final ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    @FXML
    private void customerIdSelectAction() {
        try {
            ResultSet resultSet = customerBo.getCustomer(customerIDs.getValue());
            if (resultSet.next()) {
                if (resultSet.getString("name").length() < 10) {
                    nameDisplay.setText(resultSet.getString("name").substring(0, resultSet.getString("name").length()));
                } else {
                    nameDisplay.setText(resultSet.getString("name").substring(0, 10) + "...");
                    nameDisplay.setTooltip(new Tooltip(resultSet.getString("name")));
                }

                if (resultSet.getString(ADDRESS).length() < 10) {
                    addressDisplay.setText(resultSet.getString(ADDRESS).substring(0, resultSet.getString(ADDRESS).length()));
                } else {
                    addressDisplay.setText(resultSet.getString(ADDRESS).substring(0, 10) + "...");
                    addressDisplay.setTooltip(new Tooltip(resultSet.getString(ADDRESS)));
                }

                salaryDisplay.setText(resultSet.getString("salary"));

                if (resultSet.getString("city").length() < 10) {
                    cityDisplay.setText(resultSet.getString("city").substring(0, resultSet.getString("city").length()));
                } else {
                    cityDisplay.setText(resultSet.getString("city").substring(0, 10) + "...");
                    cityDisplay.setTooltip(new Tooltip(resultSet.getString("city")));
                }
                dspCustomerId.setText(customerIDs.getValue());
                inputValidation();
            }
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    @FXML
    private void itemCodeSelectAction() {
        try {
            ResultSet resultSet = itemBo.getItem(itemCODEs.getValue());
            if (resultSet.next()) {
                if (resultSet.getString(DESCRIPTION).length() < 10) {
                    descriptionDisplay.setText(resultSet.getString(DESCRIPTION).substring(0, resultSet.getString("dscription").length()));
                } else {
                    descriptionDisplay.setText(resultSet.getString(DESCRIPTION).substring(0, 10) + "...");
                    descriptionDisplay.setTooltip(new Tooltip(resultSet.getString(DESCRIPTION)));
                }

                packSizeDisplay.setText(resultSet.getString("packSize"));
                unitPriceDisplay.setText(resultSet.getString(UNIT_PRICE));
                qtyOnHandDisplay.setText(resultSet.getString("qtyOnHand"));
                inputValidation();
            }
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    @FXML
    private void selectionCancelAction() {
        clearFields();
        inputValidation();
    }

    private void clearFields() {
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
        inputQuantity.setText("");
        quantityError.setText("");
    }

    private ObservableList<String> getCustomerIDs() {
        ObservableList<String> customerIdList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = customerBo.getAllCustomers();
            while (resultSet.next()) {
                customerIdList.add("0" + resultSet.getString("customerId"));
            }
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return customerIdList;
    }

    private ObservableList<String> getItemCODEs() {
        ObservableList<String> itemCodeList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = itemBo.getAllItems();
            while (resultSet.next()) {
                itemCodeList.add(resultSet.getString(ITEM_CODE));
            }
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return itemCodeList;
    }

    // set data and time
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateDisplay.setText(simpleDateFormat.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            timeDisplay.setText(localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // id generate
    private String getOrderId() {
        try {
            ResultSet resultTableRowCount = orderBo.getTableRowCount();
            resultTableRowCount.next();

            int size = resultTableRowCount.getInt("row_count");

            if (size > 0) {
                ResultSet resultSet = orderBo.getTableLastId();
                resultSet.next();

                String lastId = resultSet.getString("orderId");

                String[] part = lastId.split("OR");
                int num = Integer.parseInt(part[1]);
                num++;

                return String.format("OR%04d", num);
            } else {
                return "OR0001";
            }
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return null;
    }

    @FXML
    private void addToCartAction() {
        addOrderDetail(itemCODEs.getValue(), inputQuantity.getText());
        clearFields();
        inputValidation();
    }

    private void inputValidation() {
        if (customerIDs.getValue() != null && itemCODEs.getValue() != null && !inputQuantity.getText().isEmpty()) {
            btnAddToCart.setDisable(false);
        } else {
            btnAddToCart.setDisable(true);
        }

        if (customerIDs.getValue() != null || itemCODEs.getValue() != null || !inputQuantity.getText().isEmpty()) {
            btnCancel.setDisable(false);
        } else {
            btnCancel.setDisable(true);
        }
    }

    @FXML
    private void quantityKeyPressed(KeyEvent keyEvent) {
        String value = inputQuantity.getText();
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

        if (length < 2 && condition && (chInt >= 48 && chInt <= 57) || chInt == 8) {
            inputQuantity.setEditable(true);
            quantityError.setText("");
        } else {
            inputQuantity.setEditable(false);

            if (!condition) {
                quantityError.setText("* Not a Q==0");
            } else if (length == 2) {
                quantityError.setText("* only 2 digits");
            } else {
                quantityError.setText("* only digits(0-9)");
            }
        }
    }

    @FXML
    private void quantityKeyTyped() {
        inputValidation();
    }

    private void addOrderDetail(String itemCode, String quantity) {
        try {
            ResultSet resultSet = itemBo.getItem(itemCode);
            resultSet.next();

            String total = CenterController.df.format(Integer.parseInt(quantity) * resultSet.getDouble(UNIT_PRICE));
            TblOrderDetail orderDetail = new TblOrderDetail(
                    resultSet.getString(ITEM_CODE),
                    resultSet.getString(DESCRIPTION),
                    quantity,
                    resultSet.getString(UNIT_PRICE),
                    total
            );
            tblOrderDetails.add(orderDetail);
            this.tblOrderDetail.setItems(tblOrderDetails);

            if (!tblOrderDetails.isEmpty()) {
                btnPlaceOrder.setDisable(false);
            } else {
                btnPlaceOrder.setDisable(true);
            }
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    @FXML
    private void placeOrderAction() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateAndTime = simpleDateFormat.format(date) + " " + timeDisplay.getText();
        Order order = new Order(
                orderIdDisplay.getText(),
                dateAndTime,
                dspCustomerId.getText()
        );
        boolean res = orderBo.placeOrder(order);
        if (res) {
            boolean response = addOrderDetail();
            if (response) {
                CenterController.alert.setAlertType(Alert.AlertType.INFORMATION);
                CenterController.alert.setContentText(orderIdDisplay.getText() + " Order is entered into the system successfully.");
                CenterController.alert.show();
                orderIdDisplay.setText(getOrderId());
                btnPlaceOrder.setDisable(true);
                clearFields();
                dspCustomerId.setText("");
                tblOrderDetails = FXCollections.observableArrayList();
                tblOrderDetail.setItems(tblOrderDetails);
            }
        }
    }

    private boolean addOrderDetail() {
        for (TblOrderDetail ob : tblOrderDetails) {
            OrderDetail orderDetail = new OrderDetail(
                    orderIdDisplay.getText(),
                    ob.getItemCode(),
                    ob.getQuantity()
            );
            boolean res = orderDetailBo.addOrderDetail(orderDetail);
            if (!res) {
                return false;
            }
        }
        return true;
    }

    @FXML
    private void goToHomeAction() throws IOException {
        Parent parent = new FXMLLoader(getClass().getResource("/view/home-page.fxml")).load();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(parent);
    }

    @FXML
    private void backAction() throws IOException {
        Parent parent = new FXMLLoader(getClass().getResource("/view/order-form.fxml")).load();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(parent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDs.setItems(getCustomerIDs());
        customerIDs.setVisibleRowCount(5);
        itemCODEs.setItems(getItemCODEs());
        itemCODEs.setVisibleRowCount(5);
        loadDateAndTime();
        orderIdDisplay.setText(getOrderId());
        btnAddToCart.setDisable(true);
        btnCancel.setDisable(true);
        btnPlaceOrder.setDisable(true);
        colItemCode.setCellValueFactory(new PropertyValueFactory<>(ITEM_CODE));
        colDescription.setCellValueFactory(new PropertyValueFactory<>(DESCRIPTION));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>(UNIT_PRICE));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
