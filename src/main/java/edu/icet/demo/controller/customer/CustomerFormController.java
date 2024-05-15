package edu.icet.demo.controller.customer;

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

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
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
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String CHAR_SAFE = "* only digits(0-9)";
    private static final String CUSTOMER_ID = "customerId";
    private static final String TITLE_TYPE = "title";
    private static final String NAME = "name";
    private static final String DATE_OF_BARTH = "dateOfBarth";
    private static final String SALARY = "salary";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String PROVINCE = "province";
    private static final String POSTAL_CODE = "postalCode";

    private final CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    // add customer
    @FXML
    private void addCustomerAction() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        Customer customer = new Customer(
                customerIdInput.getText(),
                title.getValue(),
                nameInput.getText(),
                dateOfBarth.getValue().format(dateTimeFormatter),
                salaryInput.getText(),
                addressInput.getText(),
                cityInput.getText(),
                provinceInput.getText(),
                postalCodeInput.getText()
        );
        boolean res = customerBo.addCustomer(customer);
        if (res) {
            clearForm();
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
            if (length == 10) {
                customerIdError.setText("* input 10 digits");
            } else if (!conditionFirstEquals0) {
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

        if (length < 8 && condition && (ch.charAt(0) >= '0' && ch.charAt(0) <= '9') || keyEvent.getCode().getCode() == 8) {
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
        boolean customerIdIsHave = false;

        String id = "";
        try {
            ResultSet resultSet = customerBo.getCustomer(customerIdInput.getText());
            if (resultSet.next()) {
                customerIdIsHave = true;
                id = "0" + resultSet.getString(CUSTOMER_ID);
            }

        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }

        if (customerIdLength == 10 && title.getValue() != null && nameLength > 0 && dateOfBarth.getValue() != null && salaryLength > 0 && addressLength > 0 && cityLength > 0 && provinceLength > 0 && postalCodeLength > 0 && !customerIdIsHave) {
            btnAddCustomer.setDisable(false);
            btnUpdate.setDisable(true);
        } else if (customerIdLength == 10 && title.getValue() != null && nameLength > 0 && dateOfBarth.getValue() != null && salaryLength > 0 && addressLength > 0 && cityLength > 0 && provinceLength > 0 && postalCodeLength > 0 && customerIdIsHave) {
            validateUpdate(id);
            btnAddCustomer.setDisable(true);
            btnDelete.setDisable(false);
        } else {
            btnAddCustomer.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        Customer customer = new Customer(
                customerIdInput.getText(),
                title.getValue(),
                nameInput.getText(),
                dateOfBarth.getValue().format(dateTimeFormatter),
                salaryInput.getText(),
                addressInput.getText(),
                cityInput.getText(),
                provinceInput.getText(),
                postalCodeInput.getText()
        );

        boolean res = customerBo.updateCustomer(customer);
        if (res) {
            clearForm();
        }
    }

    private void validateUpdate(String id) {
        setCustomerToSearchCustomer(Objects.requireNonNull(searchCustomer(id)));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate searchDateOfBarth = LocalDate.parse(searchCustomer.getDateOfBarth(), dateTimeFormatter);

        if (customerIdInput.getText().equals(searchCustomer.getCustomerId())) {
            if (!title.getValue().equals(searchCustomer.getTitle())) {
                btnUpdate.setDisable(false);
            } else if (!nameInput.getText().equals(searchCustomer.getName())) {
                btnUpdate.setDisable(false);
            } else if (!dateOfBarth.getValue().equals(searchDateOfBarth)) {
                btnUpdate.setDisable(false);
            } else if (!salaryInput.getText().equals(searchCustomer.getSalary())) {
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
        boolean res = customerBo.deleteCustomer(customerIdInput.getText());
        if (res) {
            clearForm();
        }
    }

    // search customer
    @FXML
    private void searchAction() {
        try {
            ResultSet resultSet = searchCustomer(customerIdInput.getText());

            assert resultSet != null;
            title.setValue(resultSet.getString(TITLE_TYPE));
            nameInput.setText(resultSet.getString(NAME));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            LocalDate localDate = LocalDate.parse(resultSet.getString(DATE_OF_BARTH), dateTimeFormatter);
            dateOfBarth.setValue(localDate);
            salaryInput.setText(resultSet.getString(SALARY));
            addressInput.setText(resultSet.getString(ADDRESS));
            cityInput.setText(resultSet.getString(CITY));
            provinceInput.setText(resultSet.getString(PROVINCE));
            postalCodeInput.setText(resultSet.getString(POSTAL_CODE));
            validateInputs();

        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    private void setCustomerToSearchCustomer(ResultSet resultSet) {
        try {
            searchCustomer = new Customer(
                    "0" + resultSet.getString(CUSTOMER_ID),
                    resultSet.getString(TITLE_TYPE),
                    resultSet.getString(NAME),
                    resultSet.getString(DATE_OF_BARTH),
                    resultSet.getString(SALARY),
                    resultSet.getString(ADDRESS),
                    resultSet.getString(CITY),
                    resultSet.getString(PROVINCE),
                    resultSet.getString(POSTAL_CODE)
            );

        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    private ResultSet searchCustomer(String id) {
        try {
            ResultSet resultSet = customerBo.getCustomer(id);
            resultSet.next();
            return resultSet;

        } catch (SQLException e) {
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
            searchCustomer = null;
            validateInputs();

            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
        return null;
    }

    @FXML
    private void cancelFormAction() {
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
        searchCustomer = null;
        validateInputs();
    }

    private ObservableList<Customer> getTableData() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = customerBo.getAllCustomers();

            while (resultSet.next()) {
                Customer customer = new Customer(
                        "0" + resultSet.getString(CUSTOMER_ID),
                        resultSet.getString(TITLE_TYPE),
                        resultSet.getString(NAME),
                        resultSet.getString(DATE_OF_BARTH),
                        resultSet.getString(SALARY),
                        resultSet.getString(ADDRESS),
                        resultSet.getString(CITY),
                        resultSet.getString(PROVINCE),
                        resultSet.getString(POSTAL_CODE)
                );
                allCustomers.add(customer);
            }

        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }

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
