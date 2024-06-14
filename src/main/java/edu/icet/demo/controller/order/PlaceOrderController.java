package edu.icet.demo.controller.order;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.bo.custom.ItemBo;
import edu.icet.demo.bo.custom.OrderBo;
import edu.icet.demo.bo.custom.OrderDetailBo;
import edu.icet.demo.controller.CenterController;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.entity.ItemEntity;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.model.*;
import edu.icet.demo.util.BoType;
import edu.icet.demo.util.HibernateUtil;
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
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    private ObservableList<TblOrderDetail> tblOrderDetailData = FXCollections.observableArrayList();
    private final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private final OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private final CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private final ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    @FXML
    private void customerIdSelectAction() {
        try {
            if (!Objects.equals(customerIDs.getValue(), "")) {
                Customer customer = customerBo.getCustomer(customerIDs.getValue());
                if (customer.getName().length() < 10) {
                    nameDisplay.setText(customer.getName().substring(0, customer.getName().length()));
                } else {
                    nameDisplay.setText(customer.getName().substring(0, 10) + "...");
                    nameDisplay.setTooltip(new Tooltip(customer.getName()));
                }

                if (customer.getAddress().length() < 10) {
                    addressDisplay.setText(customer.getAddress().substring(0, customer.getAddress().length()));
                } else {
                    addressDisplay.setText(customer.getAddress().substring(0, 10) + "...");
                    addressDisplay.setTooltip(new Tooltip(customer.getAddress()));
                }

                salaryDisplay.setText(String.valueOf(customer.getSalary()));

                if (customer.getCity().length() < 10) {
                    cityDisplay.setText(customer.getCity().substring(0, customer.getCity().length()));
                } else {
                    cityDisplay.setText(customer.getCity().substring(0, 10) + "...");
                    cityDisplay.setTooltip(new Tooltip(customer.getCity()));
                }
                dspCustomerId.setText(customerIDs.getValue());
                inputValidation();
            }
        } catch (Exception e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    @FXML
    private void itemCodeSelectAction() {
        try {
            if (!Objects.equals(itemCODEs.getValue(), "")) {
                Item item = itemBo.getItem(itemCODEs.getValue());
                if (item.getDescription().length() < 10) {
                    descriptionDisplay.setText(item.getDescription().substring(0, item.getDescription().length()));
                } else {
                    descriptionDisplay.setText(item.getDescription().substring(0, 10) + "...");
                    descriptionDisplay.setTooltip(new Tooltip(item.getDescription()));
                }

                packSizeDisplay.setText(item.getPackSize());
                unitPriceDisplay.setText(String.valueOf(item.getUnitPrice()));
                qtyOnHandDisplay.setText(String.valueOf(item.getQtyOnHand()));
                inputValidation();
            }
        } catch (Exception e) {
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
        customerIDs.setValue("");
        nameDisplay.setText("");
        addressDisplay.setText("");
        salaryDisplay.setText("");
        cityDisplay.setText("");
        itemCODEs.setValue("");
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
            List<Customer> customerList = customerBo.getAllCustomers();
            for (Customer customer : customerList) {
                customerIdList.add(customer.getId());
            }
        } catch (Exception e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return customerIdList;
    }

    private ObservableList<String> getItemCODEs() {
        ObservableList<String> itemCodeList = FXCollections.observableArrayList();
        try {
            List<Item> itemList = itemBo.getAllItems();
            for (Item item : itemList) {
                if(tblOrderDetailData.isEmpty()){
                    itemCodeList.add(item.getId());
                } else {
                    if(!isExist(item.getId())){
                        itemCodeList.add(item.getId());
                    }
                }
            }
        } catch (Exception e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return itemCodeList;
    }

    private boolean isExist(String id){
        for(TblOrderDetail tblOrderDetailOb : tblOrderDetailData){
            if(Objects.equals(tblOrderDetailOb.getItemCode(), id)){
                return true;
            }
        }
        return false;
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
            int size = orderBo.getTableRowCount();

            if (size > 0) {
                String lastId = orderBo.getTableLastId().getId();

                String[] part = lastId.split("OR");
                int num = Integer.parseInt(part[1]);
                num++;

                return String.format("OR%04d", num);
            } else {
                return "OR0001";
            }
        } catch (Exception e) {
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
        itemCODEs.setItems(getItemCODEs());
        itemCODEs.setVisibleRowCount(5);
    }

    private void inputValidation() {
        if (!Objects.equals(customerIDs.getValue(), "") && !Objects.equals(itemCODEs.getValue(), "") && !inputQuantity.getText().isEmpty()) {
            Item item = itemBo.getItem(itemCODEs.getValue());
            if(Integer.parseInt(inputQuantity.getText())<=item.getQtyOnHand()){
                btnAddToCart.setDisable(false);
                quantityError.setText("");
            } else {
                btnAddToCart.setDisable(true);
                quantityError.setText("* Not QTY greater than QTYOnHand");
            }
        } else {
            btnAddToCart.setDisable(true);
        }

        if (!Objects.equals(customerIDs.getValue(), "") || !Objects.equals(itemCODEs.getValue(), "") || !inputQuantity.getText().isEmpty()) {
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
            Item item = itemBo.getItem(itemCode);
            String total = CenterController.df.format(Integer.parseInt(quantity) * item.getUnitPrice());
            TblOrderDetail orderDetail = new TblOrderDetail(
                    item.getId(),
                    item.getDescription(),
                    quantity,
                    String.valueOf(item.getUnitPrice()),
                    total
            );
            tblOrderDetailData.add(orderDetail);
            this.tblOrderDetail.setItems(tblOrderDetailData);
            btnPlaceOrder.setDisable(tblOrderDetailData.isEmpty());
        } catch (Exception e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    @FXML
    private void placeOrderAction() {
        try {
            Date date = new Date();
            CustomerEntity customerEntity = new ModelMapper().map(customerBo.getCustomer(dspCustomerId.getText()), CustomerEntity.class);
            Order order = new Order(
                    orderIdDisplay.getText(),
                    date,
                    customerEntity
            );
            orderBo.placeOrder(order);
            updateInventory();
            addOrderDetail(new ModelMapper().map(order, OrderEntity.class));

            CenterController.alert.setAlertType(Alert.AlertType.INFORMATION);
            CenterController.alert.setContentText(orderIdDisplay.getText() + " Order is entered into the system successfully.");
            CenterController.alert.show();
            orderIdDisplay.setText(getOrderId());
            btnPlaceOrder.setDisable(true);
            clearFields();
            dspCustomerId.setText("");
            tblOrderDetailData = FXCollections.observableArrayList();
            tblOrderDetail.setItems(tblOrderDetailData);
            itemCODEs.setItems(getItemCODEs());
            itemCODEs.setVisibleRowCount(5);

        } catch (Exception e) {
            HibernateUtil.singletonRollback();
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        } finally {
            HibernateUtil.singletonSessionClose();
        }
    }

    private void addOrderDetail(OrderEntity orderEntity) {
        for (TblOrderDetail tblOb : tblOrderDetailData) {
            OrderDetail orderDetail = new OrderDetail();

            ItemEntity itemEntity = new ModelMapper().map(itemBo.getItem(tblOb.getItemCode()), ItemEntity.class);

            orderDetail.setOrder(orderEntity);
            orderDetail.setItem(itemEntity);
            orderDetail.setQuantity(Integer.parseInt(tblOb.getQuantity()));

            orderDetailBo.addOrderDetail(orderDetail);
        }
        HibernateUtil.singletonCommit();
    }

    private void updateInventory(){
        for (TblOrderDetail tblOb : tblOrderDetailData) {
            String[] arr = getNewQtyOnHand(tblOb.getItemCode(), tblOb.getQuantity());
            Item item = new Item(
                    tblOb.getItemCode(),
                    tblOb.getDescription(),
                    arr[1],
                    Double.parseDouble(tblOb.getUnitPrice()),
                    Integer.parseInt(arr[0])
            );
            itemBo.updateInventory(item);
        }
    }

    private String[] getNewQtyOnHand(String itemCode, String qty) {
        try {
            Item item = itemBo.getItem(itemCode);
            String newValue = String.valueOf(item.getQtyOnHand() - Integer.parseInt(qty));
            return new String[]{newValue, item.getPackSize()};
        } catch (Exception e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return new String[0];
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
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
