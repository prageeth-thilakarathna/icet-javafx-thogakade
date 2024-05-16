package edu.icet.demo.controller.orders;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.bo.custom.ItemBo;
import edu.icet.demo.bo.custom.OrderBo;
import edu.icet.demo.bo.custom.OrderDetailBo;
import edu.icet.demo.controller.CenterController;
import edu.icet.demo.model.TblOrderDetail;
import edu.icet.demo.model.Order;
import edu.icet.demo.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Order> tblOrder;
    @FXML
    private TableColumn<Order, String> colOrderId;
    @FXML
    private TableColumn<Order, String> colOrderDate;
    @FXML
    private TableColumn<Order, String> colCustomerId;
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

    private final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private final OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private final ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private final CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    @FXML
    private void searchAction() {
        try{
            ResultSet resultSet = orderBo.getOrder(txtOrderId.getText());
            resultSet.next();
            txtOrderId.setEditable(false);
            btnSearch.setDisable(true);
            btnCancel.setDisable(false);
            btnDelete.setDisable(false);
            dspCustomerId.setText("0"+resultSet.getString("customerId"));
            dspDateAndTime.setText(resultSet.getString("orderDate"));

            ResultSet resultSetCustomer = customerBo.getCustomer("0"+resultSet.getString("customerId"));
            resultSetCustomer.next();
            dspName.setText(resultSetCustomer.getString("name"));
            dspAddress.setText(resultSetCustomer.getString("address"));
            dspCity.setText(resultSetCustomer.getString("city"));
            dspProvince.setText(resultSetCustomer.getString("province"));
            dspPostalCode.setText(resultSetCustomer.getString("postalCode"));

            ResultSet resultSetOrderDetail = orderDetailBo.getOrderDetail(resultSet.getString("orderId"));
            ObservableList<TblOrderDetail> tblOrderDetailList = FXCollections.observableArrayList();

            while (resultSetOrderDetail.next()){
                ResultSet resultSetItem = itemBo.getItem(resultSetOrderDetail.getString("itemCode"));
                resultSetItem.next();

                String total = CenterController.df.format(resultSetOrderDetail.getInt("quantity")*resultSetItem.getDouble("unitPrice"));
                TblOrderDetail tblOrderDetail = new TblOrderDetail(
                        resultSetItem.getString("itemCode"),
                        resultSetItem.getString("description"),
                        resultSetOrderDetail.getString("quantity"),
                        resultSetItem.getString("unitPrice"),
                        total
                );
                tblOrderDetailList.add(tblOrderDetail);
            }
            setOrderDetailData(tblOrderDetailList);
        } catch (SQLException e) {
            btnSearch.setDisable(false);
            txtOrderId.setEditable(true);
            btnCancel.setDisable(true);
            btnDelete.setDisable(true);

            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    private void setOrderDetailData(ObservableList<TblOrderDetail> tblOrderDetail){
        this.tblOrderDetail.setItems(tblOrderDetail);
    }

    private void clearForm(){
        txtOrderId.setText("");
        btnDelete.setDisable(true);
        dspCustomerId.setText("");
        dspDateAndTime.setText("");
        dspName.setText("");
        dspAddress.setText("");
        dspCity.setText("");
        dspProvince.setText("");
        dspPostalCode.setText("");
        setOrderDetailData(FXCollections.observableArrayList());
        txtOrderId.setEditable(true);
        btnSearch.setDisable(false);
        btnCancel.setDisable(true);
    }

    @FXML
    private void cancelAction() {
        clearForm();
    }

    @FXML
    private void deleteAction(ActionEvent actionEvent) {
    }

    @FXML
    private void placeOrderAction(ActionEvent actionEvent) {
    }

    @FXML
    private void backAction(ActionEvent actionEvent) {
    }

    private ObservableList<Order> getOrdersTableData(){
        ObservableList<Order> allOrders = FXCollections.observableArrayList();
        try{
            ResultSet resultSet = orderBo.getAllOrders();
            while(resultSet.next()){
                Order order = new Order(
                        resultSet.getString("orderId"),
                        resultSet.getString("orderDate"),
                        resultSet.getString("customerId")
                );
                allOrders.add(order);
            }
            return allOrders;
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrder.setItems(getOrdersTableData());

    }
}
