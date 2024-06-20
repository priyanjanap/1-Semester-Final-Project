package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Repository.EmployeeDTO;
import Lk.ijse.Dress.DTO.Employee;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AddNewEmployeeFormController {

    @FXML
    private AnchorPane anchorPane;
    private JFXButton btmCansel;

    @FXML
    private JFXButton btmInsert;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtDateOfBiath;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtTell;


    private void clearFields() {
        txtName.setText("");
        txtGender.setText("");
        txtAddress.setText("");
        txtDateOfBiath.setText("");
        txtEmployeeId.setText("");
        txtSalary.setText("");
        txtAge.setText("");
        txtTell.setText("");


    }
    @FXML
    void initialize() {
        txtEmployeeId.setOnAction(this::navigateToNextField);

        txtName.setOnAction(this::navigateToNextField);
        txtAddress.setOnAction(this::navigateToNextField);
        txtAge.setOnAction(this::navigateToNextField);
        txtSalary.setOnAction(this::navigateToNextField);
        txtGender.setOnAction(this::navigateToNextField);
        txtDateOfBiath.setOnAction(this::navigateToNextField);
        txtTell.setOnAction(this::navigateToNextField);
    }

    private void navigateToNextField(ActionEvent event) {
        TextField sourceField = (TextField) event.getSource();

        int index = getFieldIndex(sourceField);

        if (index < getAllTextFields().length - 1) {
            TextField nextField = getAllTextFields()[index + 1];
            nextField.requestFocus();
        }
    }

    private int getFieldIndex(TextField field) {
        TextField[] allFields = getAllTextFields();
        for (int i = 0; i < allFields.length; i++) {
            if (allFields[i] == field) {
                return i;
            }
        }
        return -1;
    }

    private TextField[] getAllTextFields() {
        return new TextField[]{txtEmployeeId, txtName, txtAddress, txtAge, txtSalary, txtGender, txtDateOfBiath, txtTell};
    }


    @FXML
    void btmCanselOnAction(ActionEvent event) {
clearFields();
    }


    @FXML
    void btmInsertOnAction(ActionEvent event) throws IOException {

        String employeeID= txtEmployeeId.getText();

        String name=txtName.getText();
        String address= txtAddress.getText();
        int age= Integer.parseInt(txtAge.getText());
        double salary= Double.parseDouble(txtSalary.getText());
        String gender=txtGender.getText();
        Date date= Date.valueOf(txtDateOfBiath.getText());
        int tell= Integer.parseInt(txtTell.getText());

        Employee employee=new Employee(employeeID,name,address,age,salary,gender,date,tell);
        try {
            boolean isSaved = EmployeeDTO.insertEmployee(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee  saved!").show();

                clearFields();


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
