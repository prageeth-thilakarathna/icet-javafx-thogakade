package edu.icet.demo.controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.controller.CenterController;
import edu.icet.demo.model.Customer;
import edu.icet.demo.util.BoType;
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
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private TableView<Customer> tableFirst;
    @FXML
    private TableColumn<Customer, String> colFirstTblCustomerId;
    @FXML
    private TableColumn<Customer, String> colTitle;
    @FXML
    private TableColumn<Customer, String> colName;
    @FXML
    private TableColumn<Customer, String> colDateOfBarth;
    @FXML
    private TableColumn<Customer, String> colSalary;
    @FXML
    private TableView<Customer> tableSecond;
    @FXML
    private TableColumn<Customer, String> colSecondTblCustomerId;
    @FXML
    private TableColumn<Customer, String> colAddress;
    @FXML
    private TableColumn<Customer, String> colCity;
    @FXML
    private TableColumn<Customer, String> colProvince;
    @FXML
    private TableColumn<Customer, String> colPostalCode;
    @FXML
    private Label salaryError;
    @FXML
    private Label postalCodeError;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnAddCustomer;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private JFXTextField customerIdInput;
    @FXML
    private Label customerIdError;
    @FXML
    private JFXComboBox<String> title;
    @FXML
    private JFXTextField nameInput;
    @FXML
    private DatePicker dateOfBarth;
    @FXML
    private JFXTextField salaryInput;
    @FXML
    private JFXTextField addressInput;
    @FXML
    private JFXTextField cityInput;
    @FXML
    private JFXTextField provinceInput;
    @FXML
    private JFXTextField postalCodeInput;
    private Customer searchCustomer;
    private static final String CHAR_SAFE = "* only digits(0-9)";
    private static final String CUSTOMER_ID = "id";
    private static final String TITLE_TYPE = "title";
    private static final String NAME = "name";
    private static final String DATE_OF_BARTH = "dateOfBarth";
    private static final String SALARY = "salary";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String PROVINCE = "province";
    private static final String POSTAL_CODE = "postalCode";
    private static final String CUSTOMER_ALERT_NAME = " customer.";

    private final CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    // add customer
    @FXML
    private void addCustomerAction() {
        try {
            Customer customer = new Customer(
                    customerIdInput.getText(),
                    title.getValue(),
                    nameInput.getText(),
                    dateOfBarth.getValue(),
                    Double.parseDouble(salaryInput.getText()),
                    addressInput.getText(),
                    cityInput.getText(),
                    provinceInput.getText(),
                    postalCodeInput.getText()
            );
            boolean res = customerBo.addCustomer(customer);
            if (res) {
                CenterController.alert.setAlertType(Alert.AlertType.INFORMATION);
                CenterController.alert.setContentText(customerIdInput.getText() + " Customer is entered into the system successfully.");
                CenterController.alert.show();
                clearForm();
            } else {
                CenterController.alert.setAlertType(Alert.AlertType.ERROR);
                CenterController.alert.setContentText("Failed! An error occurred while entering the " + customerIdInput.getText() + CUSTOMER_ALERT_NAME);
                CenterController.alert.show();
            }
        } catch (Exception exception) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(exception.getMessage());
            CenterController.alert.show();
        }
    }

    @FXML
    private void customerIdKeyPressed(KeyEvent keyEvent) {
        String value = customerIdInput.getText();
        String ch = keyEvent.getCode().getChar();
        int length = value.length();
        int count = 10;
        boolean conditionFirstEquals0 = true;
        boolean conditionChar = false;

        if (length == 0 && !ch.equals("0")) {
            conditionFirstEquals0 = false;
        }

        for (int i = 0; i < count; i++) {
            String charValue = String.valueOf(i);
            if (ch.equals(charValue)) {
                conditionChar = true;
                count = i + 1;
            } else {
                conditionChar = false;
            }
        }

        if (length < 10 && conditionChar && conditionFirstEquals0 || keyEvent.getCode().getCode() == 8) {
            customerIdInput.setEditable(true);
            customerIdError.setText("");
        } else {
            customerIdInput.setEditable(false);
            customerIdError.setTextFill(Color.RED);
            if (!conditionFirstEquals0) {
                customerIdError.setText("* Not a first digit==0");
            } else {
                customerIdError.setText(CHAR_SAFE);
            }
        }
    }

    @FXML
    private void customerIdKeyTyped() {
        validateInputs();
    }

    @FXML
    private void titleAction() {
        validateInputs();
    }

    @FXML
    private void nameKeyPressed(KeyEvent keyEvent) {
        int length = nameInput.getText().length();
        nameInput.setEditable(length < 30 || keyEvent.getCode().getCode() == 8);
    }

    @FXML
    private void nameKeyTyped() {
        validateInputs();
    }

    @FXML
    private void dateOfBarthAction() {
        validateInputs();
    }

    @FXML
    private void salaryKeyPressed(KeyEvent keyEvent) {
        int length = salaryInput.getText().length();
        String ch = keyEvent.getCode().getChar();

        boolean condition = true;
        int count = 0;
        if (length > 0) {
            count++;
        }

        if (count == 0 && ch.equals("0")) {
            condition = false;
        }

        if (length < 8 && condition && (ch.charAt(0) >= '0' && ch.charAt(0) <= '9') || keyEvent.getCode().getCode() == 8 || keyEvent.getCode().getCode() == 46) {
            salaryInput.setEditable(true);
            salaryError.setText("");
        } else {
            salaryInput.setEditable(false);
            if (!condition) {
                salaryError.setText("* Not a Q==0");
            } else if (length == 8) {
                salaryError.setText("* only 08 digits");
            } else {
                salaryError.setText(CHAR_SAFE);
            }
        }
    }

    @FXML
    private void salaryKeyTyped() {
        validateInputs();
    }

    @FXML
    private void addressKeyPressed(KeyEvent keyEvent) {
        int length = addressInput.getText().length();
        addressInput.setEditable(length < 50 || keyEvent.getCode().getCode() == 8);
    }

    @FXML
    private void addressKeyTyped() {
        validateInputs();
    }

    @FXML
    private void cityKeyPressed(KeyEvent keyEvent) {
        int length = cityInput.getText().length();
        cityInput.setEditable(length < 30 || keyEvent.getCode().getCode() == 8);
    }

    @FXML
    private void cityKeyTyped() {
        validateInputs();
    }

    @FXML
    private void provinceKeyPressed(KeyEvent keyEvent) {
        int length = provinceInput.getText().length();
        provinceInput.setEditable(length < 20 || keyEvent.getCode().getCode() == 8);
    }

    @FXML
    private void provinceKeyTyped() {
        validateInputs();
    }

    @FXML
    private void postalCodeKeyPressed(KeyEvent keyEvent) {
        int length = postalCodeInput.getText().length();
        String ch = keyEvent.getCode().getChar();

        if (length < 5 && (ch.charAt(0) >= '0' && ch.charAt(0) <= '9') || keyEvent.getCode().getCode() == 8) {
            postalCodeInput.setEditable(true);
            postalCodeError.setText("");
        } else {
            postalCodeInput.setEditable(false);

            if (length == 5) {
                postalCodeError.setText("* only 05 digits");
            } else {
                postalCodeError.setText(CHAR_SAFE);
            }
        }
    }

    @FXML
    private void postalCodeKeyTyped() {
        validateInputs();
    }

    private void validateInputs() {
        int customerIdLength = customerIdInput.getText().length();
        int nameLength = nameInput.getText().length();
        int salaryLength = salaryInput.getText().length();
        int addressLength = addressInput.getText().length();
        int cityLength = cityInput.getText().length();
        int provinceLength = provinceInput.getText().length();
        int postalCodeLength = postalCodeInput.getText().length();

        Customer customer = customerBo.getCustomer(customerIdInput.getText());

        if (customer == null) {
            if (customerIdLength == 10 && title.getValue() != null && nameLength > 0 && dateOfBarth.getValue() != null && salaryLength > 0 && addressLength > 0 && cityLength > 0 && provinceLength > 0 && postalCodeLength > 0) {
                btnAddCustomer.setDisable(false);
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnSearch.setDisable(true);
                customerIdError.setTextFill(Color.GREEN);
                customerIdError.setText("* This is available.");
            } else if (customerIdLength == 10) {
                customerIdError.setTextFill(Color.GREEN);
                customerIdError.setText("* This is available.");
                btnAddCustomer.setDisable(true);
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnSearch.setDisable(true);
            } else {
                btnAddCustomer.setDisable(true);
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnSearch.setDisable(true);
            }
        } else {
            btnSearch.setDisable(searchCustomer != null);
            customerIdError.setTextFill(Color.RED);
            if (searchCustomer == null) {
                customerIdError.setText("* Sorry! This already exists.");
            } else {
                customerIdError.setText("");
            }
            if (customerIdLength == 10 && title.getValue() != null && nameLength > 0 && dateOfBarth.getValue() != null && salaryLength > 0 && addressLength > 0 && cityLength > 0 && provinceLength > 0 && postalCodeLength > 0) {
                btnAddCustomer.setDisable(true);
                validateUpdate();
                btnDelete.setDisable(false);
            } else {
                btnAddCustomer.setDisable(true);
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
            }
        }

        btnCancel.setDisable(customerIdLength == 0 && title.getValue() == null && nameLength == 0 && dateOfBarth.getValue() == null && salaryLength == 0 && addressLength == 0 && cityLength == 0 && provinceLength == 0 && postalCodeLength == 0);
    }

    private void clearForm() {
        customerIdInput.setText("");
        customerIdError.setText("");
        title.setValue(null);
        nameInput.setText("");
        dateOfBarth.setValue(null);
        salaryInput.setText("");
        salaryError.setText("");
        addressInput.setText("");
        cityInput.setText("");
        provinceInput.setText("");
        postalCodeInput.setText("");
        postalCodeError.setText("");
        tableFirst.setItems(getTableData());
        tableSecond.setItems(getTableData());
        searchCustomer = null;
        customerIdInput.setDisable(false);
        validateInputs();
    }

    private ObservableList<String> getTitles() {
        ObservableList<String> titles = FXCollections.observableArrayList();

        titles.add("Mr");
        titles.add("Mrs");
        titles.add("Miss");
        titles.add("Ms");

        return titles;
    }

    // update customer
    @FXML
    private void updateAction() {
        try {
            Customer customer = new Customer(
                    customerIdInput.getText(),
                    title.getValue(),
                    nameInput.getText(),
                    dateOfBarth.getValue(),
                    Double.parseDouble(salaryInput.getText()),
                    addressInput.getText(),
                    cityInput.getText(),
                    provinceInput.getText(),
                    postalCodeInput.getText()
            );

            boolean res = customerBo.updateCustomer(customer);
            if (res) {
                CenterController.alert.setAlertType(Alert.AlertType.INFORMATION);
                CenterController.alert.setContentText(customerIdInput.getText() + " Customer update is successfully.");
                CenterController.alert.show();
                clearForm();
            } else {
                CenterController.alert.setAlertType(Alert.AlertType.INFORMATION);
                CenterController.alert.setContentText("Failed! An error occurred while updating the " + customerIdInput.getText() + CUSTOMER_ALERT_NAME);
                CenterController.alert.show();
            }
        } catch (Exception exception) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(exception.getMessage());
            CenterController.alert.show();
        }
    }

    private void validateUpdate() {
        if (customerIdInput.getText().equals(searchCustomer.getId())) {
            if (!title.getValue().equals(searchCustomer.getTitle())) {
                btnUpdate.setDisable(false);
            } else if (!nameInput.getText().equals(searchCustomer.getName())) {
                btnUpdate.setDisable(false);
            } else if (!dateOfBarth.getValue().equals(searchCustomer.getDateOfBarth())) {
                btnUpdate.setDisable(false);
            } else if (!salaryInput.getText().equals(String.valueOf(searchCustomer.getSalary()))) {
                btnUpdate.setDisable(false);
            } else if (!addressInput.getText().equals(searchCustomer.getAddress())) {
                btnUpdate.setDisable(false);
            } else if (!cityInput.getText().equals(searchCustomer.getCity())) {
                btnUpdate.setDisable(false);
            } else if (!provinceInput.getText().equals(searchCustomer.getProvince())) {
                btnUpdate.setDisable(false);
            } else if (!postalCodeInput.getText().equals(searchCustomer.getPostalCode())) {
                btnUpdate.setDisable(false);
            } else {
                btnUpdate.setDisable(true);
            }
        } else {
            btnUpdate.setDisable(true);
        }
    }

    @FXML
    private void deleteAction() {
        try {
            Customer customer = new Customer(
                    customerIdInput.getText(),
                    title.getValue(),
                    nameInput.getText(),
                    dateOfBarth.getValue(),
                    Double.parseDouble(salaryInput.getText()),
                    addressInput.getText(),
                    cityInput.getText(),
                    provinceInput.getText(),
                    postalCodeInput.getText()
            );

            boolean res = customerBo.deleteCustomer(customer);
            if (res) {
                CenterController.alert.setAlertType(Alert.AlertType.INFORMATION);
                CenterController.alert.setContentText(customerIdInput.getText() + " Customer delete is successfully.");
                CenterController.alert.show();
                clearForm();
            } else {
                CenterController.alert.setAlertType(Alert.AlertType.INFORMATION);
                CenterController.alert.setContentText("Failed! An error occurred while deleting the " + customerIdInput.getText() + CUSTOMER_ALERT_NAME);
                CenterController.alert.show();
            }
        } catch (Exception exception) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(exception.getMessage());
            CenterController.alert.show();
        }
    }

    // search customer
    @FXML
    private void searchAction() {
        Customer customer = customerBo.getCustomer(customerIdInput.getText());

        if (customer != null) {
            title.setValue(customer.getTitle());
            nameInput.setText(customer.getName());
            dateOfBarth.setValue(customer.getDateOfBarth());
            salaryInput.setText(String.valueOf(customer.getSalary()));
            addressInput.setText(customer.getAddress());
            cityInput.setText(customer.getCity());
            provinceInput.setText(customer.getProvince());
            postalCodeInput.setText(customer.getPostalCode());
            searchCustomer = customer;
            customerIdInput.setDisable(true);
            validateInputs();
        } else {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText("Failed! " + customerIdInput.getText() + " Customer Not Found.");
            CenterController.alert.show();
        }
    }

    @FXML
    private void cancelFormAction() {
        clearForm();
        btnCancel.setDisable(true);
    }

    private ObservableList<Customer> getTableData() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        List<Customer> customerList = customerBo.getAllCustomers();
        allCustomers.addAll(customerList);
        return allCustomers;
    }

    @FXML
    private void backAction() throws IOException {
        Parent parent = new FXMLLoader(getClass().getResource("/view/home-page.fxml")).load();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(parent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setItems(getTitles());
        btnAddCustomer.setDisable(true);
        dateOfBarth.setEditable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
        btnSearch.setDisable(true);

        colFirstTblCustomerId.setCellValueFactory(new PropertyValueFactory<>(CUSTOMER_ID));
        colTitle.setCellValueFactory(new PropertyValueFactory<>(TITLE_TYPE));
        colName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        colDateOfBarth.setCellValueFactory(new PropertyValueFactory<>(DATE_OF_BARTH));
        colSalary.setCellValueFactory(new PropertyValueFactory<>(SALARY));
        colSecondTblCustomerId.setCellValueFactory(new PropertyValueFactory<>(CUSTOMER_ID));
        colAddress.setCellValueFactory(new PropertyValueFactory<>(ADDRESS));
        colCity.setCellValueFactory(new PropertyValueFactory<>(CITY));
        colProvince.setCellValueFactory(new PropertyValueFactory<>(PROVINCE));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>(POSTAL_CODE));

        tableFirst.setItems(getTableData());
        tableSecond.setItems(getTableData());
    }
}
