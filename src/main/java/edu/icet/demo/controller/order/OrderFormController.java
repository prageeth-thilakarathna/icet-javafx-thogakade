package edu.icet.demo.controller.order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.bo.custom.ItemBo;
import edu.icet.demo.bo.custom.OrderBo;
import edu.icet.demo.bo.custom.OrderDetailBo;
import edu.icet.demo.controller.CenterController;
import edu.icet.demo.model.*;
import edu.icet.demo.util.BoType;
import edu.icet.demo.util.HibernateUtil;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<TblOrder> tblOrder;
    @FXML
    private TableColumn<TblOrder, String> colOrderId;
    @FXML
    private TableColumn<TblOrder, String> colOrderDate;
    @FXML
    private TableColumn<TblOrder, String> colCustomerId;
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
        try {
            Order order = orderBo.getOrder(txtOrderId.getText());
            txtOrderId.setEditable(false);
            btnSearch.setDisable(true);
            btnCancel.setDisable(false);
            btnDelete.setDisable(false);
            dspCustomerId.setText(order.getCustomer().getId());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            dspDateAndTime.setText(dateFormat.format(order.getOrderDate()));

            Customer customer = customerBo.getCustomer(order.getCustomer().getId());
            dspName.setText(customer.getName());
            dspAddress.setText(customer.getAddress());
            dspCity.setText(customer.getCity());
            dspProvince.setText(customer.getProvince());
            dspPostalCode.setText(customer.getPostalCode());

            List<OrderDetail> orderDetailList = orderDetailBo.getOrderDetail(order);
            ObservableList<TblOrderDetail> tblOrderDetailList = FXCollections.observableArrayList();

            for (OrderDetail orderDetail : orderDetailList) {
                Item item = itemBo.getItem(orderDetail.getItem().getId());
                String total = CenterController.df.format(orderDetail.getQuantity() * item.getUnitPrice());
                TblOrderDetail tblOrderDetailOb = new TblOrderDetail(
                        item.getId(),
                        item.getDescription(),
                        String.valueOf(orderDetail.getQuantity()),
                        String.valueOf(item.getUnitPrice()),
                        total
                );
                tblOrderDetailList.add(tblOrderDetailOb);
            }
            setOrderDetailData(tblOrderDetailList);
        } catch (Exception e) {
            btnSearch.setDisable(false);
            txtOrderId.setEditable(true);
            btnCancel.setDisable(true);
            btnDelete.setDisable(true);

            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
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
        try {
            orderBo.deleteOrder(orderBo.getOrder(txtOrderId.getText()));
            updateInventory();
            CenterController.alert.setAlertType(Alert.AlertType.INFORMATION);
            CenterController.alert.setContentText(txtOrderId.getText() + " Order delete is successfully.");
            CenterController.alert.show();
            clearForm();
            tblOrder.setItems(getOrdersTableData());

        } catch (Exception e) {
            HibernateUtil.singletonRollback();
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        } finally {
            HibernateUtil.singletonSessionClose();
        }
    }

    private void updateInventory() {
        List<OrderDetail> orderDetailList = orderDetailBo.getOrderDetail(orderBo.getOrder(txtOrderId.getText()));
        for (OrderDetail orderDetail : orderDetailList) {
            Integer[] arr = getNewQtyOnHand(orderDetail.getItem().getId(), orderDetail.getQuantity());
            Item item = new Item(
                    orderDetail.getItem().getId(),
                    orderDetail.getItem().getDescription(),
                    orderDetail.getItem().getPackSize(),
                    orderDetail.getItem().getUnitPrice(),
                    arr[0]
            );
            itemBo.updateInventory(item);
        }
        HibernateUtil.singletonCommit();
    }

    private Integer[] getNewQtyOnHand(String itemCode, Integer qty) {
        Item item = itemBo.getItem(itemCode);
        int newValue = item.getQtyOnHand() + qty;
        return new Integer[]{newValue};
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

    private ObservableList<TblOrder> getOrdersTableData() {
        ObservableList<TblOrder> tblOrders = FXCollections.observableArrayList();
        try {
            List<Order> orderList = orderBo.getAllOrders();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            for (Order order : orderList) {
                TblOrder tblOrderOb = new TblOrder(
                        order.getId(),
                        dateFormat.format(order.getOrderDate()),
                        order.getCustomer().getId()
                );
                tblOrders.add(tblOrderOb);
            }
            return tblOrders;
        } catch (Exception e) {
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
