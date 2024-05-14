package edu.icet.demo.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.model.Customer;
import edu.icet.demo.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private Button btnSearch;
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

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    // add customer
    @FXML
    private void addCustomerAction() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
                customerIdError.setText("* only digits(0-9)");
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

        if (length < 30 || keyEvent.getCode().getCode() == 8) {
            nameInput.setEditable(true);
        } else {
            nameInput.setEditable(false);
        }
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
                salaryError.setText("* only digits(0-9)");
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

        if (length < 50 || keyEvent.getCode().getCode() == 8) {
            addressInput.setEditable(true);
        } else {
            addressInput.setEditable(false);
        }
    }

    @FXML
    private void addressKeyTyped() {
        validateInputs();
    }

    @FXML
    private void cityKeyPressed(KeyEvent keyEvent) {
        int length = cityInput.getText().length();

        if (length < 30 || keyEvent.getCode().getCode() == 8) {
            cityInput.setEditable(true);
        } else {
            cityInput.setEditable(false);
        }
    }

    @FXML
    private void cityKeyTyped() {
        validateInputs();
    }

    @FXML
    private void provinceKeyPressed(KeyEvent keyEvent) {
        int length = provinceInput.getText().length();

        if (length < 20 || keyEvent.getCode().getCode() == 8) {
            provinceInput.setEditable(true);
        } else {
            provinceInput.setEditable(false);
        }
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
                postalCodeError.setText("* only digits(0-9)");
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

        try {
            ResultSet resultSet = CenterController.getInstance().getCustomer(customerIdInput.getText());
            if (resultSet.next()) {
                customerIdIsHave = true;
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
            if (searchCustomer != null) {
                validateUpdate();
            } else {
                btnUpdate.setDisable(true);
            }
            btnAddCustomer.setDisable(true);
        } else {
            btnAddCustomer.setDisable(true);
            btnUpdate.setDisable(true);
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
    private void updateAction(ActionEvent actionEvent) {
    }

    private void validateUpdate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
    private void deleteAction(ActionEvent actionEvent) {

    }

    // search customer
    @FXML
    private void searchAction() {
        try {
            ResultSet resultSet = CenterController.getInstance().getCustomer(customerIdInput.getText());
            resultSet.next();

            title.setValue(resultSet.getString("title"));
            nameInput.setText(resultSet.getString("name"));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(resultSet.getString("dateOfBarth"), dateTimeFormatter);
            dateOfBarth.setValue(localDate);
            salaryInput.setText(resultSet.getString("salary"));
            addressInput.setText(resultSet.getString("address"));
            cityInput.setText(resultSet.getString("city"));
            provinceInput.setText(resultSet.getString("province"));
            postalCodeInput.setText(resultSet.getString("postalCode"));
            validateInputs();

            Customer customer = new Customer(
                    "0" + resultSet.getString("customerId"),
                    resultSet.getString("title"),
                    resultSet.getString("name"),
                    resultSet.getString("dateOfBarth"),
                    resultSet.getString("salary"),
                    resultSet.getString("address"),
                    resultSet.getString("city"),
                    resultSet.getString("province"),
                    resultSet.getString("postalCode")
            );
            searchCustomer = customer;

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
            ResultSet resultSet = CenterController.getInstance().getAllCustomers();

            while (resultSet.next()) {
                Customer customer = new Customer(
                        "0" + resultSet.getString("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("name"),
                        resultSet.getString("dateOfBarth"),
                        resultSet.getString("salary"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postalCode")
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

        colFirstTblCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDateOfBarth.setCellValueFactory(new PropertyValueFactory<>("dateOfBarth"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colSecondTblCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        tableFirst.setItems(getTableData());
        tableSecond.setItems(getTableData());
    }
}
