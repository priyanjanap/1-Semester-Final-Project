package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Repository.EmployeeDTO;
import Lk.ijse.Dress.DTO.Employee;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class EmployeeUpdateFormController {

    @FXML
    private JFXButton btmDelete;

    @FXML
    private JFXButton btmUpdate;

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtTell;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;
    public void initialize(){getEmployeeIDs();


    }
    private void clearFields() {
        txtUserId.setText("");
        txtUserName.setText("");
        txtAddress.setText("");
        txtTell.setText("");
        txtSalary.setText("");
    }
    private void getEmployeeIDs() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            var idList = EmployeeDTO.getAllEmployeeIds();
            obList.addAll(idList);
            cmbEmployeeId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btmDeleteOnAction(ActionEvent event) {
       clearFields();
    }

    @FXML
    void btmUpdateOnAction(ActionEvent event) {
        String id = cmbEmployeeId.getValue();

        if (id == null || id.isEmpty()) {
            showAlert("Validation Error", "Please select an Employee ID.");
            return;
        }

        try {
            Employee employee = EmployeeDTO.searchEmployeeById(id);
            if (employee == null) {
                showAlert("Error", "Not a valid Employee ID. No such employee exists.");
                return;
            }

            String name = txtUserName.getText();
            String address = txtAddress.getText();
            String tellText = txtTell.getText();
            String salaryText = txtSalary.getText();

            if (!name.isEmpty()) {
                employee.setEmployeeName(name);
            }
            if (!address.isEmpty()) {
                employee.setEmployeeAddress(address);
            }
            if (!tellText.isEmpty()) {
                int tell = Integer.parseInt(tellText);
                employee.setContactNumber(tell);
            }
            if (!salaryText.isEmpty()) {
                double salary = Double.parseDouble(salaryText);
                employee.setSalary(salary);
            }

            boolean isUpdated = EmployeeDTO.updateEmployee2(employee);
            if (isUpdated) {
                showAlert("Update Successful", "Employee updated successfully.");
                refreshForm();
            } else {
                showAlert("Update Failed", "Failed to update the employee.");
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert("Database Error", "Failed to update employee: " + e.getMessage());
        }
    }
    private void refreshForm() {
        txtUserName.clear();
        txtSalary.clear();
        txtTell.clear();
        txtAddress.clear();
        cmbEmployeeId.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
