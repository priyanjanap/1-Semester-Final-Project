package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.db.DbConnection;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignupFormConroller implements Initializable {

    public AnchorPane MainAnchorPane;
    public StackPane Stackpane;
    @FXML
    private Button btmHaveAccount;

    @FXML
    private Button btmSignUp;

    @FXML
    private Label lblCheckPassowrd;

    @FXML
    private Label lblEmailSent;

    @FXML
    private Label lblValidUserName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNicNumber;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtReTypePassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void BtmHaveAccountOnAction(ActionEvent event) throws IOException {

        Parent rootNode= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Login_form.fxml")));
        Scene scene=btmHaveAccount.getScene();
        rootNode.translateXProperty().set(scene.getWidth());
      //  StackPane Stackpane=(StackPane)scene.getRoot();
        Stackpane.getChildren().add(rootNode);
        Timeline timeline=new Timeline();
        KeyValue keyValue=new KeyValue(rootNode.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame keyFrame= new KeyFrame(Duration.seconds(1),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> {
            Stackpane.getChildren().removeAll(MainAnchorPane);
        });
        timeline.play();

    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtUserName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                validateUserName(newValue);
            }
        });
        txtReTypePassword.textProperty().addListener((observable, oldValue, newValue) -> {
            comparePasswords(txtPassword.getText(), newValue);
        });
    }

    private void validateUserName(String Username) {
        if (isValidUsername(Username)) {
            lblValidUserName.setText("Valid username");
            lblValidUserName.setStyle("-fx-text-fill: green;");
        } else {
            lblValidUserName.setText("Invalid username!");
            lblValidUserName.setStyle("-fx-text-fill: red;");
        }
    }
    private void comparePasswords(String password, String retypePassword) {
        if (password.equals(retypePassword)) {
            lblCheckPassowrd.setText("Passwords match");
            lblCheckPassowrd.setStyle("-fx-text-fill: green;");
        } else {
            lblCheckPassowrd.setText("Passwords do not match");
            lblCheckPassowrd.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    void btmSignUpOnAction(ActionEvent event) {

        String Username =txtUserName.getText();

        String nic_number= txtNicNumber.getText();
        String email= txtEmail.getText();
        String password= txtPassword.getText();
        String repassword=txtReTypePassword.getText();


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
    public static boolean isValidUsername(String Username) {

        String usernameRegex = "^[a-zA-Z0-9_.-]+$";
        return Username.matches(usernameRegex);
    }




}
