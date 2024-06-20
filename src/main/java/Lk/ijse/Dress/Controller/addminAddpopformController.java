package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.db.DbConnection;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addminAddpopformController {

    @FXML
    private TextField UserNamefield;

    @FXML
    private JFXButton canselButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField namefeild;

    @FXML
    private TextField passwordField;

    @FXML
    private JFXButton saveButton;

    @FXML
    void onCanselButton(ActionEvent event) {

    }

    @FXML
    void onSaveClick(ActionEvent event) {

        String Username =namefeild.getText();

        String nic_number= UserNamefield.getText();
        String email= emailField.getText();
        String password= passwordField.getText();



        try {
            boolean signup = signup(Username, nic_number, email, password);
            if (signup) {
                new Alert(Alert.AlertType.CONFIRMATION, "sign up is saved!").show();
                System.out.println("dp");
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println("sds");
        }
    }
    @SneakyThrows
    private boolean signup(String Username,String nic_number,String email,String password) throws SQLException {
        String sql="Insert into useraccount(Username,nic_number,email,password) Values(?,?,?,?)";


        Connection connection= DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql);

        preparedStatement.setObject(1,Username);
        preparedStatement.setObject(2,nic_number);
        preparedStatement.setObject(3,email);
        preparedStatement.setObject(4,password);


        return preparedStatement.executeUpdate()>0;
    }
}
