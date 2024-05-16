package edu.icet.demo.controller.orders;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.demo.controller.CenterController;
import edu.icet.demo.db.LoadDBDriver;
import edu.icet.demo.model.TblOrderDetail;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {
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

    private ObservableList<TblOrderDetail> tblOrderDetails = FXCollections.observableArrayList();

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
                inputValidation();
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
    }

    private void clearFields(){
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

    // set data and time
    private void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateDisplay.setText(simpleDateFormat.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime localTime = LocalTime.now();
            timeDisplay.setText(localTime.getHour()+":"+localTime.getMinute()+":"+localTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // id generate
    private String getOrderId(){
        try {
            ResultSet resultTableRowCount = getTableRowCount();
            resultTableRowCount.next();

            int size = resultTableRowCount.getInt("row_count");

            if (size > 0) {
                ResultSet resultSet = getTableLastId();
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

    // get table row count
    private ResultSet getTableRowCount() throws SQLException{
        String sql = "SELECT COUNT(*) AS row_count FROM orders";
        Connection connection = LoadDBDriver.getLoadDBDriverInstance().getConnection();
        Statement stm = connection.createStatement();
        return stm.executeQuery(sql);
    }

    // get table last id
    private ResultSet getTableLastId() throws SQLException {
        String sql = "SELECT * FROM orders ORDER BY orderId DESC LIMIT 1";
        Connection connection = LoadDBDriver.getLoadDBDriverInstance().getConnection();
        Statement stm = connection.createStatement();
        return stm.executeQuery(sql);
    }

    @FXML
    private void addToCartAction(ActionEvent actionEvent) {
        addOrderDetail(itemCODEs.getValue(), inputQuantity.getText());
    }

    private void inputValidation(){
        if(customerIDs.getValue()!=null && itemCODEs.getValue()!=null && !inputQuantity.getText().isEmpty()){
            btnAddToCart.setDisable(false);
        } else {
            btnAddToCart.setDisable(true);
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
    private void quantityKeyTyped(KeyEvent keyEvent) {
        inputValidation();
    }

    private void addOrderDetail(String itemCode, String quantity){
        try{
            ResultSet resultSet = CenterController.getInstance().getItem(itemCode);
            resultSet.next();

            String total = CenterController.df.format(Integer.parseInt(quantity)*resultSet.getDouble("unitPrice"));

            TblOrderDetail tblOrderDetail = new TblOrderDetail(
                    resultSet.getString("itemCode"),
                    resultSet.getString("description"),
                    quantity,
                    resultSet.getString("unitPrice"),
                    total
            );

            tblOrderDetails.add(tblOrderDetail);
            this.tblOrderDetail.setItems(tblOrderDetails);

            if(!tblOrderDetails.isEmpty()){
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
    private void placeOrderAction(ActionEvent actionEvent) {
        String sql = "INSERT INTO orders VALUES(?,?,?)";

        try{
            Connection connection = LoadDBDriver.getLoadDBDriverInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateAndTime = simpleDateFormat.format(date)+" "+timeDisplay.getText();

            preparedStatement.setString(1, orderIdDisplay.getText());
            preparedStatement.setString(2, dateAndTime);
            preparedStatement.setInt(3, Integer.parseInt(customerIDs.getValue()));
            preparedStatement.executeUpdate();
            saveOrderDetail();

            CenterController.alert.setAlertType(Alert.AlertType.INFORMATION);
            CenterController.alert.setContentText(orderIdDisplay.getText()+" Order is entered into the system successfully.");
            CenterController.alert.show();
            orderIdDisplay.setText(getOrderId());
            btnPlaceOrder.setDisable(true);
            clearFields();
            tblOrderDetails = null;
            tblOrderDetail.setItems(tblOrderDetails);

        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    private void saveOrderDetail() throws SQLException {
        for(int i = 0; i< tblOrderDetails.size(); i++){
            String sql = "INSERT INTO orderDetail VALUES(?,?,?)";

            Connection connection = LoadDBDriver.getLoadDBDriverInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, orderIdDisplay.getText());
            preparedStatement.setString(2, tblOrderDetails.get(i).getItemCode());
            preparedStatement.setInt(3, Integer.parseInt(tblOrderDetails.get(i).getQuantity()));
            preparedStatement.executeUpdate();
        }
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
        btnPlaceOrder.setDisable(true);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
