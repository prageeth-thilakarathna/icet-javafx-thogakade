package edu.icet.demo.controller.orders;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.bo.custom.ItemBo;
import edu.icet.demo.bo.custom.OrderBo;
import edu.icet.demo.bo.custom.OrderDetailBo;
import edu.icet.demo.controller.CenterController;
import edu.icet.demo.model.Item;
import edu.icet.demo.model.TblOrderDetail;
import edu.icet.demo.model.Order;
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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
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
    private static final String CUSTOMER_ID = "customerId";
    private static final String ORDER_DATE = "orderDate";
    private static final String ORDER_ID = "orderId";
    private static final String ITEM_CODE = "itemCode";
    private static final String QUANTITY = "quantity";
    private static final String UNIT_PRICE = "unitPrice";
    private static final String DESCRIPTION = "description";

    private final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private final OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private final ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private final CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    @FXML
    private void searchAction() {
        /*try {
            ResultSet resultSet = orderBo.getOrder(txtOrderId.getText());
            resultSet.next();
            txtOrderId.setEditable(false);
            btnSearch.setDisable(true);
            btnCancel.setDisable(false);
            btnDelete.setDisable(false);
            dspCustomerId.setText("0" + resultSet.getString(CUSTOMER_ID));
            dspDateAndTime.setText(resultSet.getString(ORDER_DATE));

            /*ResultSet resultSetCustomer = customerBo.getCustomer("0" + resultSet.getString(CUSTOMER_ID));
            resultSetCustomer.next();
            dspName.setText(resultSetCustomer.getString("name"));
            dspAddress.setText(resultSetCustomer.getString("address"));
            dspCity.setText(resultSetCustomer.getString("city"));
            dspProvince.setText(resultSetCustomer.getString("province"));
            dspPostalCode.setText(resultSetCustomer.getString("postalCode"));

            ResultSet resultSetOrderDetail = orderDetailBo.getOrderDetail(resultSet.getString(ORDER_ID));
            ObservableList<TblOrderDetail> tblOrderDetailList = FXCollections.observableArrayList();

            while (resultSetOrderDetail.next()) {
                ResultSet resultSetItem = itemBo.getItem(resultSetOrderDetail.getString(ITEM_CODE));
                resultSetItem.next();

                String total = CenterController.df.format(resultSetOrderDetail.getInt(QUANTITY) * resultSetItem.getDouble(UNIT_PRICE));
                TblOrderDetail orderDetail = new TblOrderDetail(
                        resultSetItem.getString(ITEM_CODE),
                        resultSetItem.getString(DESCRIPTION),
                        resultSetOrderDetail.getString(QUANTITY),
                        resultSetItem.getString(UNIT_PRICE),
                        total
                );
                tblOrderDetailList.add(orderDetail);
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
        }*/
    }

    private void setOrderDetailData(ObservableList<TblOrderDetail> tblOrderDetail) {
        this.tblOrderDetail.setItems(tblOrderDetail);
    }

    private void clearForm() {
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
    private void deleteAction() {
        boolean resIVT = updateInventory(txtOrderId.getText());
        if(resIVT){
            boolean res = orderBo.deleteOrder(txtOrderId.getText());
            if (res) {
                clearForm();
                tblOrder.setItems(getOrdersTableData());
            }
        }
    }

    private boolean updateInventory(String orderId){
        /*try{
            ResultSet resultSet = orderDetailBo.getOrderDetail(orderId);
            while (resultSet.next()){
                String[] arr = getNewQtyOnHand(resultSet.getString(ITEM_CODE), resultSet.getString(QUANTITY));
                Item item = new Item(
                        resultSet.getString(ITEM_CODE),
                        arr[2],
                        arr[1],
                        arr[3],
                        arr[0]
                );
                boolean res = itemBo.updateItem(item);
                if(!res){
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }*/
        return false;
    }

    private String[] getNewQtyOnHand(String itemCode, String qty){
        /*try{
            ResultSet resultSet = itemBo.getItem(itemCode);
            resultSet.next();
            String newValue = String.valueOf(resultSet.getInt("qtyOnHand")+Integer.parseInt(qty));
            return new String[]{newValue, resultSet.getString("packSize"), resultSet.getString(DESCRIPTION), resultSet.getString(UNIT_PRICE)};
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }*/
        return new String[0];
    }

    @FXML
    private void placeOrderAction() throws IOException {
        Parent parent = new FXMLLoader(getClass().getResource("/view/place-order-form.fxml")).load();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(parent);
    }

    @FXML
    private void backAction() throws IOException {
        Parent parent = new FXMLLoader(getClass().getResource("/view/home-page.fxml")).load();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(parent);
    }

    private ObservableList<Order> getOrdersTableData() {
        ObservableList<Order> allOrders = FXCollections.observableArrayList();
        /*try {
            ResultSet resultSet = orderBo.getAllOrders();
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getString(ORDER_ID),
                        resultSet.getString(ORDER_DATE),
                        resultSet.getString(CUSTOMER_ID)
                );
                allOrders.add(order);
            }
            return allOrders;
        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }*/
        return FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
        colOrderId.setCellValueFactory(new PropertyValueFactory<>(ORDER_ID));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>(ORDER_DATE));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>(CUSTOMER_ID));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>(ITEM_CODE));
        colDescription.setCellValueFactory(new PropertyValueFactory<>(DESCRIPTION));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>(QUANTITY));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>(UNIT_PRICE));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblOrder.setItems(getOrdersTableData());
    }
}
