package edu.icet.demo.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.db.LoadDBDriver;
import edu.icet.demo.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {
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
        addCustomer(customer);
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

        if (customerIdLength == 10 && title.getValue() != null && nameLength > 0 && dateOfBarth.getValue() != null && salaryLength > 0 && addressLength > 0 && cityLength > 0 && provinceLength > 0 && postalCodeLength > 0) {
            btnAddCustomer.setDisable(false);
        } else {
            btnAddCustomer.setDisable(true);
        }
    }

    private void addCustomer(Customer customer) {
        String sql = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";

        try {
            Connection connection = LoadDBDriver.getLoadDBDriverInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, Integer.parseInt(customer.getCustomerId()));
            preparedStatement.setString(2, customer.getTitle());
            preparedStatement.setString(3, customer.getName());
            preparedStatement.setString(4, customer.getDateOfBarth());
            preparedStatement.setDouble(5, Double.parseDouble(customer.getSalary()));
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setString(7, customer.getCity());
            preparedStatement.setString(8, customer.getProvince());
            preparedStatement.setInt(9, Integer.parseInt(customer.getPostalCode()));

            preparedStatement.executeUpdate();

            CenterController.alert.setAlertType(Alert.AlertType.CONFIRMATION);
            CenterController.alert.setContentText(customer.getCustomerId() + " Customer is entered into the system successfully.");
            CenterController.alert.show();

        } catch (SQLException e) {
            CenterController.alert.setAlertType(Alert.AlertType.ERROR);
            CenterController.alert.setContentText(e.getMessage());
            CenterController.alert.show();
        }
    }

    private ObservableList<String> getTitles() {
        ObservableList<String> titles = FXCollections.observableArrayList();

        titles.add("Mr");
        titles.add("Mrs");
        titles.add("Miss");
        titles.add("Ms");

        return titles;
    }

    @FXML
    private void updateAction(ActionEvent actionEvent) {
    }

    @FXML
    private void deleteAction(ActionEvent actionEvent) {
    }

    @FXML
    private void searchAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setItems(getTitles());
        btnAddCustomer.setDisable(true);
        dateOfBarth.setEditable(false);
    }
}
