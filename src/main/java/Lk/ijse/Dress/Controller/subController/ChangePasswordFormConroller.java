package Lk.ijse.Dress.Controller.subController;


import Lk.ijse.Dress.db.DbConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;


public class ChangePasswordFormConroller {

    public TextField txtOtp;
    public Label lblRepassswordEquals;
    public Label lblValidUserName1;
    @FXML
    private Button btmBackToLogin;

    @FXML
    private AnchorPane MainAnchoPane;

    @FXML
    private Button btmContinue;

    @FXML
    private Label btmEaualPassword;

    @FXML
    private Button btmVerifaction;

    @FXML
    private TextField lblOTP;

    @FXML
    private Label lblValidUserName;

    @FXML
    private Label lblotpEqualsVerifationCode;

    @FXML
    private TextField txtEmail1;

    @FXML
    private PasswordField txtPaasowd1;

    @FXML
    private PasswordField txtReTypePassword2;

    @FXML
    private TextField txtUserName1;


    javaMailUtil javaMailUtilRefer = new javaMailUtil();
    int randomNumber;
    private Node rootNode;

    @SneakyThrows
    @FXML
    void btmContinueOnaction(ActionEvent event) throws SQLException {

        String username = txtUserName1.getText();
        String password = txtPaasowd1.getText();

        String sql = "SELECT COUNT(*) FROM useraccount WHERE Username=?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    changePassword(username, password);
                    System.out.println("Password updated successfully for user: " + username);
                    new Alert(Alert.AlertType.CONFIRMATION, "Password updated successfully for user: " + username).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Username does not exist").show();
                }
            }
        }
    }

    @SneakyThrows
    private boolean changePassword(String Username, String newPassword) throws SQLException {

        String sql = "UPDATE useraccount SET password = ? WHERE Username = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, Username);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        }
    }

    private void comparePasswords(String password, String retypePassword) {
        if (password.equals(retypePassword)) {
            lblRepassswordEquals.setText("Passwords match");
            lblRepassswordEquals.setStyle("-fx-text-fill: green;");
        } else {
            lblRepassswordEquals.setText("Passwords do not match");
            lblRepassswordEquals.setStyle("-fx-text-fill: red;");
        }
    }

  /*  @SneakyThrows
    private boolean changePassword( String Username,String newPassword) throws SQLException {
        String sql = "UPDATE useraccount SET password = ? WHERE Username = ?";
        try (Connection connection = DbConnection.getInstance().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, Username);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        }
    }

   */

    @FXML
    void btmVerifactionOnAction(ActionEvent event) throws Exception {
        String email1Text = txtEmail1.getText();
        new Thread(() -> {
            try {
                javaMailUtilRefer.sendMail(email1Text);
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
        }).start();
        new Alert(Alert.AlertType.INFORMATION, "You have to check your mailbox").show();
    }


    @FXML
    void onOTPTyping(KeyEvent event) {
        try {
            int enteredOtp = Integer.parseInt(txtOtp.getText());
            int savedRandomNumber = javaMailUtilRefer.getSaveRandomNumber();

            if (enteredOtp == savedRandomNumber) {
                lblotpEqualsVerifationCode.setText("Correct OTP");
                lblotpEqualsVerifationCode.setStyle("-fx-text-fill: green;");
                System.out.println("Correct OTP");


                txtPaasowd1.setDisable(false);
                txtReTypePassword2.setDisable(false);


                txtReTypePassword2.textProperty().addListener((observable, oldValue, newValue) -> {
                    comparePasswords(txtPaasowd1.getText(), newValue);
                });
            } else {
                lblotpEqualsVerifationCode.setText("Incorrect OTP");
                lblotpEqualsVerifationCode.setStyle("-fx-text-fill: red;");
                System.out.println("Incorrect OTP");


                txtPaasowd1.setDisable(true);
                txtReTypePassword2.setDisable(true);


                txtReTypePassword2.clear();
            }
        } catch (NumberFormatException e) {
            lblotpEqualsVerifationCode.setText("Invalid OTP");
            System.out.println("Invalid OTP");


            txtPaasowd1.setDisable(true);
            txtReTypePassword2.setDisable(true);
        }
    }


    @FXML
    void btmbacktologinOnaction(ActionEvent event) throws IOException {

        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/VIEW/Login_form.fxml")));
        Scene scene = btmBackToLogin.getScene();
        scene.setRoot(rootNode);
    }

    public void initialize() {

        txtUserName1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                validateUserName(newValue);
            }
        });
        txtReTypePassword2.textProperty().addListener((observable, oldValue, newValue) -> {
            comparePasswords(txtPaasowd1.getText(), newValue);
        });

    }

    private void validateUserName(String username) {
        if (isValidUsername(username)) {
            lblValidUserName1.setText("Valid username");
            lblValidUserName1.setStyle("-fx-text-fill: green;");
        } else {
            lblValidUserName1.setText("Invalid username!");
            lblValidUserName1.setStyle("-fx-text-fill: red;");
        }
    }

    public static boolean isValidUsername(String Username) {

        String usernameRegex = "^[a-zA-Z0-9_.-]+$";
        return Username.matches(usernameRegex);
    }


}

