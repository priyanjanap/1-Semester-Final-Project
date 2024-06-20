package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.db.DbConnection;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class adminVrifyformController {

    @FXML
    private TextField adminname;

    @FXML
    private PasswordField adminpassword;

    @FXML
    private JFXButton canselButton;

    @FXML
    private JFXButton okButton;

    @FXML
    void onOkClick(ActionEvent event) throws IOException {
        String username = adminname.getText();
        String password = adminpassword.getText();
        String sql = "SELECT COUNT(*) FROM useraccount WHERE Username=?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {

                    validateLogin(username, password);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Username does not exist").show();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @SneakyThrows
    public void validateLogin(String userId, String password) throws SQLException, IOException {
        String sql = "SELECT Username, password FROM useraccount WHERE Username = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        {
            preparedStatement.setString(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String dbPassword = resultSet.getString("password");

                    if (password.equals(dbPassword)) {
                        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/newAdminAdd_form.fxml")));
                        Scene scene = adminpassword.getScene();
                        scene.setRoot(rootNode);


                    } else {

                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Username does not exist").show();
                }
            }


        }
    }

    @FXML
    void onaddEnterPassword(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            okButton.requestFocus();
        }

    }

    @FXML
    void onaddEnterUserName(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            adminpassword.requestFocus();
        }


    }

    @FXML
    void oncanselClick(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();

    }

}
