package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.Repository.CustomerDTO;
import Lk.ijse.Dress.DTO.Customer;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddNewCustomerFormController {
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXButton BtmCansel;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private JFXButton btmCusCard;

    @FXML
    private JFXButton btmCusMail;

    @FXML
    private JFXButton btmUpdate;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtCusMail;

    @FXML
    private TextField txtCusName;
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCusNumber;

    @FXML
    void BtmCanselOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/dashboard2_form.fxml"));
        Stage stage = (Stage)pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Item Form");
        stage.centerOnScreen();

    }

    @FXML
    void BtmOnUpdate(ActionEvent event) {
        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtAddress.getText();
        int tel = Integer.parseInt(txtCusNumber.getText());
        String mail = txtCusMail.getText();
        Customer customer = new Customer(id, name, address, tel,mail);

        try {
            boolean isSaved = CustomerDTO.save(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void clearFields() {
        txtCusId.setText("");
        txtCusName.setText("");
        txtAddress.setText("");
        txtCusNumber.setText("");
        txtCusMail.setText("");
    }

    @FXML
    void btmCusMailOnAction(ActionEvent event) {
        String email1Text = txtCusMail.getText();
        new Thread(() -> {
            try {
                Mail.sendMail(email1Text);
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
        }).start();
        new Alert(Alert.AlertType.INFORMATION, "You have to check your mailbox").show();
    }




    @FXML
    void btmCuscardOnAction(ActionEvent event) {


    }

    @FXML
    void getDate(ActionEvent event) {
        LocalDate date= DatePicker.getValue();
        String Date=date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

    }

}
